package ru.sergey_gusarov.hw17.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.sergey_gusarov.hw17.domain.books.Author;
import ru.sergey_gusarov.hw17.service.books.AuthorService;

import static org.springframework.web.reactive.function.BodyExtractors.toMono;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
@EnableReactiveMongoRepositories("ru.sergey_gusarov.hw17.repository")
public class AuthorFunctionalConfig {
    private final AuthorService authorService;

    @Autowired
    public AuthorFunctionalConfig(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Bean
    RouterFunction<ServerResponse> getAllAuthorRoute() {
        return route(GET("/author"),
                req -> ok().body(authorService.findAll(), Author.class));
    }

    @Bean
    RouterFunction<ServerResponse> updateAuthorRoute() {
        return route(PUT("/author/"),
                req -> req.body(toMono(Author.class))
                        .doOnNext(authorService::save)
                        .then(ok().build()));
    }

    @Bean
    RouterFunction<ServerResponse> deleteAuthor() {
        return route(DELETE("/author/{id}"),
                req -> ok().body(
                        authorService.deleteByIdAndRetList(req.pathVariable("id")),
                        Author.class));
    }

}
