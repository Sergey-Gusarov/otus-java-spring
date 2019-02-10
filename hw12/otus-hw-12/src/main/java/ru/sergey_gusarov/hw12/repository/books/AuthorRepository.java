package ru.sergey_gusarov.hw12.repository.books;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import ru.sergey_gusarov.hw12.domain.books.Author;

import java.util.List;

public interface AuthorRepository extends MongoRepository<Author, String> {
    Author findByName(String name);
}
