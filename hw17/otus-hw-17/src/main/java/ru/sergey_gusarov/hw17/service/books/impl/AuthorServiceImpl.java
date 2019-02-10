package ru.sergey_gusarov.hw17.service.books.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.sergey_gusarov.hw17.domain.books.Author;
import ru.sergey_gusarov.hw17.repository.author.AuthorRepository;
import ru.sergey_gusarov.hw17.service.books.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Mono<Author> getById(String id) {
        return authorRepository.findById(id);
    }

    @Override
    public Mono<Author> findById(String id) {
        return authorRepository.findById(id);
    }

    @Override
    public Mono<Author> findByName(String name) {
        Flux<Author> authors = authorRepository.findByName(name);
        if (authors.count().block() > 0)
            return authors.next();
        else
            return Mono.empty();
    }

    @Override
    public Mono<Void> deleteById(String id) {
        authorRepository.deleteById(id);
        return Mono.empty();
    }

    @Override
    public Flux<Author> deleteByIdAndRetList(String id) {
        authorRepository.deleteById(id);
        return authorRepository.findAll();
    }

    @Override
    public Mono<Author> save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Flux<Author> findAll() {
        return authorRepository.findAll();
    }
}
