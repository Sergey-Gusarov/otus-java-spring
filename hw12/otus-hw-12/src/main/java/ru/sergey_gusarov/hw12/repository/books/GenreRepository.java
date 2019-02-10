package ru.sergey_gusarov.hw12.repository.books;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import ru.sergey_gusarov.hw12.domain.books.Genre;

import java.util.List;

public interface GenreRepository extends MongoRepository<Genre, String> {
    Genre findByName(String name);
}
