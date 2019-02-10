package ru.sergey_gusarov.hw12.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.sergey_gusarov.hw12.repository.books.BookCommentRepository;
import ru.sergey_gusarov.hw12.repository.books.BookRepository;
import ru.sergey_gusarov.hw12.service.CommentsService;


@ShellComponent
public class BookCommentShell {
    private final BookCommentRepository bookCommentRepository;
    private final CommentsService commentsService;

    public BookCommentShell(BookCommentRepository bookCommentRepository, CommentsService commentsService) {
        this.bookCommentRepository = bookCommentRepository;
        this.commentsService = commentsService;
    }

    @ShellMethod("BookComment count")
    public long bookCommentCount() {
        return bookCommentRepository.count();
    }

    @ShellMethod("BookComment get by id")
    public String bookCommentGetById(@ShellOption String id) {
        return bookCommentRepository.findById(id).toString();
    }

    @ShellMethod("BookComment delete by id")
    public void bookCommentDeleteById(@ShellOption String id) {
        bookCommentRepository.deleteById(id);
    }

    @ShellMethod("BookComment save")
    public void bookCommentInsert(@ShellOption String bookId, @ShellOption String commentText) {
        commentsService.AddBookComments(bookId, commentText);
    }

    @ShellMethod("BookComment list")
    public String bookCommentList() {
        return bookCommentRepository.findAll().toString();
    }
}