package ru.sergey_gusarov.hw15;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@SpringBootApplication
//@EnableMongoRepositories("ru.sergey_gusarov.hw15.repository")
public class Main {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplication.run(Main.class);
    }
}
