package com.hascode.tutorial.jaxrs.server;

import java.util.List;

import com.hascode.tutorial.jaxrs.entity.Book;

public interface BookRepository {

	public abstract void saveBook(final Book book);

	public abstract void deleteBook(final String id);

	public abstract List<Book> getAll();

}