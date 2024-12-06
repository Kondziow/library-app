package com.demo.rest.book.view;

import com.demo.rest.book.model.AuthorsModel;
import com.demo.rest.book.service.AuthorService;
import com.demo.rest.component.ModelFunctionFactory;
import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

@ViewScoped
@Named
public class AuthorList implements Serializable {
    private AuthorService service;
    private final ModelFunctionFactory factory;

    private final FacesContext facesContext;

    private AuthorsModel authors;

    @Inject
    public AuthorList(ModelFunctionFactory factory, FacesContext facesContext) {
        this.factory = factory;
        this.facesContext = facesContext;
    }

    @EJB
    private void setService(AuthorService service) {
        this.service = service;
    }

    public AuthorsModel getAuthors() {
        if (authors == null) {
            authors = factory.authorsToModel().apply(service.findAll());
        }
        return authors;
    }

    public void deleteAction(AuthorsModel.Author author) {
        service.delete(author.getId());
        authors = factory.authorsToModel().apply(service.findAll());
    }
}
