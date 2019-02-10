package ru.sergey_gusarov.hw7_8.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.sergey_gusarov.hw7_8.dao.books.BookCommentRepository;
import ru.sergey_gusarov.hw7_8.dao.books.BookRepository;
import ru.sergey_gusarov.hw7_8.service.CommentsService;


@ShellComponent
public class BookCommentShell {
    private final BookCommentRepository bookCommentRepository;
    private final BookRepository bookRepository;
    private final CommentsService commentsService;

    public BookCommentShell(BookCommentRepository bookCommentRepository, BookRepository bookRepository, CommentsService commentsService) {
        this.bookCommentRepository = bookCommentRepository;
        this.bookRepository = bookRepository;
        this.commentsService = commentsService;
    }

    @ShellMethod("BookComment count")
    public long bookCommentCount() {
        return bookCommentRepository.count();
    }

    @ShellMethod("BookComment get by id")
    public String bookCommentGetById(@ShellOption long id) {
        return bookCommentRepository.getById(id).toString();
    }

    @ShellMethod("BookComment delete by id")
    public void bookCommentDeleteById(@ShellOption long id) {
        bookCommentRepository.deleteById(id);
    }

    @ShellMethod("BookComment insert")
    public void bookCommentInsert(@ShellOption long bookId, @ShellOption String commentText) {
        commentsService.AddBookComments(bookId, commentText);
    }

    @ShellMethod("BookComment list")
    public String bookCommentList() {
        return bookCommentRepository.findAll().toString();
    }
}