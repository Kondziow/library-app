package com.demo.rest.repository.api;

import java.util.List;
import java.util.Optional;

public interface Repository<E, K> {
    List<E> findAll();
    Optional<E> find(K id);
    void create(E entity);
    void update(E entity);
    void delete(E entity);
}
