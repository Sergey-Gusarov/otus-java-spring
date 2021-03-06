package ru.sergey_gusarov.hw23.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import ru.sergey_gusarov.hw23.dao.books.dict.DictAuthorRepository;
import ru.sergey_gusarov.hw23.domain.books.Author;

@Transactional
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

    @ShellMethod("Author save")
    public void authorSave(@ShellOption String name) {
        dictAuthorRepository.save(new Author(name));
    }

    @ShellMethod("Author list")
    public String authorList() {
        return dictAuthorRepository.findAll().toString();
    }

}