package ru.sergey_gusarov.hw7_8.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sergey_gusarov.hw7_8.dao.books.BookRepository;
import ru.sergey_gusarov.hw7_8.dao.books.dict.DictAuthorRepository;
import ru.sergey_gusarov.hw7_8.dao.books.dict.DictGenreRepository;
import ru.sergey_gusarov.hw7_8.domain.books.Author;
import ru.sergey_gusarov.hw7_8.domain.books.Book;
import ru.sergey_gusarov.hw7_8.domain.books.Genre;

import javax.persistence.NoResultException;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private DictAuthorRepository dictAuthorRepository;
    @Autowired
    private DictGenreRepository dictGenreRepository;

    @Override
    public long bookCount() {
        return bookRepository.count();
    }

    @Override
    public String bookGetById(long id) {
        return bookRepository.getById(id).toString();
    }

    @Override
    public String bookGetByTitle(String title) {
        return bookRepository.getByTitle(title).toString();
    }

    @Override
    public void bookDeleteById(long id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void bookInsertTitleAuthorGenre(String title, String authorName, String genreName) {
        Book book = new Book();
        book.setTitle(title);
        Author author;
        try {
            author = dictAuthorRepository.getByName(authorName);
        }catch (Exception ex ){
            if(ex.getCause() instanceof NoResultException)
                author = new Author(authorName);
            else
                throw ex;
        }
        book.getAuthors().add(author);

        Genre genre;
        try {
            genre = dictGenreRepository.getByName(genreName);
        }catch (Exception ex){
            if(ex.getCause() instanceof NoResultException)
                genre = new Genre(genreName);
            else
                throw ex;
        }
        book.getGenres().add(genre);
        bookRepository.insert(book);
    }

    @Override
    public String bookList() {
        return bookRepository.findAll().toString();
    }
}
