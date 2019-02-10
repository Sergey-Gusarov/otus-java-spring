package ru.sergey_gusarov.hw10.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import ru.sergey_gusarov.hw10.dao.books.BookRepository;
import ru.sergey_gusarov.hw10.dao.books.dict.DictAuthorRepository;
import ru.sergey_gusarov.hw10.dao.books.dict.DictGenreRepository;
import ru.sergey_gusarov.hw10.domain.books.Author;
import ru.sergey_gusarov.hw10.domain.books.Book;
import ru.sergey_gusarov.hw10.domain.books.Genre;


@ShellComponent
public class BookShell {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private DictAuthorRepository dictAuthorRepository;
    @Autowired
    private DictGenreRepository dictGenreRepository;

    @ShellMethod("Book count")
    public long bookCount() {
        return bookRepository.count();
    }

    @ShellMethod("Book get by id")
    public String bookGetById(@ShellOption long id) {
        return bookRepository.getById(id).toString();
    }

    @ShellMethod("Book get by id")
    public String bookGetByTitle(@ShellOption String title) {
        return bookRepository.getByTitle(title).toString();
    }

    @ShellMethod("Book delete by id")
    public void bookDeleteById(@ShellOption long id) {
        bookRepository.deleteById(id);
    }

    @ShellMethod("Book insert")
    @Transactional
    public void bookInsert(@ShellOption String title, @ShellOption String authorName, @ShellOption String genreName) {
        Book book = new Book();
        book.setTitle(title);
        Author author = dictAuthorRepository.getByName(authorName);
        if (author == null)
            author = new Author(authorName);

        book.getAuthors().add(author);
        Genre genre = dictGenreRepository.getByName(genreName);
        if (genre == null)
            genre = new Genre(genreName);
        book.getGenres().add(genre);

        bookRepository.save(book);
    }

    @ShellMethod("Book list")
    public String bookList() {
        return bookRepository.findAll().toString();
    }
}

