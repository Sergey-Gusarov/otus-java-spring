package ru.sergey_gusarov.hw6.domain.books;

import java.util.Objects;

public class Author {
    private final int id;
    private final String name;

    public Author(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {

        return id;
    }
}
