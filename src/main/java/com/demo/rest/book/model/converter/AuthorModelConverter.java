package com.demo.rest.book.model.converter;

import com.demo.rest.book.entity.Author;
import com.demo.rest.book.model.AuthorModel;
import com.demo.rest.book.service.AuthorService;
import com.demo.rest.component.ModelFunctionFactory;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

import java.util.Optional;
import java.util.UUID;

@FacesConverter(forClass = AuthorModel.class, managed = true)
public class AuthorModelConverter implements Converter<AuthorModel> {
    private final AuthorService service;
    private final ModelFunctionFactory factory;

    @Inject
    public AuthorModelConverter(AuthorService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public AuthorModel getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        Optional<Author> author = service.find(UUID.fromString(value));
        return author.map(factory.authorToModel()).orElse(null);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, AuthorModel value) {
        return value == null ? "" : value.getId().toString();
    }
}
