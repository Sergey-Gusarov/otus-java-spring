package ru.sergey_gusarov.hw7_8.domain.books;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Author {
    @Id
    @SequenceGenerator(name = "AuthorSeqGen", sequenceName = "AuthorSeq", initialValue = 1, allocationSize = 0)
    @GeneratedValue(generator = "AuthorSeqGen")
    private long id;
    private String name;

    public Author() {
    }

    public Author(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Author(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
