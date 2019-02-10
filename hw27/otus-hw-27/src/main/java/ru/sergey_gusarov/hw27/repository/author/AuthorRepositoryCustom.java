package ru.sergey_gusarov.hw27.repository.author;

import ru.sergey_gusarov.hw27.domain.books.Author;

import java.util.List;

public interface AuthorRepositoryCustom {
    List<Author> getAuthorByNumMethod(String authorNum);

    Author getAuthorByNum1Method(String authorNum);
}
