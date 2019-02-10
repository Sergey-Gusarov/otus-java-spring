package ru.sergey_gusarov.hw23;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@SpringBootApplication
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@EnableMongoRepositories("ru.sergey_gusarov.hw23.mongo.repository")
public class Main {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplication.run(Main.class);
    }
}
