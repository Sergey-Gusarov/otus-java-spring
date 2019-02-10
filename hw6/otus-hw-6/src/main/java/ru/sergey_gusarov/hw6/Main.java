package ru.sergey_gusarov.hw6;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.sergey_gusarov.hw6.dao.books.BookDao;
import ru.sergey_gusarov.hw6.domain.books.Book;

import java.util.List;

@SpringBootApplication
public class Main {

    public static void main(String[] args) throws Exception {

        ApplicationContext context = SpringApplication.run(Main.class);

        BookDao bookDao = context.getBean(BookDao.class);

        List<Book> books = null;
        books = bookDao.findAll();
        Book testBook1 = bookDao.getById(1);
        Book testBook2 = bookDao.getById(2);
        Book testBook3 = bookDao.getById(3);
        System.out.println("Book count " + bookDao.count());
        Console.main(args);
    }
}
