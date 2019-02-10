package ru.sergey_gusarov.hw21.service.books;

import ru.sergey_gusarov.hw21.domain.books.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    Optional<Author> getById(String id);

    void deleteById(String id);

    Author save(Author author);

    List<Author> findAll();
}
