package org.beuk.sevilla.wp.entry;

import javax.xml.bind.annotation.*;

@XmlRootElement
public class Round {

	@XmlElement(name = "RankOrder")
	public int rankOrder;

	@XmlElement(name = "Round")
	public int round;

	@XmlElement(name = "Date")
	public String date;

	@XmlElement(name = "Name")
	public String name;
}
