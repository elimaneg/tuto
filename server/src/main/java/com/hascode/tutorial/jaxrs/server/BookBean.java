package com.hascode.tutorial.jaxrs.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.ejb.Singleton;
import javax.ejb.Startup;

import com.hascode.tutorial.jaxrs.entity.Book;

@Singleton
@Startup
public class BookBean implements BookRepository {
	private final Map<String, Book> books = new HashMap<>();

	@Override
	public Book saveBook(final Book book) {
		book.setId(UUID.randomUUID().toString());
		books.put(book.getId(), book);
		return book;
	}

	@Override
	public void deleteBook(final String id) {
		if (books.containsKey(id)) {
			books.remove(id);
		}
	}

	@Override
	public List<Book> getAll() {
		return new ArrayList<Book>(books.values());
	}

	@Override
	public Book getById(final String id) {
		return books.get(id);
	}
}
