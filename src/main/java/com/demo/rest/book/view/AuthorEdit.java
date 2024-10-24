package com.demo.rest.book.view;

import com.demo.rest.book.entity.Author;
import com.demo.rest.book.model.AuthorEditModel;
import com.demo.rest.book.service.AuthorService;
import com.demo.rest.component.ModelFunctionFactory;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class AuthorEdit implements Serializable {
    private final AuthorService service;
    private final ModelFunctionFactory factory;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private AuthorEditModel author;

    @Inject
    public AuthorEdit(AuthorService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    public void init() throws IOException {
        Optional<Author> author = service.find(id);
        if (author.isPresent()) {
            this.author = factory.authorToEditModel().apply(author.get());
            System.out.println("========================");
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Author not found");
        }
    }

    public String saveAction() {
        service.update(factory.updateAuthor().apply(service.find(id).orElseThrow(), author));
        return "/author/author_list.xhtml?faces-redirect=true";
    }
}
