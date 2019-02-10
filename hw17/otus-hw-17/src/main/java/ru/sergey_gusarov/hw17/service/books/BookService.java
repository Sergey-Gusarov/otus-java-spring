package ru.sergey_gusarov.hw17.service.books;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.sergey_gusarov.hw17.domain.books.Author;
import ru.sergey_gusarov.hw17.domain.books.Book;
import ru.sergey_gusarov.hw17.domain.books.Genre;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Mono<Long> count();

    Mono<Book> getById(String id);

    Mono<Book> findById(String id);

    Flux<Book> findByTitle(String title);

    Mono<Void> deleteById(String id);

    Mono<Book> save(Book book);

    Mono<Book> add(String title, List<Author> authors, List<Genre> genres);

    Flux<Book> findAll();

    Mono<Void> deleteAll();

    Mono<Void> addComment(String id, String comment);

    Flux<Book> findByAuthorName(String authorName);
}
