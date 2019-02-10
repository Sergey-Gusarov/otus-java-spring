package ru.sergey_gusarov.hw6.domain.books;

import java.util.Objects;

public class Genre {
    private final int id;
    private final String name;

    public Genre(int id, String name) {

        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
