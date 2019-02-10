package ru.sergey_gusarov.hw12.service;

import ru.sergey_gusarov.hw12.domain.books.Book;

public interface CommentsService {
    void AddBookComments(Book book, String text);
    void AddBookComments(String bookId, String commentText);
}
