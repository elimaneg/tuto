package it;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Test;

import com.hascode.tutorial.jaxrs.entity.Book;

public class ClientExamples {
	private static final String REST_SERVICE_URL = "http://localhost:8080/tutorial/rs/book";

	private static final String TITLE = "One big book";
	private static final BigDecimal PRICE = new BigDecimal("20.0");
	private static final GregorianCalendar PUBLISHED = new GregorianCalendar(
			2013, 12, 24);

	Client client = ClientBuilder.newClient().register(JacksonFeature.class);

	public Book mockBook() {
		Book book = new Book();
		book.setTitle(TITLE);
		book.setPrice(PRICE);
		book.setPublished(PUBLISHED);
		return book;
	}

	@Test
	public void crudExample() {
		// 1. Save a new book
		Book book = mockBook();
		Book bookPersisted = client
				.target(REST_SERVICE_URL)
				.request()
				.post(Entity.entity(book, MediaType.APPLICATION_JSON),
						Book.class);

		String bookId = bookPersisted.getId();
		assertThat(bookId, notNullValue());

		// 2. Fetch book by id
		Book book2 = client.target(REST_SERVICE_URL).path("/{bookId}")
				.resolveTemplate("bookId", bookId).request().get(Book.class);
		assertThat(book2, notNullValue());
		assertThat(book2.getTitle(), equalTo(TITLE));
		assertThat(book2.getPrice(), equalTo(PRICE));
		assertThat(book2.getPublished().getTime(), equalTo(PUBLISHED.getTime()));

		// 3. Fetch all books
		GenericType<List<Book>> bookType = new GenericType<List<Book>>() {
		}; // generic type to wrap a generic list of books
		List<Book> books = client.target(REST_SERVICE_URL).request()
				.get(bookType);
		assertThat(books.size(), equalTo(1));

		// 4. Delete a book
		client.target(REST_SERVICE_URL).path("/{bookId}")
				.resolveTemplate("bookId", bookId).request().delete();
		List<Book> books2 = client.target(REST_SERVICE_URL).request()
				.get(bookType);
		assertThat(books2.isEmpty(), equalTo(true));
	}
}