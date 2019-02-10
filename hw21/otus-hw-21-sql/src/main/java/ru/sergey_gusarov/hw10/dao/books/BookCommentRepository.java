package ru.sergey_gusarov.hw10.dao.books;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.sergey_gusarov.hw10.domain.books.BookComment;

import java.util.List;

@Transactional
public interface BookCommentRepository extends JpaRepository<BookComment, Long> {
    long count();

    BookComment getById(long id);

    void deleteById(long id);
}
