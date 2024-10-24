package com.demo.rest.book.view;

import com.demo.rest.book.model.AuthorsModel;
import com.demo.rest.book.model.BooksModel;
import com.demo.rest.book.service.AuthorService;
import com.demo.rest.component.ModelFunctionFactory;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

@ViewScoped
@Named
public class AuthorList implements Serializable {
    private final AuthorService service;
    private final ModelFunctionFactory factory;

    private AuthorsModel authors;

    @Inject
    public AuthorList(AuthorService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    public AuthorsModel getAuthors() {
        if (authors == null) {
            authors = factory.authorsToModel().apply(service.findAll());
        }
        return authors;
    }

    public String deleteAction(AuthorsModel.Author author) {
        service.delete(author.getId());
        return "author_list?faces-redirect=true";
    }
}
