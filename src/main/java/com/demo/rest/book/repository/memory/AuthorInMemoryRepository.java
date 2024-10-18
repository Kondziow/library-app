package com.demo.rest.book.repository.memory;

import com.demo.rest.book.entity.Author;
import com.demo.rest.book.repository.api.AuthorRepository;
import com.demo.rest.datastore.DataStore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class AuthorInMemoryRepository implements AuthorRepository {
    private final DataStore dataStore;

    @Inject
    public AuthorInMemoryRepository(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public List<Author> findAll() {
        return dataStore.findAllAuthors();
    }

    @Override
    public Optional<Author> find(UUID id) {
        return dataStore.findAllAuthors().stream()
                .filter((author) -> author.getId().equals(id))
                .findFirst();
    }

    @Override
    public void create(Author entity) {
        dataStore.createAuthor(entity);
    }

    @Override
    public void update(Author entity) {
        dataStore.updateAuthor(entity);
    }

    @Override
    public void delete(Author entity) {
        dataStore.deleteAuthor(entity.getId());
    }
}
