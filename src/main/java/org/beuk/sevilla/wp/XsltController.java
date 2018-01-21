package org.beuk.sevilla.wp;

import java.io.*;
import java.util.*;

import javax.xml.bind.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.apache.commons.configuration.*;
import org.beuk.sevilla.wp.dto.*;
import org.beuk.sevilla.wp.entry.*;
import org.slf4j.*;
import org.w3c.dom.*;
import org.w3c.dom.Element;
import org.xml.sax.*;

public class XsltController {

	private static <T> List<T> unmarshalList(Unmarshaller unmarshaller, Class<T> clazz, Node node) throws JAXBException {

		final ListWrapper<T> wrapper = unmarshaller.unmarshal(node, ListWrapper.class).getValue();
		return wrapper.getItems();
	}

	private final Logger log = LoggerFactory.getLogger(XsltController.class);
	private TransformerFactory tFactory;
	private DocumentBuilderFactory docFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private DOMSource domSource;

	private PropertiesConfiguration configuration;

	public XsltController() {

	}

	public XsltController(final PropertiesConfiguration configuration) {
		this.configuration = configuration;
	}

	public List<PlayerResult> generatePlayerResult(RoundDTO roundDoc) throws FileNotFoundException, JAXBException, TransformerException {

		final JAXBContext jc = JAXBContext.newInstance(ListWrapper.class, PlayerResult.class);
		final Unmarshaller unmarshaller = jc.createUnmarshaller();
		final DOMResult domResult = new DOMResult();
		final Transformer transformer = setTransformer("xsl.player.result");
		transformer.setParameter("Round", roundDoc.round);
		// printXML(transformer, domResult);
		// final Transformer transformer = setTransformer("xsl.player.result");
		transformer.transform(domSource, domResult);
		return unmarshalList(unmarshaller, PlayerResult.class, domResult.getNode());
	}

	public List<PlayerRanking> generateRanking(int round) throws FileNotFoundException, JAXBException, TransformerException {

		final JAXBContext jc = JAXBContext.newInstance(ListWrapper.class, PlayerRanking.class);
		final Unmarshaller unmarshaller = jc.createUnmarshaller();
		final Transformer transformer = setTransformer("xsl.main.ranking");
		transformer.setParameter("Round", round);
		final DOMResult domResult = new DOMResult();
		transformer.transform(domSource, domResult);

		return unmarshalList(unmarshaller, PlayerRanking.class, domResult.getNode());
	}

	public List<RoundResult> generateRoundResult(RoundDTO roundDoc) throws JAXBException, TransformerConfigurationException, Throwable {

		final JAXBContext jc = JAXBContext.newInstance(ListWrapper.class, RoundResult.class);
		final Unmarshaller unmarshaller = jc.createUnmarshaller();
		final Transformer transformer = setTransformer("xsl.round.result");
		transformer.setParameter("Round", roundDoc.round);
		final DOMResult domResult = new DOMResult();
		transformer.transform(domSource, domResult);

		return unmarshalList(unmarshaller, RoundResult.class, domResult.getNode());
	}

	public void initialize() throws TransformerException, ParserConfigurationException, SAXException, IOException {

		final String xmlInputFile = configuration.getString("sev.xml.inputFile");
		log.info("input: " + xmlInputFile);
		tFactory = TransformerFactory.newInstance();
		docFactory = DocumentBuilderFactory.newInstance();
		docBuilder = docFactory.newDocumentBuilder();
		doc = docBuilder.parse(xmlInputFile);
		domSource = new DOMSource(doc);
	}

	public List<Category> retrieveCategoryInfo() throws FileNotFoundException, TransformerException, JAXBException {

		final JAXBContext jc = JAXBContext.newInstance(ListWrapper.class, Category.class);
		final Unmarshaller unmarshaller = jc.createUnmarshaller();
		final Transformer transformer = setTransformer("xsl.category.info");
		final DOMResult domResult = new DOMResult();
		transformer.transform(domSource, domResult);
		return unmarshalList(unmarshaller, Category.class, domResult.getNode());
	}

	public Competition retrieveCompetitionInfo() throws FileNotFoundException, TransformerException, JAXBException {

		final Transformer transformer = setTransformer("xsl.competition.info");
		final DOMResult domResult = new DOMResult();
		transformer.transform(domSource, domResult);
		final Element element = ((Document) domResult.getNode()).getDocumentElement();

		final JAXBContext context = JAXBContext.newInstance(Competition.class);
		final Unmarshaller unmarshaller = context.createUnmarshaller();
		final JAXBElement<Competition> roundJaxbElement = unmarshaller.unmarshal(element, Competition.class);
		final Competition competition = roundJaxbElement.getValue();

		log.debug("Competition name: " + competition.name);
		return competition;
	}

	public List<Round> retrieveRoundInfo() throws FileNotFoundException, TransformerException, JAXBException {

		final JAXBContext jc = JAXBContext.newInstance(ListWrapper.class, Round.class);
		final Unmarshaller unmarshaller = jc.createUnmarshaller();
		final Transformer transformer = setTransformer("xsl.round.info");
		final DOMResult domResult = new DOMResult();
		transformer.transform(domSource, domResult);
		// printXML(transformer, domResult);
		return unmarshalList(unmarshaller, Round.class, domResult.getNode());
	}

	void printXML(Transformer transformer, DOMResult domResult) throws TransformerException {

		final StringWriter buffer = new StringWriter();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		transformer.transform(domSource, new StreamResult(buffer));
		log.info("buffer: \n" + buffer.toString());
	}

	private Transformer setTransformer(String xslFileName) throws TransformerConfigurationException, FileNotFoundException {

		final String xslFile = configuration.getString(xslFileName);
		log.debug("loading transformer file: " + xslFile);
		final Transformer transformer = tFactory.newTransformer(new StreamSource(xslFile));
		if (transformer == null)
			throw new FileNotFoundException("transformer null, not found? : " + xslFile);

		return transformer;
	}
}
