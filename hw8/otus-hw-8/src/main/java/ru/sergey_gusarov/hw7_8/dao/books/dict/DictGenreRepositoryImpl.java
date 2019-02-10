package ru.sergey_gusarov.hw7_8.dao.books.dict;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sergey_gusarov.hw7_8.domain.books.Genre;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class DictGenreRepositoryImpl implements DictGenreRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Long count() {
        TypedQuery<Long> query = (TypedQuery<Long>) em.createQuery("select count(g) from Genre g ");
        return query.getSingleResult();
    }

    @Override
    public void insert(Genre genre) {
        em.persist(genre);
    }

    @Override
    public Genre getById(long id) {
        return em.find(Genre.class, id);
    }

    @Override
    public Genre getByName(String name) {
        TypedQuery<Genre> query = em.createQuery(
                "select a from Genre a where a.name = :name",
                Genre.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public List<Genre> findAll() {
        TypedQuery<Genre> query = em.createQuery(
                "select g from Genre g",
                Genre.class);
        return query.getResultList();
    }

    @Override
    public Genre update(Genre genre) {
        return em.merge(genre);
    }

    @Override
    public void delete(Genre genre) {
        em.remove(genre);
    }

    @Override
    public void deleteById(long id) {
        this.delete(em.find(Genre.class,  id));
    }

    @Override
    @Transactional
    public Genre save(Genre genre) {
        try {
            return this.getByName(genre.getName());
        }catch (NoResultException noResultException){
            this.insert(genre);
            return this.getByName(genre.getName());
        }
    }
}
