package com.hascode.tutorial.jaxrs.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

public class Book implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String title;
	private BigDecimal price;
	private Calendar published;

	public final String getId() {
		return id;
	}

	public final void setId(final String id) {
		this.id = id;
	}

	public final String getTitle() {
		return title;
	}

	public final void setTitle(final String title) {
		this.title = title;
	}

	public final BigDecimal getPrice() {
		return price;
	}

	public final void setPrice(final BigDecimal price) {
		this.price = price;
	}

	public final Calendar getPublished() {
		return published;
	}

	public final void setPublished(final Calendar published) {
		this.published = published;
	}

	@Override
	public String toString() {
		return String.format("Book [id=%s, title=%s, price=%s, published=%s]",
				id, title, price, published);
	}

}
