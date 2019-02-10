package ru.sergey_gusarov.hw19.repository.author;

import ru.sergey_gusarov.hw19.domain.books.Author;

import java.util.List;

public interface AuthorRepositoryCustom {
    List<Author> getAuthorByNumMethod(String authorNum);

    Author getAuthorByNum1Method(String authorNum);
}
