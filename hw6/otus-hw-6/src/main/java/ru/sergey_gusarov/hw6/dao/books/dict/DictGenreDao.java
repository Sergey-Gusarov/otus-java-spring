package ru.sergey_gusarov.hw6.dao.books.dict;

import ru.sergey_gusarov.hw6.domain.books.Genre;

import java.util.List;

public interface DictGenreDao {
    static final String TABLE_NAME = "GENRE";
    static final String ID_COLUMN = "ID";
    static final String NAME_COLUMN = "NAME";

    int count();

    int insert(Genre genre);

    Genre getById(int id);

    Genre getByName(String name);

    List<Genre> findAll();

    int update(Genre genre);

    int delete(Genre genre);
}
