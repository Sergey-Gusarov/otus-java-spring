package ru.sergey_gusarov.hw17.repository.book;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.sergey_gusarov.hw17.domain.books.Book;

import java.util.List;

@Repository
public interface BookRepository extends ReactiveCrudRepository<Book, String> {
    Flux<Book> findByTitle(String title);

    @Query(value = "{ 'authors.id' : ?0 }")
    Flux<Book> findByAuthorId(String authorId);
}
