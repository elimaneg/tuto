package com.hascode.tutorial.jaxrs.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Singleton;

import com.hascode.tutorial.jaxrs.entity.Book;

@Singleton
public class BookRepository {
	private final Map<String, Book> books = new HashMap<>();

	public void saveBook(final Book book) {
		books.put(book.getId(), book);
	}

	public void deleteBook(final String id) {
		if (books.containsKey(id)) {
			books.remove(id);
		}
	}

	public List<Book> getAll() {
		return new ArrayList<Book>(books.values());
	}
}
