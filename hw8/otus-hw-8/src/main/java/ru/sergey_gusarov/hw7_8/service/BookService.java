package ru.sergey_gusarov.hw7_8.service;

public interface BookService {
    long bookCount();

    String bookGetById(long id);

    String bookGetByTitle(String title);

    void bookDeleteById(long id);

    void bookInsertTitleAuthorGenre(String title, String authorName, String genreName);

    String bookList();
}
