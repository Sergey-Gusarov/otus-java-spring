package ru.sergey_gusarov.hw14.domain.books;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "book")
public class Book {
    @Id
    @Indexed
    private String id;
    @Indexed
    private String title;
    private List<Genre> genres = new ArrayList<>();
    // А как вы предпочитаете? set или list - по логике должен быть set, но удобней работать с list.
    @DBRef
    private List<Author> authors = new ArrayList<>();
    private List<BookComment> bookComments = new ArrayList<>();

    public Book() {
    }

    public Book(String title) {
        this.title = title;
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

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<BookComment> getBookComments() {
        return bookComments;
    }

    public void setBookComments(List<BookComment> bookComments) {
        this.bookComments = bookComments;
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
