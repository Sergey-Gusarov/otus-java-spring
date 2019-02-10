package ru.sergey_gusarov.hw23.service;


import org.junit.jupiter.api.BeforeEach;
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
import ru.sergey_gusarov.hw23.dao.books.BookRepository;
import ru.sergey_gusarov.hw23.domain.books.Author;
import ru.sergey_gusarov.hw23.domain.books.Book;
import ru.sergey_gusarov.hw23.domain.books.BookComment;
import ru.sergey_gusarov.hw23.domain.books.Genre;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
//@SpringBootTest(classes={Main.class})
@ComponentScan("ru.sergey_gusarov.hw23")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.NONE)
class CommentsServiceTest {
    @Autowired
    private CommentsService commentsService;

    @Autowired
    private BookRepository bookRepository;

    private Book dummyBook1Genre1Author2() {
        Book book = new Book();
        book.setTitle("Title1");

        Genre genre = new Genre();
        List<Genre> genres = new ArrayList<>(1);
        genres.add(new Genre("Genre1"));
        List<Author> authors = new ArrayList<>(2);
        authors.add(new Author("Author1"));
        authors.add(new Author("Author2"));

        book.setAuthors(authors);
        book.setGenres(genres);
        return book;
    }

    @BeforeEach
    @Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    private void reSetupSchema() {

    }

    @Test
    @DisplayName("Add book comments by book id")
    void addBookComments() {
        final String commentStr = "Comment 1";
        Book book = dummyBook1Genre1Author2();
        book = bookRepository.save(book);
        commentsService.AddBookComments(book.getId(), commentStr);
        Book bookFromDb = bookRepository.getById(book.getId());
        Optional<BookComment> bookComment = bookFromDb.getBookComments().stream().findFirst();
        assertEquals(bookComment.get().getText(), commentStr);
    }
}