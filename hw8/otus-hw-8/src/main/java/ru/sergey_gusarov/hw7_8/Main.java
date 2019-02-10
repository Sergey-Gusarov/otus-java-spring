package ru.sergey_gusarov.hw7_8;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.sergey_gusarov.hw7_8.dao.books.BookCommentRepository;
import ru.sergey_gusarov.hw7_8.dao.books.BookRepository;
import ru.sergey_gusarov.hw7_8.dao.books.dict.DictGenreRepository;
import ru.sergey_gusarov.hw7_8.domain.books.Author;
import ru.sergey_gusarov.hw7_8.domain.books.Book;
import ru.sergey_gusarov.hw7_8.domain.books.BookComment;
import ru.sergey_gusarov.hw7_8.domain.books.Genre;
import ru.sergey_gusarov.hw7_8.service.CommentsService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class);
    }
}
