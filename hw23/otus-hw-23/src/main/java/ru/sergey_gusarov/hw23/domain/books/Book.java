package ru.sergey_gusarov.hw23.domain.books;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class Book {
    @Id
    @SequenceGenerator(name = "BookSeqGen", sequenceName = "BookSeq", initialValue = 1, allocationSize = 0)
    @GeneratedValue(generator = "BookSeqGen")
    private long id;
    private String title;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL})
    @JoinTable(name = "BOOK_GENRE_REL",
            joinColumns = @JoinColumn(name = "BOOK_ID"),
            inverseJoinColumns = @JoinColumn(name = "GENRE_ID", nullable = false, updatable = false))
    private List<Genre> genres = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL})
    @JoinTable(name = "BOOK_AUTHOR_REL",
            joinColumns = @JoinColumn(name = "BOOK_ID"),
            inverseJoinColumns = @JoinColumn(name = "AUTHOR_ID", nullable = false, updatable = false))
    private List<Author> authors = new ArrayList<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<BookComment> bookComments = new ArrayList<>();

    public Book() {
    }

    public Book(String title) {
        this.title = title;
    }

    public Book(long id, String title, List<Genre> genres, List<Author> authors) {
        this.id = id;
        this.title = title;
        this.genres = genres;
        this.authors = authors;
    }

    public Book(String title, List<Genre> genres, List<Author> authors) {
        this.title = title;
        this.genres = genres;
        this.authors = authors;
    }

}
