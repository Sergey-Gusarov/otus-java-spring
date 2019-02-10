package ru.sergey_gusarov.hw7_8.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.sergey_gusarov.hw7_8.dao.books.BookRepository;
import ru.sergey_gusarov.hw7_8.dao.books.dict.DictAuthorRepository;
import ru.sergey_gusarov.hw7_8.dao.books.dict.DictGenreRepository;
import ru.sergey_gusarov.hw7_8.domain.books.Author;
import ru.sergey_gusarov.hw7_8.domain.books.Book;
import ru.sergey_gusarov.hw7_8.domain.books.Genre;
import ru.sergey_gusarov.hw7_8.service.BookService;

import javax.persistence.NoResultException;


@ShellComponent
public class BookShell {
    @Autowired
    private BookService bookService;

    @ShellMethod("Book count")
    public long bookCount() {
        return bookService.bookCount();
    }

    @ShellMethod("Book get by id")
    public String bookGetById(@ShellOption long id) {
        return bookService.bookGetById(id);
    }

    @ShellMethod("Book get by id")
    public String bookGetByTitle(@ShellOption String title) {
        return bookService.bookGetByTitle(title);
    }

    @ShellMethod("Book delete by id")
    public void bookDeleteById(@ShellOption long id) {
        bookService.bookDeleteById(id);
    }

    @ShellMethod("Book insert")
    public void bookInsert(@ShellOption String title, @ShellOption String authorName, @ShellOption String genreName) {
        bookService.bookInsertTitleAuthorGenre(title, authorName, genreName);
    }

    @ShellMethod("Book list")
    public String bookList() {
        return bookService.bookList();
    }
}

