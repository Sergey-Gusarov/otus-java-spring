package ru.sergey_gusarov.hw23.dao.books;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.sergey_gusarov.hw23.domain.books.Book;

@Transactional
public interface BookRepository extends JpaRepository<Book, Long> {
    long count();

    Book getById(long id);

    Book getByTitle(String title);

    void deleteById(long id);
}
