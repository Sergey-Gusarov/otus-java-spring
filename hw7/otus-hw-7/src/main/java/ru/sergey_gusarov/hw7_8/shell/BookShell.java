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

import javax.persistence.NoResultException;


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
    public void bookInsert(@ShellOption String title, @ShellOption String authorName, @ShellOption String genreName) {
        Book book = new Book();
        book.setTitle(title);
        Author author;
        try {
            author = dictAuthorRepository.getByName(authorName);
        }catch (Exception ex ){
            if(ex.getCause() instanceof NoResultException)
                author = new Author(authorName);
            else
                throw ex;
        }
        book.getAuthors().add(author);

        Genre genre;
        try {
            genre = dictGenreRepository.getByName(genreName);
        }catch (Exception ex){
            if(ex.getCause() instanceof NoResultException)
                genre = new Genre(genreName);
            else
                throw ex;
        }
        book.getGenres().add(genre);
        bookRepository.insert(book);
    }

    @ShellMethod("Book list")
    public String bookList() {
        return bookRepository.findAll().toString();
    }
}

