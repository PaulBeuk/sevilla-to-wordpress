package org.beuk.sevilla.wp.entry;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Competition {

	public String name;
	public String title;
	public String resultsSlugPrefix;

	public void setPlayersResultsSlug(String wpBaseDir, String parentPageSlug) {

		resultsSlugPrefix = parentPageSlug + '/' + name.replace(' ', '-') + '-';
	}

	public void setTitle() {

		title = name.replace(' ', '-');
	}
}
