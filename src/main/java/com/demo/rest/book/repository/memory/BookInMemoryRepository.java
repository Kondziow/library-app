package com.demo.rest.book.repository.memory;

import com.demo.rest.book.entity.Author;
import com.demo.rest.book.entity.Book;
import com.demo.rest.book.repository.api.BookRepository;
import com.demo.rest.datastore.DataStore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class BookInMemoryRepository implements BookRepository {
    private final DataStore dataStore;

    @Inject
    public BookInMemoryRepository(DataStore dataStore) {
        this.dataStore = dataStore;
    }
    @Override
    public List<Book> findAll() {
        return dataStore.findAllBooks();
    }

    @Override
    public Optional<Book> find(UUID id) {
        return dataStore.findAllBooks().stream()
                .filter((author) -> author.getId().equals(id))
                .findFirst();
    }

    @Override
    public void create(Book entity) {
        dataStore.createBook(entity);
    }

    @Override
    public void update(Book entity) {
        dataStore.updateBook(entity);
    }

        @Override
    public void delete(Book entity) {
            dataStore.deleteBook(entity.getId());
    }

    public List<Book> findAllByAuthor(Author author) {
        return dataStore.findAllBooks().stream()
                .filter(book -> author.equals(book.getAuthor()))
                .collect(Collectors.toList());
    }
}
