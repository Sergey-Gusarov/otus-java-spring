package ru.sergey_gusarov.hw7_8.dao.books;

import ru.sergey_gusarov.hw7_8.domain.books.Book;

import java.util.List;

public interface BookRepository {
    Long count();

    void insert(Book book);

    Book getById(long id);

    Book getByTitle(String title);

    List<Book> findAll();

    Book update(Book book);

    void delete(Book book);

    void deleteById(long id);
}
