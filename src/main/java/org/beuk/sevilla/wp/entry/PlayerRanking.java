package org.beuk.sevilla.wp.entry;

import java.util.*;

import javax.xml.bind.annotation.*;

@XmlRootElement
public class PlayerRanking {

	public enum Gender {
		Male,
		Female
	}

	public short id;

	public String name;

	public int pos;

	public float value;

	public float score;

	public short games;

	public short wins;

	public short draws;

	public short losses;

	public Gender gender;

	@XmlElement(name = "TPR")
	public short tpr;

	public short iRtg;

	public short balance;

	public String[] categories;

	public float percentage;

	public PlayerResult roundResults;

	public String defaultAvatarPrefix;

	public String avatarPrefix;

	public String profilePrefix;

	public String slugPrefix;

	public List<String> myCompetitions;

	public String parentSlug;

	public List<String> competitions;

	public String getAvatarPrefix() {

		return avatarPrefix;
	}

	public short getBalance() {

		return balance;
	}

	public String[] getCategories() {

		return categories;
	}

	public List<String> getCompetitions() {

		return competitions;
	}

	public String getDefaultAvatarPrefix() {

		return defaultAvatarPrefix;
	}

	public short getDraws() {

		return draws;
	}

	public short getGames() {

		return games;
	}

	public Gender getGender() {

		return gender;
	}

	public short getId() {

		return id;
	}

	public short getiRtg() {

		return iRtg;
	}

	public short getLosses() {

		return losses;
	}

	public List<String> getMyCompetitions() {

		return myCompetitions;
	}

	public String getName() {

		return name;
	}

	public String getParentSlug() {

		return parentSlug;
	}

	public float getPercentage() {

		return percentage;
	}

	public int getPos() {

		return pos;
	}

	public String getProfilePrefix() {

		return profilePrefix;
	}

	public PlayerResult getRoundResults() {

		return roundResults;
	}

	public float getScore() {

		return score;
	}

	public String getSlugPrefix() {

		return slugPrefix;
	}

	public short getTpr() {

		return tpr;
	}

	public float getValue() {

		return value;
	}

	public short getWins() {

		return wins;
	}

	@XmlElement(name = "category")
	public void setCategory(String category) {

		categories = category.isEmpty() ? new String[0] : category.split("\\+");
	}
}
