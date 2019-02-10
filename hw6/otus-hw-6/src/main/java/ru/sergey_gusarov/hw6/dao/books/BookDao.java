package ru.sergey_gusarov.hw6.dao.books;

import ru.sergey_gusarov.hw6.domain.books.Book;
import ru.sergey_gusarov.hw6.exception.DaoException;

import java.util.List;

public interface BookDao {
    static final String TABLE_NAME = "BOOK";
    static final String ID_COLUMN = "ID";
    static final String TITLE_COLUMN = "TITLE";

    int count();

    int insertWithAuthorAndGenre(Book book) throws DaoException;

    int insert(Book book);

    Book getById(int id) throws DaoException;

    List<Book> findAll();

    int update(Book book);

    int delete(Book book);

}
