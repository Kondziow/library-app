package com.demo.rest.book.repository.persistence;

import com.demo.rest.book.entity.Author;
import com.demo.rest.book.entity.Book;
import com.demo.rest.book.entity.Book_;
import com.demo.rest.book.repository.api.BookRepository;
import com.demo.rest.user.entity.User;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

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
        em.refresh(em.find(Author.class, author.getId()));
        return author.getBooks();
    }

    @Override
    public List<Book> findAllByUser(User user) {
//        return em.createQuery("select b from Book b where b.user = :user", Book.class)
//                .setParameter("user", user)
//                .getResultList();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> root = query.from(Book.class);
        query.select(root)
                .where(cb.equal(root.get(Book_.user), user));
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Book> findByAuthorAndUser(User user, Author author) {
//        return em.createQuery("select b from Book b where b.author.id = :id and b.user = :user", Book.class)
//                .setParameter("user", user)
//                .setParameter("id", author.getId())
//                .getResultList();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> root = query.from(Book.class);
        query.select(root)
                .where(cb.and(
                        cb.equal(root.get(Book_.user), user),
                        cb.equal(root.get(Book_.author), author)
                ));
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Book> findAll() {

//        return em.createQuery("select b from Book b", Book.class).getResultList();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> root = query.from(Book.class);
        query.select(root);
        return em.createQuery(query).getResultList();
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
//            return Optional.of(em.createQuery("select b from Book b where b.id = :id and b.user = :user", Book.class)
//                    .setParameter("user", user)
//                    .setParameter("id", id)
//                    .getSingleResult());

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Book> query = cb.createQuery(Book.class);
            Root<Book> root = query.from(Book.class);
            query.select(root)
                    .where(cb.and(
                            cb.equal(root.get(Book_.user), user),
                            cb.equal(root.get(Book_.id), id)
                    ));
            return Optional.of(em.createQuery(query).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }
}
