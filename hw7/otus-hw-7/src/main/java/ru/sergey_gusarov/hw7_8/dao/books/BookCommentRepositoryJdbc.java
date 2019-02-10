package ru.sergey_gusarov.hw7_8.dao.books;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sergey_gusarov.hw7_8.domain.books.BookComment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
//@Transactional
public class BookCommentRepositoryJdbc implements BookCommentRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Long count() {
        TypedQuery<Long> query = (TypedQuery<Long>) em.createQuery("select count(b) from BookComment b ");
        return query.getSingleResult();
    }

    @Override
    public void insert(BookComment bookComment) {
        em.persist(bookComment);
        em.flush();
    }

    @Override
    public BookComment getById(long id) {
        return em.find(BookComment.class, id);
    }

    @Override
    public List<BookComment> findAll() {
        TypedQuery<BookComment> query = em.createQuery(
                "select b from BookComment b",
                BookComment.class);
        return query.getResultList();
    }

    @Override
    public BookComment update(BookComment bookComment) {
        return em.merge(bookComment);
    }

    @Override
    public void delete(BookComment bookComment) {
        em.remove(bookComment);
    }

    @Override
    public void deleteById(long id) {
        this.delete(em.find(BookComment.class,  id));
    }

}
