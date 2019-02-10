package ru.sergey_gusarov.hw7_8.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.sergey_gusarov.hw7_8.dao.books.dict.DictGenreRepository;
import ru.sergey_gusarov.hw7_8.domain.books.Genre;

@ShellComponent
public class GenreShell {
    private final DictGenreRepository dictGenreRepository;

    public GenreShell(DictGenreRepository dictGenreRepository) {
        this.dictGenreRepository = dictGenreRepository;
    }

    @ShellMethod("Genre count")
    public long genreCount() {
        return dictGenreRepository.count();
    }

    @ShellMethod("Genre get by id")
    public String genreGetById(@ShellOption long id) {
        return dictGenreRepository.getById(id).toString();
    }

    @ShellMethod("Genre delete by id")
    public void genreDeleteById(@ShellOption long id) {
        dictGenreRepository.deleteById(id);
    }

    @ShellMethod("Genre insert")
    public void genreSave(@ShellOption String genre) {
        dictGenreRepository.save(new Genre(genre));
    }

    @ShellMethod("Genre list")
    public String genreList() {
        return dictGenreRepository.findAll().toString();
    }
}
