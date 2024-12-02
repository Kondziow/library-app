package com.demo.rest.book.repository.persistence;

import com.demo.rest.book.entity.Author;
import com.demo.rest.book.entity.Book;
import com.demo.rest.book.repository.api.BookRepository;
import com.demo.rest.user.entity.User;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class BookPersistenceRepository implements BookRepository {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Book> findAllByAuthor(Author author) {
        return author.getBooks();
    }

    @Override
    public List<Book> findAllByUser(User user) {
        return em.createQuery("select b from Book b where b.user = :user", Book.class)
                .setParameter("user", user)
                .getResultList();
    }

    @Override
    public List<Book> findByAuthorAndUser(User user, Author author) {
        return em.createQuery("select b from Book b where b.author.id = :id and b.user = :user", Book.class)
                .setParameter("user", user)
                .setParameter("id", author.getId())
                .getResultList();
    }

    @Override
    public List<Book> findAll() {
        return em.createQuery("select b from Book b", Book.class).getResultList();
    }

    @Override
    public Optional<Book> find(UUID id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public void create(Book entity) {
        em.persist(entity);
        em.refresh(em.find(Author.class, entity.getAuthor().getId()));
        em.refresh(em.find(User.class, entity.getUser().getId()));
    }

    @Override
    public void update(Book entity) {
        em.merge(entity);
    }

    @Override
    public void delete(Book entity) {
        em.remove(em.find(Book.class, entity.getId()));
    }

    @Override
    public Optional<Book> findByIdAndUser(UUID id, User user) {
        try {
            return Optional.of(em.createQuery("select b from Book b where b.id = :id and b.user = :user", Book.class)
                    .setParameter("user", user)
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }
}
