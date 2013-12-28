package it;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;

import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.Future;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Test;

import com.hascode.tutorial.jaxrs.entity.Book;

public class AsyncClientExamples {
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
	public void asyncExample() throws Exception {
		Book book = mockBook();

		Future<Book> fb = client
				.target(REST_SERVICE_URL)
				.request()
				.async()
				.post(Entity.entity(book, MediaType.APPLICATION_JSON),
						Book.class);

		Book bookPersisted = fb.get();

		String bookId = bookPersisted.getId();
		assertThat(bookId, notNullValue());

		client.target(REST_SERVICE_URL).path("/{bookId}")
				.resolveTemplate("bookId", bookId).request().async().delete()
				.get();

		Future<List<Book>> bookRequest = client.target(REST_SERVICE_URL)
				.request().async().get(new GenericType<List<Book>>() {
				});
		List<Book> books2 = bookRequest.get();
		assertThat(books2.isEmpty(), equalTo(true));
	}

	@Test
	public void invocationCallbackExample() throws Exception {
		Book book = mockBook();
		client.target(REST_SERVICE_URL)
				.request()
				.async()
				.post(Entity.entity(book, MediaType.APPLICATION_JSON),
						new InvocationCallback<Book>() {
							@Override
							public void completed(final Book bookPersisted) {
								System.out.println("book saved: "
										+ bookPersisted);
								assertThat(bookPersisted.getId(),
										notNullValue());
							}

							@Override
							public void failed(final Throwable throwable) {
								throwable.printStackTrace();
							}
						}).get();

		client.target(REST_SERVICE_URL).request().async()
				.get(new InvocationCallback<List<Book>>() {
					@Override
					public void completed(final List<Book> books) {
						System.out.println(books.size() + " books received");
						assertThat(books.size(), greaterThanOrEqualTo(1));
					}

					@Override
					public void failed(final Throwable throwable) {
						throwable.printStackTrace();
					}
				}).get();
	}

}