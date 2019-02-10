package ru.sergey_gusarov.hw19.repository.book;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sergey_gusarov.hw19.domain.books.Book;

import java.util.List;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findByTitle(String title);

    @Query(value = "{ 'authors.id' : ?0 }")
    List<Book> findByAuthorId(String authorId);
}
