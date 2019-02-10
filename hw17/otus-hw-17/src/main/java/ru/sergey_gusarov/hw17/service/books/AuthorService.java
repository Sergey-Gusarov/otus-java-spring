package ru.sergey_gusarov.hw17.service.books;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.sergey_gusarov.hw17.domain.books.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    Mono<Author> getById(String id);

    Mono<Author> findById(String id);

    Mono<Author> findByName(String name);

    Mono<Void> deleteById(String id);

    Flux<Author> deleteByIdAndRetList(String id);

    Mono<Author> save(Author author);

    Flux<Author> findAll();
}
