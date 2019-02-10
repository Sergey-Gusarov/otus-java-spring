package ru.sergey_gusarov.hw12.repository.books;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import ru.sergey_gusarov.hw12.domain.books.BookComment;

import java.util.List;

public interface BookCommentRepository extends MongoRepository<BookComment, String> {

}
