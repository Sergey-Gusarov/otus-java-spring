package ru.sergey_gusarov.hw15.service.books;

import ru.sergey_gusarov.hw15.domain.books.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    Optional<Author> getById(String id);

    Optional<Author> findByName(String name);

    void deleteById(String id);

    Author save(Author author);

    List<Author> findAll();
}
