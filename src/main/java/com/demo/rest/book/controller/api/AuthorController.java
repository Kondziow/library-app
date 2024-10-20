package com.demo.rest.book.controller.api;

import com.demo.rest.book.dto.GetAuthorResponse;
import com.demo.rest.book.dto.GetAuthorsResponse;
import com.demo.rest.book.dto.PatchAuthorRequest;
import com.demo.rest.book.dto.PutAuthorRequest;

import java.util.UUID;

public interface AuthorController {
    GetAuthorsResponse getAuthors();
    GetAuthorResponse getAuthor(UUID id);
    void putAuthor(UUID id, PutAuthorRequest request);
    void updateAuthor(UUID id, PatchAuthorRequest request);
    void deleteAuthor(UUID id);
}
