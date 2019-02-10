package ru.sergey_gusarov.hw6.dao.books;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.sergey_gusarov.hw6.domain.books.Author;
import ru.sergey_gusarov.hw6.domain.books.Book;
import ru.sergey_gusarov.hw6.domain.books.Genre;
import ru.sergey_gusarov.hw6.exception.DaoException;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class BookDaoJdbcTest {

    @Autowired
    private BookDaoJdbc bookDaoJdbc;

    private Book dummyBook1Genre1Author2() {
        Set<Genre> genres = new HashSet<>(1);
        genres.add(new Genre(1, "Genre1"));
        Set<Author> authors = new HashSet<>(2);
        authors.add(new Author(1, "Author1"));
        authors.add(new Author(2, "Author2"));
        Book book = new Book(1, "Title1", genres, authors);
        return book;
    }

    private Book dummyBook1Genre1Author2Update() {
        Set<Genre> genres = new HashSet<>(1);
        genres.add(new Genre(1, "Genre1"));
        Set<Author> authors = new HashSet<>(2);
        authors.add(new Author(1, "Author1"));
        authors.add(new Author(2, "Author2"));
        Book book = new Book(1, "Title1Update", genres, authors);
        return book;
    }

    private Book dummyBook2Genre1Author1() {
        Set<Genre> genres = new HashSet<>(1);
        genres.add(new Genre(1, "Genre1"));
        Set<Author> authors = new HashSet<>(1);
        authors.add(new Author(1, "Author1"));
        Book book = new Book(2, "Title2", genres, authors);
        return book;
    }

    private Book dummyBook3Genre1AuthorName3() {
        Set<Genre> genres = new HashSet<>(1);
        genres.add(new Genre(1, "Genre1"));
        Set<Author> authors = new HashSet<>(1);
        authors.add(new Author(3, "Author3"));
        Book book = new Book(3, "Title3", genres, authors);
        return book;
    }

    private Book dummyBookOnly() {
        Book book = new Book(1, "Title1", null, null);
        return book;
    }

    @Test
    @DisplayName("Количество")
    @Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void count() {
        try {
            bookDaoJdbc.insertWithAuthorAndGenre(dummyBook1Genre1Author2());
            bookDaoJdbc.insertWithAuthorAndGenre(dummyBook2Genre1Author1());
            bookDaoJdbc.insertWithAuthorAndGenre(dummyBook3Genre1AuthorName3());
        } catch (DaoException ex) {
            ex.printStackTrace();
            fail("Ошибка добавления");
        }
        assertEquals(3, bookDaoJdbc.count());
    }

    @Test
    @DisplayName("Вставка всех данных")
    @Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void insertWithAuthorAndGenre() {
        Book book = dummyBook3Genre1AuthorName3();
        try {
            bookDaoJdbc.insertWithAuthorAndGenre(book);
        } catch (DaoException ex) {
            ex.printStackTrace();
            fail("Ошибка добавления");
        }
        Book bookFromDb = null;
        try {
            bookFromDb = bookDaoJdbc.getById(book.getId());
        } catch (DaoException ex) {
            ex.printStackTrace();
            fail("Ошибка извлечения");
        }
        testBook(book, bookFromDb);
    }

    @Test
    @DisplayName("Вставка кники без связей")
    @Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void insert() {
        Book book = dummyBookOnly();
        bookDaoJdbc.insert(book);
        Book bookFromDb = null;
        try {
            bookFromDb = bookDaoJdbc.getById(book.getId());
        } catch (DaoException ex) {
            ex.printStackTrace();
            fail("Ошибка добавления");
        }
        boolean eqId = book.getId() == bookFromDb.getId();
        boolean eqTitle = book.getTitle() == bookFromDb.getTitle();

        assertTrue(eqId && eqTitle);
    }

    @Test
    @DisplayName("Взятие по идентификатору")
    @Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void getById() {
        Book book = dummyBook3Genre1AuthorName3();
        Book bookFromDb = null;
        try {
            bookDaoJdbc.insertWithAuthorAndGenre(book);
            bookFromDb = bookDaoJdbc.getById(book.getId());
        } catch (DaoException ex) {
            ex.printStackTrace();
            fail("Ошибка добавления");
        }
        testBook(book, bookFromDb);
    }

    @Test
    @DisplayName("вытаскивание всех книг")
    @Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void findAll() {
        List<Book> books = new ArrayList<>(3);
        List<Book> booksFromDb = null;
        books.add(dummyBook1Genre1Author2());
        books.add(dummyBook2Genre1Author1());
        books.add(dummyBook3Genre1AuthorName3());
        try {
            bookDaoJdbc.insertWithAuthorAndGenre(books.get(0));
            bookDaoJdbc.insertWithAuthorAndGenre(books.get(1));
            bookDaoJdbc.insertWithAuthorAndGenre(books.get(2));
            booksFromDb = bookDaoJdbc.findAll();
        } catch (DaoException ex) {
            ex.printStackTrace();
            fail("Ошибка добавления");
        }

        Book book = books.stream()
                .filter(s -> s.getId() == 1)
                .findFirst().get();
        Book bookFromDb = booksFromDb.stream()
                .filter(s -> s.getId() == 1)
                .findFirst().get();

        testBook(book, bookFromDb);
    }

    private void testBook(Book book, Book bookFromDb) {
        boolean eqId = book.getId() == bookFromDb.getId();
        boolean eqTitle = book.getTitle() == bookFromDb.getTitle();
        boolean eqAuthor = false;
        boolean eqGenres = false;
        Author author = null;
        Author authorFromDb = null;
        Genre genre = null;
        Genre genreFromDb = null;

        if(book.getAuthors() == bookFromDb.getAuthors())
            eqAuthor = true;
        else if((book.getAuthors() != null && !book.getAuthors().isEmpty()) &&
                (bookFromDb.getAuthors() != null) && !book.getAuthors().isEmpty()) {
            int needId =  book.getAuthors().stream().findFirst().get().getId();
            author = book.getAuthors().stream()
                    .filter(s -> s.getId() == needId)
                    .findFirst().get();
            authorFromDb = bookFromDb.getAuthors().stream()
                    .filter(s -> s.getId() == needId)
                    .findFirst().get();
            eqAuthor = author.getName().equals(authorFromDb.getName());
        }
        if(book.getGenres() == bookFromDb.getGenres())
            eqGenres = true;
        else if((book.getGenres() != null && !book.getGenres().isEmpty()) &&
                (bookFromDb.getGenres() != null && !bookFromDb.getGenres().isEmpty())) {
            int needId =  book.getGenres().stream().findFirst().get().getId();
            genre = book.getGenres().stream()
                    .filter(s -> s.getId() == needId)
                    .findFirst().get();
            genreFromDb = bookFromDb.getGenres().stream()
                    .filter(s -> s.getId() == needId)
                    .findFirst().get();
            eqGenres = genre.getName().equals(genreFromDb.getName());
        }
        assertTrue(eqId && eqTitle && eqAuthor && eqGenres);
    }

    @Test
    @DisplayName("Обновление книги")
    @Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void update() {
        Book book = dummyBook1Genre1Author2();
        try {
            bookDaoJdbc.insertWithAuthorAndGenre(book);
        } catch (DaoException ex) {
            ex.printStackTrace();
            fail("Ошибка добавления");
        }
        Book bookUpdate = dummyBook1Genre1Author2Update();
        bookDaoJdbc.update(bookUpdate);
        Book bookFromDb = null;
        try {
            bookFromDb = bookDaoJdbc.getById(bookUpdate.getId());
        } catch (DaoException ex) {
            ex.printStackTrace();
            fail("Ошибка извлечения");
        }
        testBook(bookUpdate, bookFromDb);
    }

    @Test
    @DisplayName("Удаление книги")
    @Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void delete() {
        List<Book> books = new ArrayList<>(3);

        books.add(dummyBook1Genre1Author2());
        books.add(dummyBook2Genre1Author1());
        books.add(dummyBook3Genre1AuthorName3());
        try {
            bookDaoJdbc.insertWithAuthorAndGenre(books.get(0));
            bookDaoJdbc.insertWithAuthorAndGenre(books.get(1));
            bookDaoJdbc.insertWithAuthorAndGenre(books.get(2));
            bookDaoJdbc.delete(books.get(0));
        } catch (DaoException ex) {
            ex.printStackTrace();
            fail("Ошибка добавления");
        }
        assertEquals(2, bookDaoJdbc.count());
    }
}