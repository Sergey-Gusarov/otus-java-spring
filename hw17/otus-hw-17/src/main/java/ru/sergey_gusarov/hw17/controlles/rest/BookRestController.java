package ru.sergey_gusarov.hw17.controlles.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.sergey_gusarov.hw17.domain.books.Author;
import ru.sergey_gusarov.hw17.domain.books.Book;
import ru.sergey_gusarov.hw17.domain.books.BookComment;
import ru.sergey_gusarov.hw17.domain.books.Genre;
import ru.sergey_gusarov.hw17.exception.NotFoundException;
import ru.sergey_gusarov.hw17.service.books.AuthorService;
import ru.sergey_gusarov.hw17.service.books.BookService;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookRestController {
    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    public BookRestController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping
    public Flux<Book> listBookPage() {
        return bookService.findAll();
    }

    @PostMapping
    public Flux<Book> addBook(@RequestBody Book book) {
        bookService.save(book);
        return bookService.findAll();
    }

    @DeleteMapping("/{id}")
    public Flux<Book> deleteBook(@PathVariable String id) {
        bookService.deleteById(id);
        return bookService.findAll();
    }

    @PutMapping
    public Mono<Book> editBook(@RequestBody Book book) {
        Book bookFromDb = bookService.getById(book.getId()).block();
        bookFromDb.setTitle(book.getTitle());
        bookService.save(bookFromDb);
        return bookService.getById(bookFromDb.getId());
    }

    @DeleteMapping("/deleteGenreFromBook/{bookId}")
    public Flux<Genre> deleteGenre(@PathVariable String bookId, @RequestBody HashMap requestBody) {
        Book bookFromDb = bookService.findById(bookId).block();
        Genre genreForDel = bookFromDb.getGenres().stream()
                .filter(p -> p.getName().equals(requestBody.get("genreName")))
                .findFirst().get();
        bookFromDb.getGenres().remove(genreForDel);
        bookService.save(bookFromDb);
        Book bookFromUpdateDb = bookService.findById(bookId).block();
        return Flux.fromStream(bookFromUpdateDb.getGenres().stream());
    }

    @DeleteMapping("/deleteCommentFromBook/{bookId}")
    public Flux<BookComment> deleteComment(@PathVariable String bookId, @RequestBody HashMap requestBody) {
        Book bookFromDb = bookService.findById(bookId).block();
        BookComment commentForDel = bookFromDb.getBookComments().stream()
                .filter(p -> p.getText().equals(requestBody.get("commentText")))
                .findFirst().get();
        bookFromDb.getBookComments().remove(commentForDel);
        bookService.save(bookFromDb);
        Book bookFromUpdateDb = bookService.findById(bookId).block();
        return Flux.fromStream(bookFromUpdateDb.getBookComments().stream());
    }

    @DeleteMapping("/deleteAuthorFromBook/{bookId}")
    public Flux<Author> deleteAuthor(@PathVariable String bookId, @RequestBody HashMap requestBody) {
        Book bookFromDb = bookService.findById(bookId).block();
        Author authorForDel = bookFromDb.getAuthors().stream()
                .filter(p -> p.getId().equals(requestBody.get("authorId")))
                .findFirst().get();
        bookFromDb.getAuthors().remove(authorForDel);
        bookService.save(bookFromDb);
        Book bookFromUpdateDb = bookService.findById(bookId).block();
        return Flux.fromStream(bookFromUpdateDb.getAuthors().stream());
    }


    @PostMapping("/addGenreToBook/{bookId}")
    public Flux<Genre> addGenreToBook(@PathVariable String bookId, @RequestBody HashMap requestBody) {
        Book bookFromDb = bookService.findById(bookId).block();
        Genre genre = new Genre();
        genre.setName(requestBody.get("name").toString());
        bookFromDb.getGenres().add(genre);
        bookService.save(bookFromDb);
        Book bookFromUpdateDb = bookService.findById(bookId).block();
        return Flux.fromStream(bookFromUpdateDb.getGenres().stream());
    }

    @PostMapping("/addCommentToBook/{bookId}")
    public Flux<BookComment> addCommentToBook(@PathVariable String bookId, @RequestBody HashMap requestBody) {
        Book bookFromDb = bookService.findById(bookId).block();
        BookComment bookComment = new BookComment();
        bookComment.setText(requestBody.get("text").toString());
        bookFromDb.getBookComments().add(bookComment);
        bookService.save(bookFromDb);
        Book bookFromUpdateDb = bookService.findById(bookId).block();
        return Flux.fromStream(bookFromUpdateDb.getBookComments().stream());
    }

    @PostMapping("/addAuthorToBook/{bookId}")
    public Flux<Author> addAuthorToBook(@PathVariable String bookId, @RequestBody HashMap requestBody) {
        Book bookFromDb = bookService.findById(bookId).block();
        Author author = authorService.getById(requestBody.get("id").toString()).block();
        bookFromDb.getAuthors().add(author);
        bookService.save(bookFromDb);
        Book bookFromUpdateDb = bookService.findById(bookId).block();
        return Flux.fromStream(bookFromUpdateDb.getAuthors().stream());
    }

    @GetMapping("/getAuthorsFromBook/{bookId}")
    public Flux<Author> listAuthorBook(@PathVariable String bookId) {
        List<Author> authors = bookService.findById(bookId).block().getAuthors();
        return Flux.fromStream(authors.stream());
    }

}
