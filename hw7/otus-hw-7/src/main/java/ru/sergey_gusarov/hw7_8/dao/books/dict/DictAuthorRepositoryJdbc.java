package ru.sergey_gusarov.hw7_8.dao.books.dict;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sergey_gusarov.hw7_8.domain.books.Author;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
//@Transactional
public class DictAuthorRepositoryJdbc implements DictAuthorRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Long count() {
        TypedQuery<Long> query = (TypedQuery<Long>) em.createQuery("select count(a) from Author a ");
        return query.getSingleResult();
    }

    @Override
    public void insert(Author author) {
        em.persist(author);
    }

    @Override
    public Author getById(long id) {
        return em.find(Author.class, id);
    }

    @Override
    public Author getByName(String name) {
        TypedQuery<Author> query = em.createQuery(
                "select a from Author a where a.name = :name",
                Author.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public List<Author> findAll() {
        TypedQuery<Author> query = em.createQuery(
                "select a from Author a",
                Author.class);
        return query.getResultList();
    }

    @Override
    public Author update(Author author) {
        return em.merge(author);
    }

    @Override
    public void delete(Author author) {
        em.remove(author);
    }

    @Override
    public void deleteById(long id) {
        this.delete(em.find(Author.class,  id));
    }

    @Override
    @Transactional
    public Author save(Author author) {
        try {
            return this.getByName(author.getName());
        }catch (NoResultException noResultException){
            this.insert(author);
            return this.getByName(author.getName());
        }
    }
}
