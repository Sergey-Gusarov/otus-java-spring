package ru.sergey_gusarov.hw15.repository.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.sergey_gusarov.hw15.domain.books.Author;

import java.util.List;

// Исключительно для тренировки
public class AuthorRepositoryImpl implements AuthorRepositoryCustom {
    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public List<Author> getAuthorByNumMethod(String authorNum) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(authorNum));
        List<Author> authors = mongoOperations.find(query, Author.class);
        return authors;
    }

    @Override
    public Author getAuthorByNum1Method(String authorNum) {
        BasicQuery query = new BasicQuery("{ name : 'AuthorName1' }");
        return mongoOperations.findOne(query, Author.class);
    }
}
