package ru.sergey_gusarov.hw27.app.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import ru.sergey_gusarov.hw27.service.books.BookService;

import java.time.LocalDateTime;


@Component
public class HealthCheckBooksCountInLib implements HealthIndicator {
    private final BookService bookService;

    private static int NORMAL_LIB_BOOKS_COUNT = 50;
    private static long BOOKS_COUNT_CACHE = -1;
    private static LocalDateTime LAST_CHECK = LocalDateTime.now();

    public HealthCheckBooksCountInLib(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public Health health() {
        int errorCode = check();
        if (errorCode == 0) {
            return Health.down()
                    .withDetail("Less "+ NORMAL_LIB_BOOKS_COUNT +" book in library", errorCode).build();
        }
        return Health.up().withDetail("Last check in "+ LAST_CHECK.toString(), errorCode).build();
    }

    // Было бы отлично кэшировать от частых запросов, есть какой-нибудь встроенный механизм?
    public int check() {
        LocalDateTime timeToCheck = LocalDateTime.now();
        if(LAST_CHECK.plusSeconds(30).isBefore(timeToCheck) || BOOKS_COUNT_CACHE == -1 ) {
            BOOKS_COUNT_CACHE = bookService.count();
            LAST_CHECK = timeToCheck;
        }
        if(BOOKS_COUNT_CACHE < NORMAL_LIB_BOOKS_COUNT)
            return 0;
        return 1;
    }
}
