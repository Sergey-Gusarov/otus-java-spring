package ru.sergey_gusarov.hw7_8.service;

import ru.sergey_gusarov.hw7_8.domain.books.Book;

public interface CommentsService {
    void AddBookComments(Book book, String text);
    void AddBookComments(long bookId, String commentText);
}
