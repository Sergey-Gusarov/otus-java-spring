package ru.sergey_gusarov.hw10.service;

import org.springframework.stereotype.Service;
import ru.sergey_gusarov.hw10.dao.books.BookRepository;
import ru.sergey_gusarov.hw10.domain.books.Book;
import ru.sergey_gusarov.hw10.domain.books.BookComment;

@Service
public class CommentsServiceIImpl implements CommentsService {

    private final BookRepository bookRepository;

    public CommentsServiceIImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void AddBookComments(Book book, String text) {
        BookComment bookComment = new BookComment();
        bookComment.setText(text);
        bookComment.setBook(book);
        book.getBookComments().add(bookComment);
        bookRepository.save(book);
    }

    @Override
    public void AddBookComments(long bookId, String commentText) {
        Book book = bookRepository.getById(bookId);
        BookComment bookComment = new BookComment();
        bookComment.setText(commentText);
        bookComment.setBook(book);
        book.getBookComments().add(bookComment);
        bookRepository.save(book);
    }
}
