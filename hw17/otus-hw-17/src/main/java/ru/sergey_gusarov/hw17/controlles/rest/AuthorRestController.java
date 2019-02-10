package ru.sergey_gusarov.hw17.controlles.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.sergey_gusarov.hw17.domain.books.Author;
import ru.sergey_gusarov.hw17.exception.NotFoundException;
import ru.sergey_gusarov.hw17.service.books.AuthorService;

import java.util.List;

@RestController
@RequestMapping("/author_old")
public class AuthorRestController {
    private final AuthorService authorService;

    @Autowired
    public AuthorRestController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public Flux<Author> listAuthorPage() {
        return authorService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Author> getAuthor(@PathVariable String id) {
        return authorService.getById(id);
    }

    @PostMapping
    public Flux<Author> addAuthor(@RequestBody Author author) {
        authorService.save(author);
        return authorService.findAll();
    }

    @DeleteMapping("/{id}")
    public Flux<Author> deleteAuthor(@PathVariable String id) {
        authorService.deleteById(id);
        return authorService.findAll();
    }

    @PutMapping
    public Mono<Author> editAuthor(@RequestBody Author author) {
        Author authorFromDb = authorService.getById(author.getId()).block();
        authorFromDb.setName(author.getName());
        authorService.save(authorFromDb);
        return authorService.getById(authorFromDb.getId());
    }
}
