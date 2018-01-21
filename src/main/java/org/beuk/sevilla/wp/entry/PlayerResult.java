package org.beuk.sevilla.wp.entry;

import java.util.*;

import javax.xml.bind.annotation.*;

@XmlRootElement
public class PlayerResult {

	@XmlAccessorType(XmlAccessType.FIELD)
	public static class GameResult {

		public short round;
		public String white;
		public String black;
		public String result;
		public boolean iAmWhite;
		public boolean absent;

		public String getBlack() {

			return black;
		}

		public String getResult() {

			return result;
		}

		public short getRound() {

			return round;
		}

		public String getWhite() {

			return white;
		}

		public boolean isAbsent() {

			return absent;
		}

		public boolean isiAmWhite() {

			return iAmWhite;
		}

	}

	public short id;

	@XmlElement(name = "game")
	public List<GameResult> games = new ArrayList<>();

	public List<GameResult> getGames() {

		return games;
	}

	public short getId() {

		return id;
	}
}
