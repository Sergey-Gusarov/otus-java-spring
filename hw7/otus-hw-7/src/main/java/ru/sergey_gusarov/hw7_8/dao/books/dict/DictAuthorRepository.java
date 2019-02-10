package ru.sergey_gusarov.hw7_8.dao.books.dict;

import ru.sergey_gusarov.hw7_8.domain.books.Author;

import java.util.List;

public interface DictAuthorRepository {
    Long count();

    void insert(Author author);

    Author getById(long id);

    Author getByName(String name);

    List<Author> findAll();

    Author update(Author author);

    void delete(Author author);

    void deleteById(long id);

    Author save(Author author);
}
