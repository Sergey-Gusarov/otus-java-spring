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
import ru.sergey_gusarov.hw7_8.domain.books.Genre;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ComponentScan("ru.sergey_gusarov.hw7_8")
class DictGenreRepositoryJdbcTest {

    @Autowired
    DictGenreRepository dictGenreRepository;

    private Genre dummyGenre1(){
        return new Genre("Genre1");
    };

    @BeforeEach
    @Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    private void reSetupSchema(){

    };

    @Test
    @DisplayName("Count")
    @Sql(scripts = "classpath:add_genre.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void count() {
        long count = dictGenreRepository.count();
        assertEquals(3L, count);
    }

    @Test
    @DisplayName("Insert")
    void insert() {
        Genre genre = new Genre("Genre1");
        dictGenreRepository.insert(genre);
        Genre genreFromDb = dictGenreRepository.getByName("Genre1");
        assertEquals(genreFromDb.getName(), genre.getName());
    }

    @Test
    @DisplayName("Get by id")
    void getById() {
    }

    @Test
    @DisplayName("Gent by name")
    void getByName() {
        Genre genre = new Genre("Genre1");
        dictGenreRepository.insert(genre);
        Genre genreFromDb = dictGenreRepository.getByName("Genre1");
        assertEquals(genreFromDb.getName(), genre.getName());
    }

    @Test
    @DisplayName("Find all")
    void findAll() {
        final int COUNT_ITERATION = 3;
        List<Genre> genres = new ArrayList<>();

        for (Integer i = 0; i < COUNT_ITERATION; i++) {
            genres.add(new Genre("Genre" + i.toString()));
            dictGenreRepository.insert(genres.get(i));
        }
        List<Genre> genresFromDb = dictGenreRepository.findAll();
        assertEquals(COUNT_ITERATION, genresFromDb.size());
        for (Integer i = 0; i < COUNT_ITERATION; i++) {
            Integer finalI = i;
            Genre genreFromDb = genresFromDb.stream()
                    .filter(s -> s.getName().equals(genres.get(finalI).getName()))
                    .findFirst().get();
            assertEquals(genres.get(finalI).getName(), genreFromDb.getName());
        }
    }

    @Test
    @DisplayName("update")
    @Sql(scripts = "classpath:add_genre.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void update() {
        List<Genre> genres = dictGenreRepository.findAll();
        final String updateNameStr = "Update genre";
        Genre genre = dictGenreRepository.getById(1);
        genre.setName(updateNameStr);
        dictGenreRepository.update(genre);
        Genre genreFromDb = dictGenreRepository.getById(1);
        assertEquals(updateNameStr, genreFromDb.getName());
    }

    @Test
    @DisplayName("delete")
    @Sql(scripts = "classpath:add_genre.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void delete() {
        List<Genre> genres = dictGenreRepository.findAll();
        Genre genre = dictGenreRepository.getById(1);
        dictGenreRepository.delete(genre);
        Genre genreFromDb = dictGenreRepository.getById(1);
        genres = dictGenreRepository.findAll();
        assertEquals(2L, genres.size());
        // Попытка удалить несуществующую запись
        Throwable nullResult = assertThrows(IllegalArgumentException.class, () -> {
                    Genre genreCantDelete = dictGenreRepository.getById(1);
                    if (genreCantDelete == null)
                        throw new IllegalArgumentException("attempt to create delete event with null entity");
            dictGenreRepository.deleteById(genreCantDelete.getId());
                }
        );
        assertEquals("attempt to create delete event with null entity",
                nullResult.getMessage(), "Doesn't throw exception");
    }

    @Test
    @DisplayName("Delete by id")
    @Sql(scripts = "classpath:add_genre.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deleteById() {
        Genre genre = dictGenreRepository.getById(1);
        dictGenreRepository.deleteById(genre.getId());
        Genre genreFromDb = dictGenreRepository.getById(1);
        List<Genre> genres = dictGenreRepository.findAll();
        assertEquals(2L, genres.size());
        // Попытка удалить несуществующую запись
        Throwable nullResult = assertThrows(IllegalArgumentException.class, () -> {
                    Genre genreCantDelete = dictGenreRepository.getById(1);
                    if (genreCantDelete == null)
                        throw new IllegalArgumentException("attempt to create delete event with null entity");
                    dictGenreRepository.deleteById(genreCantDelete.getId());
                }
        );
        assertEquals("attempt to create delete event with null entity",
                nullResult.getMessage(), "Doesn't throw exception");
    }

    @Test
    @DisplayName("Save")
    @Sql(scripts = "classpath:add_genre.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void save() {
        Genre genre = dictGenreRepository.getById(1);
        String nameGenre = genre.getName();
        Genre genreFromDb = dictGenreRepository.save(new Genre(nameGenre));
        assertEquals(1, genreFromDb.getId());
    }
}