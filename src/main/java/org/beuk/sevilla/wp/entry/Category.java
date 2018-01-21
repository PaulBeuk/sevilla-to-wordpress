package org.beuk.sevilla.wp.entry;

import javax.xml.bind.annotation.*;

@XmlRootElement
public class Category {

	private String name;

	@XmlElement(name = "ID")
	public int id;

	@XmlElement(name = "ShortName")
	public String shortName;

	public String getName() {

		return name;
	}

	@XmlElement(name = "Name")
	public void setName(String name) {

		final int index = name.indexOf('+');
		if (index > -1) {
			this.name = name.substring(0, index);
		} else {
			this.name = name;
		}
	}

}
