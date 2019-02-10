package ru.sergey_gusarov.hw23.mongo.domain.books;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Genre {
    private String name;

    public Genre(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "name='" + name + "'" +
                '}';
    }
}
