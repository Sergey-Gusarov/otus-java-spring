package ru.sergey_gusarov.hw23.mongo.repository.author;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.sergey_gusarov.hw23.mongo.domain.books.Author;

import java.util.List;

@Repository
public interface AuthorMongoRepository extends MongoRepository<Author, String> {
    List<Author> findByName(String name);
}
