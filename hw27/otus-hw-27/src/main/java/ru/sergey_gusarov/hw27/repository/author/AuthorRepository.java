package ru.sergey_gusarov.hw27.repository.author;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.sergey_gusarov.hw27.domain.books.Author;

import java.util.List;

@Repository
public interface AuthorRepository extends MongoRepository<Author, String>, AuthorRepositoryCustom {
    List<Author> findByName(String name);
}
