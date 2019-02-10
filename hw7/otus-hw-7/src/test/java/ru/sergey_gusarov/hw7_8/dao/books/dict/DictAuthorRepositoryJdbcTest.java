package ru.sergey_gusarov.hw7_8.dao.books.dict;

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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ComponentScan("ru.sergey_gusarov.hw7_8")
class DictAuthorRepositoryJdbcTest {

    @Autowired
    DictAuthorRepository dictAuthorRepository;

    private Author dummyAuthor1(){
        return new Author("Author1");
    };

    @BeforeEach
    @Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    private void reSetupSchema(){

    };

    @Test
    @DisplayName("Count")
    @Sql(scripts = "classpath:add_author.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void count() {
        long count = dictAuthorRepository.count();
        assertEquals(3L, count);
    }

    @Test
    @DisplayName("Insert")
    void insert() {
        Author author = new Author("Author1");
        dictAuthorRepository.insert(author);
        Author authorFromDb = dictAuthorRepository.getByName("Author1");
        assertEquals(authorFromDb.getName(), author.getName());
    }

    @Test
    @DisplayName("Get by id")
    void getById() {
    }

    @Test
    @DisplayName("Gent by name")
    void getByName() {
        Author author = new Author("Author1");
        dictAuthorRepository.insert(author);
        Author authorFromDb = dictAuthorRepository.getByName("Author1");
        assertEquals(authorFromDb.getName(), author.getName());
    }

    @Test
    @DisplayName("Find all")
    void findAll() {
        final int COUNT_ITERATION = 3;
        List<Author> authors = new ArrayList<>();

        for (Integer i = 0; i < COUNT_ITERATION; i++) {
            authors.add(new Author("Author" + i.toString()));
            dictAuthorRepository.insert(authors.get(i));
        }
        List<Author> authorsFromDb = dictAuthorRepository.findAll();
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
    @Sql(scripts = "classpath:add_author.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void update() {
        List<Author> authors = dictAuthorRepository.findAll();
        final String updateNameStr = "Update author";
        Author author = dictAuthorRepository.getById(1);
        author.setName(updateNameStr);
        dictAuthorRepository.update(author);
        Author authorFromDb = dictAuthorRepository.getById(1);
        assertEquals(updateNameStr, authorFromDb.getName());
    }

    @Test
    @DisplayName("delete")
    @Sql(scripts = "classpath:add_author.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void delete() {
        List<Author> authors = dictAuthorRepository.findAll();
        Author author = dictAuthorRepository.getById(1);
        dictAuthorRepository.delete(author);
        Author authorFromDb = dictAuthorRepository.getById(1);
        authors = dictAuthorRepository.findAll();
        assertEquals(2L, authors.size());
        // Попытка удалить несуществующую запись
        Throwable nullResult = assertThrows(IllegalArgumentException.class, () -> {
                    Author authorCantDelete = dictAuthorRepository.getById(1);
                    if (authorCantDelete == null)
                        throw new IllegalArgumentException("attempt to create delete event with null entity");
                    dictAuthorRepository.deleteById(authorCantDelete.getId());
                }
        );
        assertEquals("attempt to create delete event with null entity",
                nullResult.getMessage(), "Doesn't throw exception");
    }

    @Test
    @DisplayName("Delete by id")
    @Sql(scripts = "classpath:add_author.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deleteById() {
        Author author = dictAuthorRepository.getById(1);
        dictAuthorRepository.deleteById(author.getId());
        Author authorFromDb = dictAuthorRepository.getById(1);
        List<Author> authors = dictAuthorRepository.findAll();
        assertEquals(2L, authors.size());
        // Попытка удалить несуществующую запись
        Throwable nullResult = assertThrows(IllegalArgumentException.class, () -> {
                    Author authorCantDelete = dictAuthorRepository.getById(1);
                    if (authorCantDelete == null)
                        throw new IllegalArgumentException("attempt to create delete event with null entity");
                    dictAuthorRepository.deleteById(authorCantDelete.getId());
                }
        );
        assertEquals("attempt to create delete event with null entity",
                nullResult.getMessage(), "Doesn't throw exception");
    }

    @Test
    @DisplayName("Save")
    @Sql(scripts = "classpath:add_author.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void save() {
        Author author = dictAuthorRepository.getById(1);
        String nameAuthor = author.getName();
        Author authorFromDb = dictAuthorRepository.save(new Author(nameAuthor));
        assertEquals(1, authorFromDb.getId());
    }
}