package com.demo.rest.book.service;

import com.demo.rest.book.entity.Author;
import com.demo.rest.book.repository.api.AuthorRepository;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
@Log
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Inject
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Optional<Author> find(UUID id) { return authorRepository.find(id);}

    public List<Author> findAll() { return authorRepository.findAll();}

    public void create(Author author) { authorRepository.create(author);}

    public void update(Author author) { authorRepository.update(author);}

    public void delete(UUID id) {authorRepository.delete(authorRepository.find(id).orElseThrow());}
}
