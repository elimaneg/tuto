package com.hascode.tutorial.jaxrs.server;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.hascode.tutorial.jaxrs.entity.Book;

@Stateless
@Path("/book")
public class BookStoreService {
	@EJB
	private BookRepository bookRepository;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveBook(final Book book) {
		Book bookPersisted = bookRepository.saveBook(book);
		return Response.ok(bookPersisted).build();
	}

	@DELETE
	@Path("/{id}")
	public Response deleteBook(final @PathParam("id") String id) {
		bookRepository.deleteBook(id);
		return Response.ok().build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		List<Book> books = bookRepository.getAll();
		GenericEntity<List<Book>> bookWrapper = new GenericEntity<List<Book>>(
				books) {
		};
		return Response.ok(bookWrapper).build();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(final @PathParam("id") String id) {
		Book book = bookRepository.getById(id);
		return Response.ok(book).build();
	}
}
