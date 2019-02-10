package ru.sergey_gusarov.hw12.repository.books;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.sergey_gusarov.hw12.domain.books.Author;
import ru.sergey_gusarov.hw12.domain.books.Book;
import ru.sergey_gusarov.hw12.domain.books.BookComment;
import ru.sergey_gusarov.hw12.domain.books.Genre;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class BookCommentRepositoryTest {
    @Autowired
    private BookCommentRepository bookCommentRepository;
    @Autowired
    private BookRepository bookRepository;

    private Book dummyBook1Genre1Author2() {
        Set<Genre> genres = new HashSet<>(1);
        genres.add(new Genre("Genre1"));
        Set<Author> authors = new HashSet<>(2);
        authors.add(new Author("Author1"));
        authors.add(new Author("Author2"));
        Book book = new Book("Title1", genres, authors);
        return book;
    }

    private Book addToBookComment(Book book, String comment) {
        book.getBookComments().add(createBookComment(book, comment));
        return book;
    }

    private BookComment createBookComment(Book book, String comment) {
        BookComment bookComment = new BookComment();
        bookComment.setText(comment);
        bookComment.setBook(book);
        return bookComment;
    }

    @BeforeEach
    @Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    private void reSetupSchema(){

    };

    @Test
    @DisplayName("Count")
    void count() {
        Book book = dummyBook1Genre1Author2();
        book = addToBookComment(book, "Comment01");
        book = addToBookComment(book, "Comment02");
        bookRepository.save(book);
        long count = bookCommentRepository.count();
        assertEquals(2L, count);
    }

    @Test
    @DisplayName("Book comment insert")
    void insert() {
        String commentStr = "Comment1";
        Book originalBook = dummyBook1Genre1Author2();
        originalBook = bookRepository.save(originalBook);
        BookComment bookComment = new BookComment();
        bookComment.setText(commentStr);
        bookComment.setBook(originalBook);
        bookCommentRepository.save(bookComment);
        List<BookComment> bookComments = bookCommentRepository.findAll();
        BookComment bookCommentFromDb = bookCommentRepository.findById(bookComments.get(0).getId()).get();
        assertEquals(commentStr, bookCommentFromDb.getText());
    }

    @Test
    @DisplayName("Book comment by Id")
    void getById() {
        String commentStr = "Comment2";
        Book originalBook = dummyBook1Genre1Author2();
        originalBook = bookRepository.save(originalBook);
        BookComment bookComment = new BookComment();
        bookComment.setText(commentStr);
        bookComment.setBook(originalBook);
        bookCommentRepository.save(bookComment);
        Optional<BookComment> optionalBookComment = bookCommentRepository.findAll().stream().findFirst();
        BookComment bookCommentFromDb = bookCommentRepository.findById(optionalBookComment.get().getId()).get();
        assertEquals(commentStr, bookCommentFromDb.getText());
    }

    @Test
    @DisplayName("Find all")
    void findAll() {
        final int COUNT_ITERATION = 3;
        Book book = dummyBook1Genre1Author2();
        book = bookRepository.save(book);
        List<BookComment> bookComments = new ArrayList<>();

        for (Integer i = 0; i < COUNT_ITERATION; i++) {
            bookComments.add(createBookComment(book, "Comment0" + i.toString()));
            bookCommentRepository.save(bookComments.get(i));
        }
        List<BookComment> bookCommentsFromDb = bookCommentRepository.findAll();
        assertEquals(COUNT_ITERATION, bookCommentsFromDb.size());
        for (Integer i = 0; i < COUNT_ITERATION; i++) {
            Integer finalI = i;
            BookComment bookFromDb = bookCommentsFromDb.stream()
                    .filter(s -> s.getText().equals(bookComments.get(finalI).getText()))
                    .findFirst().get();
            assertEquals(bookComments.get(finalI).getText(), bookFromDb.getText());
            assertEquals(bookComments.get(finalI).getBook().getTitle(), bookFromDb.getBook().getTitle());
        }
    }

    @Test
    @DisplayName("Update")
    void update() {
        String commentStrUpdate = "update comment";
        Book book = dummyBook1Genre1Author2();
        book = bookRepository.save(book);
        BookComment bookComment = createBookComment(book, "Comment001");
        bookCommentRepository.save(bookComment);

        List<BookComment> bookCommentsFromDb = bookCommentRepository.findAll();
        BookComment bookCommentFromDb = bookCommentsFromDb.get(0);
        bookCommentFromDb.setText(commentStrUpdate);
        bookCommentRepository.save(bookCommentFromDb);

        List<BookComment> bookCommentsFromDbUpdate = bookCommentRepository.findAll();
        BookComment bookCommentFromDbUpdate = bookCommentsFromDbUpdate.get(0);
        assertEquals(bookCommentFromDbUpdate.getText(), bookCommentFromDb.getText());
        assertEquals(bookCommentFromDbUpdate.getBook().getTitle(), bookCommentFromDb.getBook().getTitle());
    }

    @Test
    @DisplayName("Delete")
    void delete() {
        final long COUNT_ITERATION = 3;
        Book book = dummyBook1Genre1Author2();
        book = bookRepository.save(book);
        List<BookComment> bookComments = new ArrayList<>();

        for (Integer i = 0; i < COUNT_ITERATION; i++) {
            bookComments.add(createBookComment(book, "Comment0" + i.toString()));
            bookCommentRepository.save(bookComments.get(i));
        }
        List<BookComment> bookCommentsFromDb = bookCommentRepository.findAll();
        assertEquals(COUNT_ITERATION, bookCommentsFromDb.size());

        BookComment bookCommentsForDelete = bookCommentRepository.findById(bookCommentsFromDb.get(1).getId()).get();
        bookCommentRepository.delete(bookCommentsForDelete);
        long count = bookCommentRepository.count();
        assertEquals(COUNT_ITERATION - 1, count);

        //Проверям, что удалили то, что нужно
        BookComment bookComment0 = bookCommentRepository.findById(bookComments.get(0).getId()).get();
        BookComment bookComment2 = bookCommentRepository.findById(bookComments.get(2).getId()).get();
        assertEquals(bookComments.get(0).getText(), bookComment0.getText());
        assertEquals(bookComments.get(2).getText(), bookComment2.getText());

        // Попытка удалить несуществующую запись
        Throwable invalidDataAccessApiUsageException = assertThrows(InvalidDataAccessApiUsageException.class, () -> {
                    BookComment bookCommentCantDelete = bookCommentRepository.findById(bookComments.get(1).getId()).get();
                    bookCommentRepository.delete(bookCommentCantDelete);
                }
        );
        assertEquals("The entity must not be null!; nested exception is java.lang.IllegalArgumentException: The entity must not be null!",
                invalidDataAccessApiUsageException.getMessage(), "Doesn't throw exception");

        bookCommentsForDelete = bookCommentRepository.findById(bookComments.get(0).getId()).get();
        bookCommentRepository.delete(bookCommentsForDelete);
        bookCommentsForDelete = bookCommentRepository.findById(bookComments.get(2).getId()).get();
        bookCommentRepository.delete(bookCommentsForDelete);
        count = bookCommentRepository.count();
        assertEquals(0L, count);
    }

    @Test
    @DisplayName("Delete by id")
    void deleteById() {
        final long COUNT_ITERATION = 3;
        Book bookOrigin = dummyBook1Genre1Author2();
        bookOrigin = bookRepository.save(bookOrigin);
        List<BookComment> bookComments = new ArrayList<>();

        for (Integer i = 0; i < COUNT_ITERATION; i++) {
            bookComments.add(createBookComment(bookOrigin, "Comment0" + i.toString()));
            bookCommentRepository.save(bookComments.get(i));
        }
        List<BookComment> bookCommentsFromDb = bookCommentRepository.findAll();
        assertEquals(COUNT_ITERATION, bookCommentsFromDb.size());

        Book book = bookRepository.findById("1").get();

        BookComment bookCommentsForDelete = bookCommentRepository.findById(bookCommentsFromDb.get(1).getId()).get();
        bookCommentRepository.deleteById(bookCommentsForDelete.getId());
        long count = bookCommentRepository.count();
        assertEquals(COUNT_ITERATION - 1, count);

        //Проверям, что удалили то, что нужно
        BookComment bookComment0 = bookCommentRepository.findById(bookComments.get(0).getId()).get();
        BookComment bookComment2 = bookCommentRepository.findById(bookComments.get(2).getId()).get();
        assertEquals(bookComments.get(0).getText(), bookComment0.getText());
        assertEquals(bookComments.get(2).getText(), bookComment2.getText());

        // Попытка удалить несуществующую запись
        Throwable illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
                    BookComment bookCommentCantDelete = bookCommentRepository.findById(bookComments.get(1).getId()).get();
                    if (bookCommentCantDelete == null)
                        throw new IllegalArgumentException("attempt to create delete event with null entity");
                    bookCommentRepository.deleteById(bookCommentCantDelete.getId());
                }
        );
        assertEquals("attempt to create delete event with null entity",
                illegalArgumentException.getMessage(), "Doesn't throw exception");
    }
}