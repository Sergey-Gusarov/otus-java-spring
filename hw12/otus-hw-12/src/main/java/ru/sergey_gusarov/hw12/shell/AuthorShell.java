package ru.sergey_gusarov.hw12.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.sergey_gusarov.hw12.repository.books.AuthorRepository;
import ru.sergey_gusarov.hw12.domain.books.Author;

@ShellComponent
public class AuthorShell {
    private final AuthorRepository authorRepository;

    public AuthorShell(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @ShellMethod("Author count")
    public long authorCount() {
        return authorRepository.count();
    }

    @ShellMethod("Author get by id")
    public String authorGetById(@ShellOption String id) {
        return authorRepository.findById(id).toString();
    }

    @ShellMethod("Author delete by id")
    public void authorDeleteById(@ShellOption String id) {
        authorRepository.deleteById(id);
    }

    @ShellMethod("Author save")
    public void authorSave(@ShellOption String name) {
        authorRepository.save(new Author(name));
    }

    @ShellMethod("Author list")
    public String authorList() {
        return authorRepository.findAll().toString();
    }

}