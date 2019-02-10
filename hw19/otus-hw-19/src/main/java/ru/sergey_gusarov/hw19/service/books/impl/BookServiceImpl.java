package ru.sergey_gusarov.hw19.service.books.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sergey_gusarov.hw19.domain.books.Author;
import ru.sergey_gusarov.hw19.domain.books.Book;
import ru.sergey_gusarov.hw19.domain.books.BookComment;
import ru.sergey_gusarov.hw19.domain.books.Genre;
import ru.sergey_gusarov.hw19.exception.NotFoundException;
import ru.sergey_gusarov.hw19.repository.author.AuthorRepository;
import ru.sergey_gusarov.hw19.repository.book.BookRepository;
import ru.sergey_gusarov.hw19.service.books.BookService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    @HystrixCommand(groupKey = "BookServiceImpl", commandKey = "count")
    public long count() {
        return bookRepository.count();
    }

    @Override
    @HystrixCommand(groupKey = "BookServiceImpl", commandKey = "findById")
    public Optional<Book> findById(String id) {
        return bookRepository.findById(id);
    }

    @Override
    @HystrixCommand(groupKey = "BookServiceImpl", commandKey = "findByTitle")
    public List<Book> findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    @HystrixCommand(groupKey = "BookServiceImpl", commandKey = "deleteById")
    public void deleteById(String id) {
        bookRepository.deleteById(id);
    }

    @Override
    @HystrixCommand(groupKey = "BookServiceImpl", commandKey = "save")
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    @HystrixCommand(groupKey = "BookServiceImpl", commandKey = "add")
    public Book add(String title, List<Author> authors, List<Genre> genres) {
        Book book = new Book();
        book.setTitle(title);
        authors.forEach(author -> {
            List<Author> authorList = authorRepository.findByName(author.getName());
            if (authorList.size() > 0)
                author.setId(authorList.get(0).getId());
            else
                authorRepository.save(author);
        });
        book.setGenres(genres);
        book.setAuthors(authors);
        return bookRepository.save(book);
    }

    @Override
    @HystrixCommand(groupKey = "BookServiceImpl", commandKey = "findAll")
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    @HystrixCommand(groupKey = "BookServiceImpl", commandKey = "deleteAll")
    public void deleteAll() {
        bookRepository.deleteAll();
    }

    @Override
    @HystrixCommand(groupKey = "BookServiceImpl", commandKey = "addComment")
    public void addComment(String id, String comment) {
        Book book = bookRepository.findById(id).get();
        book.getBookComments().add(new BookComment(comment));
        bookRepository.save(book);
    }

    @Override
    @HystrixCommand(groupKey = "BookServiceImpl", commandKey = "findByAuthorName")
    public List<Book> findByAuthorName(String authorName) {
        List<Author> authors = authorRepository.findByName(authorName);
        if(authors.isEmpty())
            throw new NotFoundException();
        Author author = authors.get(0);
        return bookRepository.findByAuthorId(author.getId());
    }

}
