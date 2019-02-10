package ru.sergey_gusarov.hw15.repository.book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.sergey_gusarov.hw15.domain.books.Author;
import ru.sergey_gusarov.hw15.repository.author.AuthorRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataMongoTest
@SpringBootTest
class AuthorRepositoryTest {

    private final static String AUTHOR_NAME = "AuthorName1";
    @Autowired
    AuthorRepository authorRepository;

    @BeforeEach
    private void reSetup() {
        authorRepository.deleteAll();
        Author author = new Author(AUTHOR_NAME);
        authorRepository.save(author);
    }

    @Test
    @DisplayName("Save")
    void save() {
        List<Author> authorsFromDb = authorRepository.findByName(AUTHOR_NAME);
        assertEquals(1L, authorsFromDb.size());
        assertEquals(AUTHOR_NAME, authorsFromDb.get(0).getName(), "Name not eq");
    }

    @Test
    @DisplayName("Find by id")
    void findById() {
        List<Author> authorsFromDb = authorRepository.findByName(AUTHOR_NAME);
        assertEquals(1L, authorsFromDb.size(), "Size not eq 1");
        Optional<Author> optionalAuthorFromDbById = authorRepository.findById(authorsFromDb.get(0).getId());
        assertEquals(AUTHOR_NAME, optionalAuthorFromDbById.get().getName(), "Author.Name is not stored");
    }

    @Test
    @DisplayName("Find by name")
    void findByName() {
        List<Author> authorsFromDb = authorRepository.findByName(AUTHOR_NAME);
        assertEquals(1L, authorsFromDb.size());
        assertEquals(AUTHOR_NAME, authorsFromDb.get(0).getName(), "Name not eq");
    }

    // Тесты для методов, которые для тренировки
    @Test
    @DisplayName("Find by ?num?")
    void getAuthorByNumMethod() {
        List<Author> authorsFromDb = authorRepository.getAuthorByNumMethod(AUTHOR_NAME);
        assertEquals(1L, authorsFromDb.size());
        assertEquals(AUTHOR_NAME, authorsFromDb.get(0).getName(), "Name not eq");
    }

    @Test
    @DisplayName("Find by ?num1?")
    void getAuthorByNum1Method() {
        Author authorFromDb = authorRepository.getAuthorByNum1Method(AUTHOR_NAME);
        assertNotNull(authorFromDb, "Doesn't find");
        assertEquals(AUTHOR_NAME, authorFromDb.getName(), "Name not eq");
    }
}