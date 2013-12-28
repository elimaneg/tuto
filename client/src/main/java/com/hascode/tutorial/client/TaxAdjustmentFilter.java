package com.hascode.tutorial.client;

import java.io.IOException;
import java.math.BigDecimal;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

import com.hascode.tutorial.jaxrs.entity.Book;

public class TaxAdjustmentFilter implements ClientRequestFilter {
	public static final BigDecimal TAX_RATE = new BigDecimal("2.5");

	@Override
	public void filter(final ClientRequestContext rc) throws IOException {
		String method = rc.getMethod();
		if ("POST".equals(method) && rc.hasEntity()) {
			Book book = (Book) rc.getEntity();
			BigDecimal priceWithTaxes = book.getPrice().multiply(TAX_RATE);
			book.setPrice(priceWithTaxes);
			rc.setEntity(book);
		}
	}

}
