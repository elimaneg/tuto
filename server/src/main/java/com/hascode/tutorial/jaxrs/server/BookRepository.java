package com.hascode.tutorial.jaxrs.server;

import java.util.List;

import com.hascode.tutorial.jaxrs.entity.Book;

public interface BookRepository {

	Book saveBook(final Book book);

	void deleteBook(final String id);

	List<Book> getAll();

	Book getById(String id);

}