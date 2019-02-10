package ru.sergey_gusarov.hw7_8.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.sergey_gusarov.hw7_8.dao.books.dict.DictAuthorRepository;
import ru.sergey_gusarov.hw7_8.domain.books.Author;

@ShellComponent
public class AuthorShell {
    private final DictAuthorRepository dictAuthorRepository;

    public AuthorShell(DictAuthorRepository dictAuthorRepository) {
        this.dictAuthorRepository = dictAuthorRepository;
    }

    @ShellMethod("Author count")
    public long authorCount() {
        return dictAuthorRepository.count();
    }

    @ShellMethod("Author get by id")
    public String authorGetById(@ShellOption long id) {
        return dictAuthorRepository.getById(id).toString();
    }

    @ShellMethod("Author delete by id")
    public void authorDeleteById(@ShellOption long id) {
        dictAuthorRepository.deleteById(id);
    }

    @ShellMethod("Author insert")
    public void authorSave(@ShellOption String name) {
        dictAuthorRepository.save(new Author(name));
    }

    @ShellMethod("Author list")
    public String authorList() {
        return dictAuthorRepository.findAll().toString();
    }

}