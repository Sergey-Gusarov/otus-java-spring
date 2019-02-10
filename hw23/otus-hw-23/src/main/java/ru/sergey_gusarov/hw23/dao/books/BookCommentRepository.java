package ru.sergey_gusarov.hw23.dao.books;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.sergey_gusarov.hw23.domain.books.BookComment;

@Transactional
public interface BookCommentRepository extends JpaRepository<BookComment, Long> {
    long count();

    BookComment getById(long id);

    void deleteById(long id);
}
