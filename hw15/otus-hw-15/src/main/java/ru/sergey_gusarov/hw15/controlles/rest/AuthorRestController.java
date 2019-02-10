package ru.sergey_gusarov.hw15.controlles.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sergey_gusarov.hw15.domain.books.Author;
import ru.sergey_gusarov.hw15.exception.NotFoundException;
import ru.sergey_gusarov.hw15.service.books.AuthorService;
import ru.sergey_gusarov.hw15.service.books.BookService;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorRestController {
    private final AuthorService authorService;

    @Autowired
    public AuthorRestController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<Author> listAuthorPage() {
        List<Author> authors = authorService.findAll();
        return authors;
    }

    @GetMapping("/{id}")
    public Author getAuthor(@PathVariable String id) {
        Author author = authorService.getById(id).orElseThrow(NotFoundException::new);
        return author;
    }

    @PostMapping
    public List<Author> addAuthor(@RequestBody Author author) {
        authorService.save(author);
        return authorService.findAll();
    }

    @DeleteMapping("/{id}")
    public List<Author> deleteAuthor(@PathVariable String id) {
        authorService.deleteById(id);
        return authorService.findAll();
    }

    @PutMapping
    public Author editAuthor(@RequestBody Author author) {
        Author authorFromDb = authorService.getById(author.getId()).orElseThrow(NotFoundException::new);
        authorFromDb.setName(author.getName());
        authorService.save(authorFromDb);
        return authorService.getById(authorFromDb.getId()).orElseThrow(NotFoundException::new);
    }
}
