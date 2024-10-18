package com.demo.rest.book.controller.api;

import com.demo.rest.book.dto.GetAuthorResponse;
import com.demo.rest.book.dto.GetAuthorsResponse;

import java.util.UUID;

public interface AuthorController {
    GetAuthorsResponse getAuthors();
    GetAuthorResponse getAuthor(UUID id);
}
