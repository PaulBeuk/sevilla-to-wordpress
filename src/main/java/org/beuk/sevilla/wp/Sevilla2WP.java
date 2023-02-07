package org.beuk.sevilla.wp;

import java.io.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;

import javax.xml.bind.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.xpath.*;

import org.apache.commons.configuration.*;
import org.apache.xmlrpc.*;
import org.beuk.sevilla.wp.dto.*;
import org.beuk.sevilla.wp.entry.*;
import org.beuk.sevilla.wp.entry.Category;
import org.beuk.wordpress.api.client.*;
import org.beuk.wordpress.api.entity.*;
import org.slf4j.*;
import org.xml.sax.*;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;

import freemarker.template.*;

public class Sevilla2WP {

	public static void main(String[] args) throws JAXBException, XPathExpressionException, IllegalStateException, XmlRpcException {

		try {
			final Sevilla2WP sev2wp = new Sevilla2WP();
			sev2wp.processSevillaData();
		} catch (TransformerException | ParserConfigurationException | SAXException | IOException | ConfigurationException e) {
			e.printStackTrace();
		}
	}

	private final PropertiesConfiguration configuration;
	Logger log = LoggerFactory.getLogger(Sevilla2WP.class);
	private final XsltController xsltController;
	private Competition competition;
	private List<RoundDTO> roundDocs;
	private List<Category> categories;
	private final TemplateController templateController;
	private int generalUserId;
	private final XMLRPCController xmlrpcController;
	private final String wpBaseDir;
	private final AvatarController avatarController;
	private final String avatarPrefix;

	String competitionList = null;
	private final String parentPageTitle;
	private XMLPageSummary parentPage;
	private final String profilePrefix;

	int lastRound = 0;

	public Sevilla2WP() throws ConfigurationException, IOException, XmlRpcException {

		final String propertyFile = System.getProperty("configPropertyFile");
		if (propertyFile == null) {
			log.error("configPropertyFile not set use -DconfigPropertyFile=<file>");
			System.exit(1);
		}
		configuration = new PropertiesConfiguration(propertyFile);
		xsltController = new XsltController(configuration);
		xmlrpcController = new XMLRPCController(configuration);
		avatarController = new AvatarController(configuration);
		templateController = new TemplateController();
		wpBaseDir = configuration.getString("wp.basedir");
		profilePrefix = configuration.getString("wp.basedir") + configuration.getString("wp.profilepage");
		parentPageTitle = configuration.getString("wp.competition.parentpage.title");
		avatarPrefix = configuration.getString("web.avatar.prefixpath");
	}

	protected void processSevillaData() throws TransformerException, ParserConfigurationException, SAXException, IOException, JAXBException, XPathExpressionException, IllegalStateException, XmlRpcException {

		xsltController.initialize();

		/* get wordpress parent page */
		getParentPage();
		/* get wordpress user */
		getGeneralUser();

		/* check wordpress avatars */
		updateAvatars();

		/* check wordpress avatars */
		retrieveCompetitionInfo();

		/* read the round number (XSLT) */
		retrieveRoundInfo();

		/* generate main ranking (XSLT) and save it in wordpress */
		mainRanking();

		/* generate user results (XSLT) and save it in wordpress */
		playerResults();
	}

	PlayerResult findRoundResult(PlayerRanking ranking, List<PlayerResult> playerResults) {

		final Optional<PlayerResult> optional = playerResults.stream().filter(p -> p.id == ranking.id).findFirst();
		if (!optional.isPresent())
			return null;

		final PlayerResult playerResult = optional.get();
		playerResult.games.stream().forEach(g -> {
			g.iAmWhite = g.white.equals(ranking.getName());
			g.absent = g.white.isEmpty();
		});
		return playerResult;
	}

	void mainRanking() {

		if (configuration.getString("main.generate.ranking").equals("yes")) {
			log.info("rroundDocs: " + roundDocs.size());
			roundDocs.stream().forEach(dto -> {
				try {
					log.info("--------- processing round: " + dto.round);
					dto.parentSlug = parentPage.pageSlug;
					final List<PlayerRanking> rank = xsltController.generateRanking(dto.round);
					log.info("Round players: " + rank.size());
					dto.ranking.add(new AbstractMap.SimpleEntry<>("Algemeen", rank));
					log.info("algemeen");
					categories.stream().forEach(c -> dto.ranking.add(new AbstractMap.SimpleEntry<>(c.getName(), rank.stream().filter(p -> filterCat(p.categories, c.shortName)).collect(Collectors.toList()))));
					log.info("1 algemeen");
					final List<RoundResult> roundResult = xsltController.generateRoundResult(dto);
					log.info("2 algemeen");
					matchResults(roundResult, dto);
					dto.competitions = getCompetition();
					final String rankingHTML = templateController.processTemplate(dto, "ranking");
					toWordpress(competition.name + (dto.isLast ? " stand" : " ronde " + dto.round), rankingHTML);
				} catch (final Throwable e) {
					e.printStackTrace();
					System.exit(1);
				}
			});
		} else {
			log.info("main.generate.ranking set to no, skipping main ranking");
		}

	}

	void playerPageToWordpress(PlayerRanking player, String html) throws JsonParseException, JsonMappingException, XmlRpcException, IOException {

		final int userId = getWordpressUser(player.name.replaceAll("[ -]", ""));
		final String title = competition.name + ' ' + player.name;
		log.info("userId: " + userId + " saving: " + title);
		xmlrpcController.savePage(title, title.replace(' ', '-'), html, userId, parentPage.pageId, null);

	}

	void playerResults() {

		if (configuration.getString("main.generate.playerresults").equals("yes")) {
			roundDocs.stream().filter(r -> r.isLast == true).forEach(round -> {
				log.info(round.name + " playerResults ------------ for round : " + round.date);
				try {
					final List<PlayerResult> playerResults = xsltController.generatePlayerResult(round);
					log.info("player results found: " + playerResults.size() + " round.rankings: " + round.ranking.get(0).getValue().size());
					round.ranking.get(0).getValue().stream().filter(player -> player.id > 0).forEach(player -> {
						player.roundResults = findRoundResult(player, playerResults);
						player.avatarPrefix = round.avatarPrefix;
						player.profilePrefix = profilePrefix;
						player.slugPrefix = competition.resultsSlugPrefix;
						player.defaultAvatarPrefix = round.getDefaultAvatarPrefix();
						player.parentSlug = parentPage.pageSlug;
						player.myCompetitions = getMyCompetitionResults(player);
						player.competitions = round.competitions;

						try {
							final String playerPage = templateController.processTemplate(player, "player");
							playerPageToWordpress(player, playerPage);
							// log.info("player page: " + playerPage);
						} catch (IOException | TemplateException | XmlRpcException e) {
							e.printStackTrace();
						}
					});
				} catch (IOException | JAXBException | TransformerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		} else {
			log.info("main.generate.playerresults set to no, skipping player result ranking");
		}
	}

	void retrieveCompetitionInfo() throws XPathExpressionException, IllegalStateException, TransformerException, FileNotFoundException, JAXBException {

		competition = xsltController.retrieveCompetitionInfo();
		competition.setPlayersResultsSlug(wpBaseDir, parentPage.pageSlug);
		competition.setTitle();
		log.info("Competition resultsSlugPrefix: " + competition.resultsSlugPrefix + " title: " + competition.title);
		categories = xsltController.retrieveCategoryInfo();
	}

	void retrieveRoundInfo() throws XPathExpressionException, IllegalStateException, TransformerException, FileNotFoundException, JAXBException {

		final List<Round> rounds = xsltController.retrieveRoundInfo();
		log.info("Found rounds: " + rounds.size());
		if (!rounds.isEmpty()) {
			lastRound = rounds.get(rounds.size() - 1).round;
		}

		roundDocs = rounds.stream().map(r -> {
			final RoundDTO dto = new RoundDTO();
			dto.date = r.date;
			dto.totalRounds = lastRound;
			dto.name = r.name;
			dto.round = r.round;

			dto.avatarPrefix = avatarPrefix;
			dto.setDefaultAvatarPrefix(dto.avatarPrefix + "caissa_avatar_64.png");
			dto.slugPrefix = competition.resultsSlugPrefix;
			dto.isLast = lastRound == r.round;
			return dto;
		}).collect(Collectors.toList());
	}

	void toWordpress(String title, String html) throws IOException, XmlRpcException {

		log.info("generalUserId: " + generalUserId + " saving page: " + title.replace(' ', '-') + " html len: " + html.length());
		final String[] cats = {};
		xmlrpcController.savePage(title, title.replace(' ', '-'), html, generalUserId, parentPage.pageId, cats);
	}

	void updateAvatars() {

		if (configuration.getString("main.update.avatars").equals("yes")) {
			avatarController.checkForNewAvatars();
		} else {
			log.info("skip updateAvatars main.update.avatars not yes");
		}
	}

	private boolean filterCat(final String[] cats, final String cat) {

		if (cats.length == 1) {
			return cats[0].equals(cat);
		} else if (cats.length == 2) {
			return cats[0].equals(cat) || cats[1].equals(cat);
		} else {
			return false;
		}
	}

	private List<String> getCompetition() {

		LocalDateTime dateTime = LocalDateTime.of(2010, 1, 1, 0, 0);
		final LocalDateTime nowDateTime = LocalDateTime.now();

		final List<String> list = new ArrayList<>();

		for (; dateTime.isBefore(nowDateTime); dateTime = dateTime.plusYears(1)) {
			final String pageTitleA = "Interne " + dateTime.getYear() + '-' + (dateTime.getYear() + 1) + " A stand";
			XMLPageSummary pageByTitle = xmlrpcController.getPageByTitle(pageTitleA);
			if (pageByTitle != null)
				list.add(pageByTitle.pageTitle);
			final String pageTitleB = "Interne " + dateTime.getYear() + '-' + (dateTime.getYear() + 1) + " B stand";
			pageByTitle = xmlrpcController.getPageByTitle(pageTitleB);
			if (pageByTitle != null) {
				list.add(pageByTitle.pageTitle);
			}
			// log.info(pageTitleA + " of " + pageTitleB);
		}
		return list;
	}

	private void getGeneralUser() throws JsonParseException, JsonMappingException, XmlRpcException, IOException {

		final String generalWPUser = configuration.getString("wp.general.user");
		generalUserId = xmlrpcController.getUserByName(generalWPUser);
		if (generalUserId < 0) {
			log.error("no general user found by property wp.general.user -: " + generalWPUser + " create user first!");
			log.error("no general user found by property wp.general.user -: " + generalWPUser + " create user first!");
			log.error("no general user found by property wp.general.user -: " + generalWPUser + " create user first!");
			System.exit(1);
		}
	}

	private List<String> getMyCompetitionResults(PlayerRanking player) {

		return xmlrpcController.getAllPages().stream().filter(s -> s.pageParentId == parentPage.pageId && s.pageTitle.endsWith(player.name) && s.pageTitle.startsWith("Interne")).map(s -> s.pageTitle).collect(Collectors.toList());
	}

	private void getParentPage() {

		parentPage = xmlrpcController.getPageByTitle(parentPageTitle);
		if (parentPage == null) {
			log.error("no parent page found by title: " + parentPageTitle + " create the page first!");
			log.error("no parent page found by title: " + parentPageTitle + " create the page first!");
			log.error("no parent page found by title: " + parentPageTitle + " create the page first!");
			System.exit(1);
		} else {
			log.info("parent page found by title: " + parentPageTitle + " id: " + parentPage.pageId);
			parentPage.pageSlug = wpBaseDir + parentPage.pageTitle.replace(' ', '-');
		}
	}

	private int getWordpressUser(final String username) throws JsonParseException, JsonMappingException, XmlRpcException, IOException {

		final int userId = xmlrpcController.getUserByName(username);
		return userId < 0 ? generalUserId : userId;
	}

	private void matchResults(List<RoundResult> roundResult, RoundDTO dto) {

		final List<PlayerRanking> list = dto.ranking.get(0).getValue();
		roundResult.stream().forEach(r -> {
			final Optional<PlayerRanking> white = list.stream().filter(p -> p.getName().equals(r.white)).findFirst();
			final Optional<PlayerRanking> black = list.stream().filter(p -> p.getName().equals(r.black)).findFirst();
			if (white.isPresent() && black.isPresent())
				dto.addResult(white.get(), black.get(), r.result);
		});
	}

}
