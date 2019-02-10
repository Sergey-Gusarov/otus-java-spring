package ru.sergey_gusarov.hw17.repository.author;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.sergey_gusarov.hw17.domain.books.Author;

@Repository
public interface AuthorRepository extends ReactiveCrudRepository<Author, String>{
    Flux<Author> findByName(String name);
}
