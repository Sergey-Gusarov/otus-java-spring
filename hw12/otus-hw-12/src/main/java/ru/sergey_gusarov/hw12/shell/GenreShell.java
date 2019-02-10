package ru.sergey_gusarov.hw12.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.sergey_gusarov.hw12.repository.books.GenreRepository;
import ru.sergey_gusarov.hw12.domain.books.Genre;

@ShellComponent
public class GenreShell {
    private final GenreRepository genreRepository;

    public GenreShell(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @ShellMethod("Genre count")
    public long genreCount() {
        return genreRepository.count();
    }

    @ShellMethod("Genre get by id")
    public String genreGetById(@ShellOption String id) {
        return genreRepository.findById(id).toString();
    }

    @ShellMethod("Genre delete by id")
    public void genreDeleteById(@ShellOption String id) {
        genreRepository.deleteById(id);
    }

    @ShellMethod("Genre save")
    public void genreSave(@ShellOption String genre) {
        genreRepository.save(new Genre(genre));
    }

    @ShellMethod("Genre list")
    public String genreList() {
        return genreRepository.findAll().toString();
    }
}
