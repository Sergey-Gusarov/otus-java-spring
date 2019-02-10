package ru.sergey_gusarov.hw12.service;

import org.springframework.stereotype.Service;
import ru.sergey_gusarov.hw12.repository.books.BookRepository;
import ru.sergey_gusarov.hw12.domain.books.Book;
import ru.sergey_gusarov.hw12.domain.books.BookComment;

import java.util.Optional;

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
    public void AddBookComments(String bookId, String commentText) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        Book book = bookOptional.get();
        BookComment bookComment = new BookComment();
        bookComment.setText(commentText);
        bookComment.setBook(book);
        book.getBookComments().add(bookComment);
        bookRepository.save(book);
    }
}
