package ru.sergey_gusarov.hw23.service;

import ru.sergey_gusarov.hw23.domain.books.Book;

public interface CommentsService {
    void AddBookComments(Book book, String text);

    void AddBookComments(long bookId, String commentText);
}
