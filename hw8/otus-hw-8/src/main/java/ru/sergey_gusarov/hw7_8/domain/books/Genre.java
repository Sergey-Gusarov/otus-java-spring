package ru.sergey_gusarov.hw7_8.domain.books;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Genre {
    @Id
    @SequenceGenerator(name = "GenreSeqGen", sequenceName = "GenreSeq", initialValue = 1, allocationSize = 0)
    @GeneratedValue(generator = "GenreSeqGen")
    private long id;
    private String name;

    public Genre() {
    }

    public Genre(long id, String name) {

        this.id = id;
        this.name = name;
    }
    public Genre(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
