package ru.sergey_gusarov.hw19.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.sergey_gusarov.hw19.domain.books.Author;
import ru.sergey_gusarov.hw19.domain.books.Genre;
import ru.sergey_gusarov.hw19.service.books.BookService;

import java.util.ArrayList;
import java.util.List;


@ShellComponent
public class BookShell {
    private final BookService bookService;


    public BookShell(BookService bookService) {
        this.bookService = bookService;

    }

    @ShellMethod("Book count")
    public long bookCount() {
        return bookService.count();
    }

    @ShellMethod("Book get by id")
    public String bookGetById(@ShellOption String id) {
        return bookService.findById(id).get().toString();
    }

    @ShellMethod("Book get by id")
    public String bookGetByTitle(@ShellOption String title) {
        return bookService.findByTitle(title).toString();
    }

    @ShellMethod("Book delete by id")
    public void bookDeleteById(@ShellOption String id) {
        bookService.deleteById(id);
    }

    @ShellMethod("Book insert")
    public void bookInsert(@ShellOption String title, @ShellOption String authorName, @ShellOption String genreName) {
        List<Author> authors = new ArrayList<>(1);
        authors.add(new Author(authorName));
        List<Genre> genres = new ArrayList<>(1);
        genres.add(new Genre(genreName));
        bookService.add(title, authors, genres);
    }

    @ShellMethod("Book list")
    public String bookList() {
        return bookService.findAll().toString();
    }

    @ShellMethod("Book add comment")
    public void bookAddComment(@ShellOption String bookId, @ShellOption String commentText) {
        bookService.addComment(bookId, commentText);
    }

    @ShellMethod("Books by author")
    public String booksByAuthor(@ShellOption String authorName) {
        return bookService.findByAuthorName(authorName).toString();
    }


}

