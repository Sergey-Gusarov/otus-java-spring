package ru.sergey_gusarov.hw7_8.domain.books;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Book {
    @Id
    @SequenceGenerator(name = "BookSeqGen", sequenceName = "BookSeq", initialValue = 1, allocationSize = 0)
    @GeneratedValue(generator = "BookSeqGen")
    private long id;
    private String title;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL})
    @JoinTable(name = "BOOK_GENRE_REL",
            joinColumns = @JoinColumn(name = "BOOK_ID"),
            inverseJoinColumns = @JoinColumn(name = "GENRE_ID"))
    private Set<Genre> genres = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL})
    @JoinTable(name = "BOOK_AUTHOR_REL",
            joinColumns = @JoinColumn(name = "BOOK_ID"),
            inverseJoinColumns = @JoinColumn(name = "AUTHOR_ID"))
    private Set<Author> authors = new HashSet<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<BookComment> bookComments = new HashSet<>();

    public Book() {
    }

    public Book(String title) {
        this.title = title;
    }

    public Book(long id, String title, Set<Genre> genres, Set<Author> authors) {
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
