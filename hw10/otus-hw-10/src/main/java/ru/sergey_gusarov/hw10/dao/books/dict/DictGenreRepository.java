package ru.sergey_gusarov.hw10.dao.books.dict;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.sergey_gusarov.hw10.domain.books.Genre;

import java.util.List;

@Transactional
public interface DictGenreRepository extends PagingAndSortingRepository<Genre, Long> {
    long count();

    Genre getById(long id);

    Genre getByName(String name);

    List<Genre> findAll();

    void deleteById(long id);

    Genre save(Genre genre);
}
