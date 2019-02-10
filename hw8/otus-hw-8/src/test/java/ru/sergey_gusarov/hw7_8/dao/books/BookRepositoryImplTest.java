package ru.sergey_gusarov.hw7_8.dao.books;

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
import ru.sergey_gusarov.hw7_8.domain.books.Author;
import ru.sergey_gusarov.hw7_8.domain.books.Book;
import ru.sergey_gusarov.hw7_8.domain.books.BookComment;
import ru.sergey_gusarov.hw7_8.domain.books.Genre;

import javax.persistence.NoResultException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ComponentScan("ru.sergey_gusarov.hw7_8")
class BookRepositoryImplTest {
    private final static String COMMENT_FOR_ADD_1 = "comment1";

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

    private Book dummyBook3Genre1AuthorName3() {
        Set<Genre> genres = new HashSet<>(1);
        genres.add(new Genre("Genre1"));
        Set<Author> authors = new HashSet<>(1);
        authors.add(new Author("Author3"));
        Book book = new Book("Title3", genres, authors);
        return book;
    }

    @BeforeEach
    @Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    private void reSetupSchema(){

    };

    @Test
    @DisplayName("Count")
    void count() {
        bookRepository.insert(new Book("Title1"));
        bookRepository.insert(new Book("Title2"));
        bookRepository.insert(new Book("Title3"));
        long count = bookRepository.count();
        assertEquals(3L, count);
    }

    @Test
    @DisplayName("Insert")
    void insert() {
        Book original = dummyBook1Genre1Author2();
        bookRepository.insert(original);
        Optional<Book> fromDbOptional = Optional.ofNullable(bookRepository.getByTitle(original.getTitle()));
        Book fromDb = fromDbOptional.get();
        testBook(original, fromDb);
    }

    @Test
    @DisplayName("Get by id")
    void getById() {
        Book original = dummyBook3Genre1AuthorName3();
        bookRepository.insert(original);
        Optional<Book> fromDbOptional = Optional.ofNullable(bookRepository.getByTitle(original.getTitle()));
        Book fromDb = fromDbOptional.get();
        fromDbOptional = Optional.ofNullable(bookRepository.getById(fromDb.getId()));
        fromDb = fromDbOptional.get();
        testBook(original, fromDb);
    }

    @Test
    @DisplayName("Get by title")
    void getByTitle() {
        Book original = dummyBook1Genre1Author2();
        bookRepository.insert(original);
        Optional<Book> fromDbOptional = Optional.ofNullable(bookRepository.getByTitle(original.getTitle()));
        Book fromDb = fromDbOptional.get();
        testBook(original, fromDb);
    }

    @Test
    @DisplayName("Find all")
    void findAll() {
        final int COUNT_ITERATION = 3;
        List<Book> books = new ArrayList<>();

        for (Integer i = 0; i < COUNT_ITERATION; i++) {
            books.add(new Book("Title" + i.toString()));
            bookRepository.insert(books.get(i));
        }
        List<Book> booksFromDb = bookRepository.findAll();
        assertEquals(COUNT_ITERATION, booksFromDb.size());
        for (Integer i = 0; i < COUNT_ITERATION; i++) {
            Integer finalI = i;
            Book bookFromDb = booksFromDb.stream()
                    .filter(s -> s.getTitle().equals(books.get(finalI).getTitle()))
                    .findFirst().get();
            testBook(books.get(finalI), bookFromDb);
        }
    }

    @Test
    @DisplayName("Update")
    void update() {
        String updatedTitle = "Update title";
        Book book = dummyBook1Genre1Author2();
        bookRepository.insert(book);
        Book bookForUpdate = bookRepository.getByTitle(book.getTitle());
        bookForUpdate.setTitle(updatedTitle);
        bookRepository.update(bookForUpdate);
        Book updatedBook = bookRepository.getByTitle(updatedTitle);
        testBook(bookForUpdate, updatedBook);
    }

    @Test
    @DisplayName("Delete")
    void delete() {
        final int COUNT_ITERATION = 3;
        List<Book> books = new ArrayList<>();

        for (Integer i = 0; i < COUNT_ITERATION; i++) {
            books.add(new Book("Title" + i.toString()));
            bookRepository.insert(books.get(i));
        }
        long count = bookRepository.count();
        assertEquals(3L, count);

        Book bookForDelete = bookRepository.getByTitle(books.get(1).getTitle());
        bookRepository.delete(bookForDelete);
        count = bookRepository.count();
        assertEquals(2L, count);

        //Проверям, что удалили то, что нужно
        Book book0 = bookRepository.getByTitle(books.get(0).getTitle());
        Book book2 = bookRepository.getByTitle(books.get(2).getTitle());
        testBook(books.get(0), book0);
        testBook(books.get(2), book2);

        // Попытка удалить несуществующую запись
        Throwable noResultException = assertThrows(NoResultException.class, () -> {
                    Book bookCantDelete = bookRepository.getByTitle(books.get(1).getTitle());
                    bookRepository.delete(bookCantDelete);
                }
        );
        assertEquals("No entity found for query",
                noResultException.getMessage(), "Don't throw exception");

        bookForDelete = bookRepository.getByTitle(books.get(0).getTitle());
        bookRepository.delete(bookForDelete);
        bookForDelete = bookRepository.getByTitle(books.get(2).getTitle());
        bookRepository.delete(bookForDelete);

        count = bookRepository.count();
        assertEquals(0L, count);
    }

    @Test
    @DisplayName("Delete by id")
    void deleteById() {
        final int COUNT_ITERATION = 3;
        List<Book> books = new ArrayList<>();

        for (Integer i = 0; i < COUNT_ITERATION; i++) {
            books.add(new Book("Title" + i.toString()));
            bookRepository.insert(books.get(i));
        }
        long count = bookRepository.count();
        assertEquals(3L, count);

        List<Book> booksFromDb = bookRepository.findAll();
        bookRepository.deleteById(booksFromDb.get(1).getId());
        count = bookRepository.count();
        assertEquals(2L, count);

        //Проверям, что удалили то, что нужно
        Book book0 = bookRepository.getById(booksFromDb.get(0).getId());
        Book book2 = bookRepository.getById(booksFromDb.get(2).getId());
        testBook(books.get(0), book0);
        testBook(books.get(2), book2);

        // Попытка удалить несуществующую запись
        Throwable illegalArgumentException = assertThrows(IllegalArgumentException.class, () ->
                bookRepository.deleteById(booksFromDb.get(1).getId())
        );
        assertEquals("attempt to create delete event with null entity",
                illegalArgumentException.getMessage(), "Don't throw exception");

        bookRepository.deleteById(booksFromDb.get(0).getId());
        bookRepository.deleteById(booksFromDb.get(2).getId());

        count = bookRepository.count();
        assertEquals(0L, count);
    }

    private void testBook(Book book, Book bookFromDb) {
        boolean eqTitle = book.getTitle() == bookFromDb.getTitle();
        boolean eqAuthor = false;
        boolean eqGenres = false;
        Author author = null;
        Author authorFromDb = null;
        Genre genre = null;
        Genre genreFromDb = null;

        if (book.getAuthors() == bookFromDb.getAuthors())
            eqAuthor = true;
        else if ((book.getAuthors() != null && !book.getAuthors().isEmpty()) &&
                (bookFromDb.getAuthors() != null) && !book.getAuthors().isEmpty()) {
            long needId = book.getAuthors().stream().findFirst().get().getId();
            author = book.getAuthors().stream()
                    .filter(s -> s.getId() == needId)
                    .findFirst().get();
            authorFromDb = bookFromDb.getAuthors().stream()
                    .filter(s -> s.getId() == needId)
                    .findFirst().get();
            eqAuthor = author.getName().equals(authorFromDb.getName());
        }
        if (book.getGenres() == bookFromDb.getGenres())
            eqGenres = true;
        else if ((book.getGenres() != null && !book.getGenres().isEmpty()) &&
                (bookFromDb.getGenres() != null && !bookFromDb.getGenres().isEmpty())) {
            long needId = book.getGenres().stream().findFirst().get().getId();
            genre = book.getGenres().stream()
                    .filter(s -> s.getId() == needId)
                    .findFirst().get();
            genreFromDb = bookFromDb.getGenres().stream()
                    .filter(s -> s.getId() == needId)
                    .findFirst().get();
            eqGenres = genre.getName().equals(genreFromDb.getName());
        }
        assertTrue(eqTitle && eqAuthor && eqGenres);
    }


    @Test
    @DisplayName("Add book comment by book")
    void addBookCommentByBook() {
        Book originalBook = dummyBook1Genre1Author2();
        bookRepository.insert(originalBook);
        Optional<Book> fromDbOptional = Optional.ofNullable(bookRepository.getByTitle(originalBook.getTitle()));
        Book fromDb = fromDbOptional.get();

        BookComment bookComment = new BookComment();
        bookComment.setBook(fromDb);
        bookComment.setText(COMMENT_FOR_ADD_1);
        fromDb.getBookComments().add(bookComment);

        Book fromDbUpdated = bookRepository.update(fromDb);
        Optional<BookComment> bookCommentOptional = fromDbUpdated.getBookComments().stream().findFirst();
        if (!bookCommentOptional.isPresent())
            fail("Comment doesn't saved");
        BookComment bookCommentFromDb = bookCommentOptional.get();
        assertEquals(COMMENT_FOR_ADD_1, bookCommentFromDb.getText());
    }
}