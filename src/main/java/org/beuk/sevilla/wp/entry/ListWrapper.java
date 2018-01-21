package org.beuk.sevilla.wp.entry;

import java.util.*;

import javax.xml.bind.annotation.*;

public class ListWrapper<T> {

	private final List<T> items;

	public ListWrapper() {
		items = new ArrayList<>();
	}

	public ListWrapper(List<T> items) {
		this.items = items;
	}

	@XmlAnyElement(lax = true)
	public List<T> getItems() {

		return items;
	}

}