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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.sergey_gusarov.hw12.domain.books.Author;
import ru.sergey_gusarov.hw12.repository.books.AuthorRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class AuthorRepositoryTest {
    @Autowired
    AuthorRepository authorRepository;

    private Author dummyAuthor1(){
        return new Author("Author1");
    };

    @BeforeEach
    private void reSetupSchema(){

    };

    @Test
    @DisplayName("Count")
    void count() {
        long count = authorRepository.count();
        assertEquals(3L, count);
    }

    @Test
    @DisplayName("Get by id")
    void getById() {
    }

    @Test
    @DisplayName("Gent by name")
    void getByName() {
        Author author = new Author("Author1");
        authorRepository.save(author);
        Author authorFromDb = authorRepository.findByName("Author1");
        assertEquals(authorFromDb.getName(), author.getName());
    }

    @Test
    @DisplayName("Find all")
    void findAll() {
        final int COUNT_ITERATION = 3;
        List<Author> authors = new ArrayList<>();

        for (Integer i = 0; i < COUNT_ITERATION; i++) {
            authors.add(new Author("Author" + i.toString()));
            authorRepository.save(authors.get(i));
        }
        List<Author> authorsFromDb = authorRepository.findAll();
        assertEquals(COUNT_ITERATION, authorsFromDb.size());
        for (Integer i = 0; i < COUNT_ITERATION; i++) {
            Integer finalI = i;
            Author authorFromDb = authorsFromDb.stream()
                    .filter(s -> s.getName().equals(authors.get(finalI).getName()))
                    .findFirst().get();
            assertEquals(authors.get(finalI).getName(), authorFromDb.getName());
        }
    }

    @Test
    @DisplayName("update")
    void update() {
        List<Author> authors = authorRepository.findAll();
        final String updateNameStr = "Update author";
        Author author = authorRepository.findById("1").get();
        author.setName(updateNameStr);
        authorRepository.save(author);
        Author authorFromDb = authorRepository.findById("1").get();
        assertEquals(updateNameStr, authorFromDb.getName());
    }

    @Test
    @DisplayName("delete")
    void delete() {
        List<Author> authors = authorRepository.findAll();
        Author author = authorRepository.findById("1").get();
        authorRepository.delete(author);
        Author authorFromDb = authorRepository.findById("1").get();
        authors = authorRepository.findAll();
        assertEquals(2L, authors.size());
        // Попытка удалить несуществующую запись
        Throwable nullResult = assertThrows(IllegalArgumentException.class, () -> {
                    Author authorCantDelete = authorRepository.findById("1").get();
                    if (authorCantDelete == null)
                        throw new IllegalArgumentException("attempt to create delete event with null entity");
                    authorRepository.deleteById(authorCantDelete.getId());
                }
        );
        assertEquals("attempt to create delete event with null entity",
                nullResult.getMessage(), "Doesn't throw exception");
    }

    @Test
    @DisplayName("Delete by id")
    void deleteById() {
        Author author = authorRepository.findById("1").get();
        authorRepository.deleteById(author.getId());
        Author authorFromDb = authorRepository.findById("1").get();
        List<Author> authors = authorRepository.findAll();
        assertEquals(2L, authors.size());
        // Попытка удалить несуществующую запись
        Throwable nullResult = assertThrows(IllegalArgumentException.class, () -> {
                    Author authorCantDelete = authorRepository.findById("1").get();
                    if (authorCantDelete == null)
                        throw new IllegalArgumentException("attempt to create delete event with null entity");
                    authorRepository.deleteById(authorCantDelete.getId());
                }
        );
        assertEquals("attempt to create delete event with null entity",
                nullResult.getMessage(), "Doesn't throw exception");
    }

    @Test
    @DisplayName("Save")
    void save() {
        Author author = authorRepository.findById("1").get();
        String nameAuthor = author.getName();
        Author authorFromDb = authorRepository.save(new Author(nameAuthor));
        assertNotEquals(1, authorFromDb.getId());
    }
}