package org.beuk.sevilla.wp.dto;

import java.util.*;

import org.beuk.sevilla.wp.entry.*;

public class RoundDTO {

	public class Result {

		public PlayerRanking white;

		public PlayerRanking black;

		public String result;

		public PlayerRanking getBlack() {

			return black;
		}

		public String getResult() {

			return result;
		}

		public PlayerRanking getWhite() {

			return white;
		}
	}

	public int rankOrder;
	public int round;
	public String date;
	public String name;
	public List<String> competitions;

	public boolean isLast;

	public ArrayList<Result> results = new ArrayList<>();

	public ArrayList<Map.Entry<String, List<PlayerRanking>>> ranking = new ArrayList<>();

	public String avatarPrefix;

	public String slugPrefix;
	private String defaultAvatarPrefix;
	public int totalRounds;
	public String parentSlug;

	public void addResult(PlayerRanking white, PlayerRanking black, String result) {

		final Result r = new Result();
		r.white = white;
		r.black = black;
		r.result = result;
		results.add(r);
	}

	public String getAvatarPrefix() {

		return avatarPrefix;
	}

	public List<String> getCompetitions() {

		return competitions;
	}

	public String getDate() {

		return date;
	}

	public String getDefaultAvatarPrefix() {

		return defaultAvatarPrefix;
	}

	public String getName() {

		return name;
	}

	public String getParentSlug() {

		return parentSlug;
	}

	public ArrayList<Map.Entry<String, List<PlayerRanking>>> getRanking() {

		return ranking;
	}

	public ArrayList<Result> getResults() {

		return results;
	}

	public int getRound() {

		return round;
	}

	public String getSlugPrefix() {

		return slugPrefix;
	}

	public int getTotalRounds() {

		return totalRounds;
	}

	public void setDefaultAvatarPrefix(String defaultAvatarPrefix) {

		this.defaultAvatarPrefix = defaultAvatarPrefix;
	}

	public void setRanking(ArrayList<Map.Entry<String, List<PlayerRanking>>> ranking) {

		this.ranking = ranking;
	}
}
