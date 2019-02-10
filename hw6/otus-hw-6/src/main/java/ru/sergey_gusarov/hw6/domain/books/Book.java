package ru.sergey_gusarov.hw6.domain.books;

import java.util.Objects;
import java.util.Set;

public class Book {
    private final int id;
    private final String title;
    private Set<Genre> genres;
    private Set<Author> authors;

    public Book(int id, String title, Set<Genre> genres, Set<Author> authors) {

        this.id = id;
        this.title = title;
        this.genres = genres;
        this.authors = authors;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public int getId() {

        return id;
    }

    public String getTitle() {
        return title;
    }
}
