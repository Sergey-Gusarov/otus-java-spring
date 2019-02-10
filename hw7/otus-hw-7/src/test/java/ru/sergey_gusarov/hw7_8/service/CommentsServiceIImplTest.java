package ru.sergey_gusarov.hw7_8.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.sergey_gusarov.hw7_8.dao.books.BookCommentRepository;
import ru.sergey_gusarov.hw7_8.dao.books.BookRepositoryJdbc;
import ru.sergey_gusarov.hw7_8.domain.books.Author;
import ru.sergey_gusarov.hw7_8.domain.books.Book;
import ru.sergey_gusarov.hw7_8.domain.books.BookComment;
import ru.sergey_gusarov.hw7_8.domain.books.Genre;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ComponentScan("ru.sergey_gusarov.hw7_8")
class CommentsServiceIImplTest {
    private final static String COMMENT_FOR_ADD_1 = "comment1";

    @Autowired
    private BookRepositoryJdbc bookRepositoryJdbc;
    @Autowired
    private BookCommentRepository bookCommentRepository;
    @Autowired
    private CommentsService commentsService;

    private Book dummyBook1Genre1Author2() {
        Set<Genre> genres = new HashSet<>(1);
        genres.add(new Genre("Genre1"));
        Set<Author> authors = new HashSet<>(2);
        authors.add(new Author("Author1"));
        authors.add(new Author("Author2"));
        Book book = new Book("Title1", genres, authors);
        return book;
    }

    @Test
    @DisplayName("Add book comment by obj")
    @Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void addBookCommentsByObjBook() {
        Book originalBook = dummyBook1Genre1Author2();
        bookRepositoryJdbc.insert(originalBook);
        Book fromDb = getBookfromDb(originalBook);
        commentsService.AddBookComments(fromDb, COMMENT_FOR_ADD_1);

        BookComment bookCommentFromDb = getBookCommentFromDb(originalBook);
        assertEquals(COMMENT_FOR_ADD_1, bookCommentFromDb.getText());
    }

    @Test
    @DisplayName("Add book comment by id")
    @Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void addBookCommentsByIdBook() {
        Book originalBook = dummyBook1Genre1Author2();
        bookRepositoryJdbc.insert(originalBook);
        Book fromDb = getBookfromDb(originalBook);
        commentsService.AddBookComments(fromDb.getId(), COMMENT_FOR_ADD_1);

        BookComment bookCommentFromDb = getBookCommentFromDb(originalBook);
        assertEquals(COMMENT_FOR_ADD_1, bookCommentFromDb.getText());
    }

    private Book getBookfromDb(Book originalBook) {
        Optional<Book> fromDbOptional = Optional.ofNullable(bookRepositoryJdbc.getByTitle(originalBook.getTitle()));
        Book fromDb = fromDbOptional.get();
        return fromDb;
    }

    private BookComment getBookCommentFromDb(Book originalBook) {
        Optional<Book> fromDbOptional2 = Optional.ofNullable(bookRepositoryJdbc.getByTitle(originalBook.getTitle()));
        Book fromDb = fromDbOptional2.get();
        Optional<BookComment> bookCommentOptional = fromDb.getBookComments().stream().findFirst();
        return bookCommentOptional.get();
    }
}