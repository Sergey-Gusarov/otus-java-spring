package ru.sergey_gusarov.hw7_8.dao.books;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sergey_gusarov.hw7_8.domain.books.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class BookRepositoryImpl implements BookRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Long count() {
        TypedQuery<Long> query = (TypedQuery<Long>) em.createQuery("select count(b) from Book b ");
        return query.getSingleResult();
    }

    @Override
    public void insert(Book book) {
        em.persist(book);
    }

    @Override
    public Book getById(long id) {
        return em.find(Book.class, id);
    }

    @Override
    public Book getByTitle(String title) {
        TypedQuery<Book> query = (TypedQuery<Book>) em.createQuery("select b from Book b where b.title = :title ");
        query.setParameter("title", title);
        return query.getSingleResult();
    }

    @Override
    public List<Book> findAll() {
        TypedQuery<Book> query = em.createQuery(
                "select b from Book b",
                Book.class);
        return query.getResultList();
    }

    @Override
    public Book update(Book book) {
        return em.merge(book);
    }

    @Override
    public void delete(Book book) {
        em.remove(book);
    }

    @Override
    public void deleteById(long id) {
        this.delete(em.find(Book.class, id));
    }
}
