package it;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.math.BigDecimal;
import java.util.GregorianCalendar;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Test;

import com.hascode.tutorial.client.TaxAdjustmentFilter;
import com.hascode.tutorial.jaxrs.entity.Book;

public class ClientFilterExamples {
	private static final String REST_SERVICE_URL = "http://localhost:8080/tutorial/rs/book";

	private static final String TITLE = "One big book";
	private static final BigDecimal PRICE = new BigDecimal("20.0");
	private static final GregorianCalendar PUBLISHED = new GregorianCalendar(
			2013, 12, 24);

	public Book mockBook() {
		Book book = new Book();
		book.setTitle(TITLE);
		book.setPrice(PRICE);
		book.setPublished(PUBLISHED);
		return book;
	}

	@Test
	public void clientRequestFilterExample() {
		Book book = mockBook();

		Client client = ClientBuilder.newClient()
				.register(JacksonFeature.class)
				.register(TaxAdjustmentFilter.class);
		Book bookPersisted = client
				.target(REST_SERVICE_URL)
				.request()
				.post(Entity.entity(book, MediaType.APPLICATION_JSON),
						Book.class);

		String bookId = bookPersisted.getId();
		assertThat(bookId, notNullValue());
		assertThat(bookPersisted.getPrice(),
				equalTo(PRICE.multiply(TaxAdjustmentFilter.TAX_RATE)));

	}
}