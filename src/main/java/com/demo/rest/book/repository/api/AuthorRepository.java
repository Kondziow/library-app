package com.demo.rest.book.repository.api;

import com.demo.rest.book.entity.Author;
import com.demo.rest.repository.api.Repository;

import java.util.UUID;

public interface AuthorRepository extends Repository<Author, UUID> {
}
