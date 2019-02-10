package ru.sergey_gusarov.hw7_8.dao.books.dict;

import ru.sergey_gusarov.hw7_8.domain.books.Genre;

import java.util.List;

public interface DictGenreRepository {
    Long count();

    void insert(Genre genre);

    Genre getById(long id);

    Genre getByName(String name);

    List<Genre> findAll();

    Genre update(Genre genre);

    void delete(Genre genre);

    void deleteById(long id);

    Genre save(Genre genre);
}
