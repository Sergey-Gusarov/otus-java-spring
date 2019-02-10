package ru.sergey_gusarov.hw15.repository.book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.sergey_gusarov.hw15.domain.books.Author;
import ru.sergey_gusarov.hw15.domain.books.Book;
import ru.sergey_gusarov.hw15.domain.books.Genre;
import ru.sergey_gusarov.hw15.repository.author.AuthorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataMongoTest
@SpringBootTest
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private MongoOperations mongoOperations;

    private Book dummyBook3Genre1AuthorName3(boolean isSaveAuthors) {
        List<Genre> genres = new ArrayList<>(1);
        genres.add(new Genre("Genre1"));
        List<Author> authors = new ArrayList<>(1);
        authors.add(new Author("Author3"));
        authors.add(new Author("Author4"));
        if(isSaveAuthors)
            authors.forEach(author -> authorRepository.save(author));
        Book book = new Book("Title3");
        book.setAuthors(authors);
        book.setGenres(genres);
        return book;
    }

    @BeforeEach
    private void reSetup() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();
        bookRepository.save(dummyBook3Genre1AuthorName3(true));
    }

    @Test
    @DisplayName("Count")
    void count() {
        bookRepository.save(new Book("Title1"));
        bookRepository.save(new Book("Title2"));
        long count = bookRepository.count();
        assertEquals(3L, count);
    }

    @Test
    @DisplayName("Save")
    void save() {
        Book bookCreated = dummyBook3Genre1AuthorName3(false);
        List<Book> booksFromDb = bookRepository.findByTitle(bookCreated.getTitle());
        Book fromDb = booksFromDb.get(0);
        assertEquals(bookCreated.getAuthors().get(0).getName(),
                fromDb.getAuthors().get(0).getName(), "Authors doesn't match");
        assertEquals(bookCreated.getGenres().get(0).getName(),
                fromDb.getGenres().get(0).getName(), "Genre doesn't match");
    }

    @Test
    @DisplayName("Get by id")
    void getById() {
        Book bookCreated = dummyBook3Genre1AuthorName3(false);
        List<Book> booksFromDbByTitle = bookRepository.findByTitle(bookCreated.getTitle());
        Book fromDbByTitle = booksFromDbByTitle.get(0);
        Optional<Book> optionalBookFromDb = bookRepository.findById(fromDbByTitle.getId());
        Book fromDb = optionalBookFromDb.get();
        assertEquals(bookCreated.getAuthors().get(0).getName(),
                fromDb.getAuthors().get(0).getName(), "Authors doesn't match");
        assertEquals(bookCreated.getGenres().get(0).getName(),
                fromDb.getGenres().get(0).getName(), "Genre doesn't match");
    }

    @Test
    @DisplayName("Delete by id")
    void deleteById() {
        Book bookCreated = dummyBook3Genre1AuthorName3(false);
        List<Book> booksFromDbByTitle = bookRepository.findByTitle(bookCreated.getTitle());
        Book fromDbByTitle = booksFromDbByTitle.get(0);
        Optional<Book> optionalBookFromDb = bookRepository.findById(fromDbByTitle.getId());
        Book fromDb = optionalBookFromDb.get();
        bookRepository.deleteById(fromDb.getId());
        long count = bookRepository.count();
        assertEquals(0L, count);
    }

    @Test
    @DisplayName("Find by author id")
    void findByAuthor() {
        List<Author> authors = authorRepository.findByName("Author3");
        Author author = authors.get(0);
        List<Book> books = bookRepository.findByAuthorId(author.getId());
        assertEquals(1L, books.size());
    }

    @Test
    @DisplayName("Find by author id, if them 2 in book")
    void findByAuthorIf2InBook() {
        List<Author> authors = authorRepository.findByName("Author3");
        Author author = authors.get(0);
        List<Book> books = bookRepository.findByAuthorId(author.getId());
        assertEquals(1L, books.size());
        Book book = books.get(0);
        Author secondAuthor = new Author("AuthorSecond");
        mongoOperations.save(secondAuthor);
        Author authorFromDb = authorRepository.findByName(secondAuthor.getName()).get(0);
        book.getAuthors().add(authorFromDb);
        mongoOperations.save(book);
        books = bookRepository.findByAuthorId(author.getId());
        assertEquals(1L, books.size());
    }

}