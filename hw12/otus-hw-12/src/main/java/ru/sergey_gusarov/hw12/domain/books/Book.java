package ru.sergey_gusarov.hw12.domain.books;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import java.util.HashSet;
import java.util.Set;

@Document
public class Book {
    @Id
    private String id;
    private String title;
    private Set<Genre> genres = new HashSet<>();
    private Set<Author> authors = new HashSet<>();
    private Set<BookComment> bookComments = new HashSet<>();

    public Book() {
    }

    public Book(String title) {
        this.title = title;
    }

    public Book(String id, String title, Set<Genre> genres, Set<Author> authors) {
        this.id = id;
        this.title = title;
        this.genres = genres;
        this.authors = authors;
    }

    public Book(String title, Set<Genre> genres, Set<Author> authors) {
        this.title = title;
        this.genres = genres;
        this.authors = authors;
    }

    public Set<BookComment> getBookComments() {
        return bookComments;
    }

    public void setBookComments(Set<BookComment> bookComments) {
        this.bookComments = bookComments;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(60);
        sb.append("Book{ id=")
                .append(getId())
                .append(", title='")
                .append(getTitle())
                .append("'");
        sb.append(getAuthors());
        sb.append(getGenres());
        sb.append(getBookComments());
        return sb.toString();
    }
}
