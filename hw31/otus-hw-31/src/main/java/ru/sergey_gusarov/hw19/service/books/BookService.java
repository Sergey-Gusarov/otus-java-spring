package ru.sergey_gusarov.hw19.service.books;

import ru.sergey_gusarov.hw19.domain.books.Author;
import ru.sergey_gusarov.hw19.domain.books.Book;
import ru.sergey_gusarov.hw19.domain.books.Genre;

import java.util.List;
import java.util.Optional;

public interface BookService {
    long count();

    Optional<Book> findById(String id);

    List<Book> findByTitle(String title);

    void deleteById(String id);

    Book save(Book book);

    Book add(String title, List<Author> authors, List<Genre> genres);

    List<Book> findAll();

    void deleteAll();

    void addComment(String id, String comment);

    List<Book> findByAuthorName(String authorName);
}
