package com.demo.rest.book.repository.persistence;

import com.demo.rest.book.entity.Author;
import com.demo.rest.book.entity.Book;
import com.demo.rest.book.repository.api.AuthorRepository;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class AuthorPersistenceRepository implements AuthorRepository {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Author> findAll() {
        return em.createQuery("select a from Author a", Author.class).getResultList();
    }

    @Override
    public Optional<Author> find(UUID id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public void create(Author entity) {
        em.persist(entity);
    }

    @Override
    public void update(Author entity) {
        em.merge(entity);
    }

    @Override
    public void delete(Author entity) {
        for (Book book : entity.getBooks()) {
            em.remove(em.find(Book.class, book.getId()));
        }
        em.remove(em.find(Author.class, entity.getId()));
    }
}
