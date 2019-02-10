package ru.sergey_gusarov.hw19.service.books.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sergey_gusarov.hw19.domain.books.Author;
import ru.sergey_gusarov.hw19.repository.author.AuthorRepository;
import ru.sergey_gusarov.hw19.service.books.AuthorService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    @HystrixCommand(groupKey = "AuthorServiceImpl", commandKey = "getById")
    public Optional<Author> getById(String id) {
        return authorRepository.findById(id);
    }

    @Override
    @HystrixCommand(groupKey = "AuthorServiceImpl", commandKey = "deleteById")
    public void deleteById(String id) {
        authorRepository.deleteById(id);
    }

    @Override
    @HystrixCommand(groupKey = "AuthorServiceImpl", commandKey = "save")
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    @HystrixCommand(groupKey = "AuthorServiceImpl", commandKey = "findAll")
    public List<Author> findAll() {
        return authorRepository.findAll(new Sort(Sort.Direction.ASC, "name"));
    }
}
