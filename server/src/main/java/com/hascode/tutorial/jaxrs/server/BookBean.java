package com.hascode.tutorial.jaxrs.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Singleton;
import javax.ejb.Startup;

import com.hascode.tutorial.jaxrs.entity.Book;

@Singleton
@Startup
public class BookBean implements BookRepository {
	private final Map<String, Book> books = new HashMap<>();

	@Override
	public void saveBook(final Book book) {
		books.put(book.getId(), book);
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
}
