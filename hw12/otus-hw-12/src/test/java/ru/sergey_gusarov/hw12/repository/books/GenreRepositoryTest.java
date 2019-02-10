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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.sergey_gusarov.hw12.domain.books.Genre;
import ru.sergey_gusarov.hw12.repository.books.GenreRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class GenreRepositoryTest {
    @Autowired
    GenreRepository genreRepository;

    private Genre dummyGenre1(){
        return new Genre("Genre1");
    };

    @BeforeEach
    private void reSetupSchema(){

    };

    @Test
    @DisplayName("Count")
    void count() {
        long count = genreRepository.count();
        assertEquals(3L, count);
    }

    @Test
    @DisplayName("Get by id")
    void getById() {
    }

    @Test
    @DisplayName("Gent by name")
    void getByName() {
        Genre genre = new Genre("Genre1");
        genreRepository.save(genre);
        Genre genreFromDb = genreRepository.findByName("Genre1");
        assertEquals(genreFromDb.getName(), genre.getName());
    }

    @Test
    @DisplayName("Find all")
    void findAll() {
        final int COUNT_ITERATION = 3;
        List<Genre> genres = new ArrayList<>();

        for (Integer i = 0; i < COUNT_ITERATION; i++) {
            genres.add(new Genre("Genre" + i.toString()));
            genreRepository.save(genres.get(i));
        }
        List<Genre> genresFromDb = genreRepository.findAll();
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
    void update() {
        List<Genre> genres = genreRepository.findAll();
        final String updateNameStr = "Update genre";
        Genre genre = genreRepository.findById("1").get();
        genre.setName(updateNameStr);
        genreRepository.save(genre);
        Genre genreFromDb = genreRepository.findById("1").get();
        assertEquals(updateNameStr, genreFromDb.getName());
    }

    @Test
    @DisplayName("delete")
    void delete() {
        List<Genre> genres = genreRepository.findAll();
        Genre genre = genreRepository.findById("1").get();
        genreRepository.delete(genre);
        Genre genreFromDb = genreRepository.findById("1").get();
        genres = genreRepository.findAll();
        assertEquals(2L, genres.size());
        // Попытка удалить несуществующую запись
        Throwable nullResult = assertThrows(IllegalArgumentException.class, () -> {
                    Genre genreCantDelete = genreRepository.findById("1").get();
                    if (genreCantDelete == null)
                        throw new IllegalArgumentException("attempt to create delete event with null entity");
                    genreRepository.deleteById(genreCantDelete.getId());
                }
        );
        assertEquals("attempt to create delete event with null entity",
                nullResult.getMessage(), "Doesn't throw exception");
    }

    @Test
    @DisplayName("Delete by id")
    void deleteById() {
        Genre genre = genreRepository.findById("1").get();
        genreRepository.deleteById(genre.getId());
        Genre genreFromDb = genreRepository.findById("1").get();
        List<Genre> genres = genreRepository.findAll();
        assertEquals(2L, genres.size());
        // Попытка удалить несуществующую запись
        Throwable nullResult = assertThrows(IllegalArgumentException.class, () -> {
                    Genre genreCantDelete = genreRepository.findById("1").get();
                    if (genreCantDelete == null)
                        throw new IllegalArgumentException("attempt to create delete event with null entity");
                    genreRepository.deleteById(genreCantDelete.getId());
                }
        );
        assertEquals("attempt to create delete event with null entity",
                nullResult.getMessage(), "Doesn't throw exception");
    }

    @Test
    @DisplayName("Save")
    void save() {
        Genre genre = genreRepository.findById("1").get();
        String nameGenre = genre.getName();
        Genre genreFromDb = genreRepository.save(new Genre(nameGenre));
        assertNotEquals(1, genreFromDb.getId());
    }
}