package ru.sergey_gusarov.hw7_8.dao.books;

import ru.sergey_gusarov.hw7_8.domain.books.BookComment;

import java.util.List;

public interface BookCommentRepository {
    Long count();

    void insert(BookComment bookComment);

    BookComment getById(long id);

    List<BookComment> findAll();

    BookComment update(BookComment bookComment);

    void delete(BookComment bookComment);

    void deleteById(long id);
}
