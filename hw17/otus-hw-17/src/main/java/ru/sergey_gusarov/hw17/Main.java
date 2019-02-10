package ru.sergey_gusarov.hw17;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.sergey_gusarov.hw17.config.HelloWebClient;


@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class);

        HelloWebClient gwc = new HelloWebClient();
        System.out.println(gwc.getResult());

    }
}
