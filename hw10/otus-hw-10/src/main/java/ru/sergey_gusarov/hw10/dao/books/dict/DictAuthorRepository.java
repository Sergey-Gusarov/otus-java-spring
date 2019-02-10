package ru.sergey_gusarov.hw10.dao.books.dict;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.sergey_gusarov.hw10.domain.books.Author;

import java.util.List;

@Transactional
public interface DictAuthorRepository extends CrudRepository<Author, Long> {
    long count();

    Author getById(long id);

    Author getByName(String name);

    List<Author> findAll();

    void delete(Author author);

    void deleteById(long id);

    Author save(Author author);
}
