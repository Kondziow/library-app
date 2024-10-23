package com.demo.rest.book.view;

import com.demo.rest.book.entity.Book;
import com.demo.rest.book.model.BookEditModel;
import com.demo.rest.book.service.BookService;
import com.demo.rest.component.ModelFunctionFactory;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class BookEdit {
    private final BookService service;
    private final ModelFunctionFactory factory;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private BookEditModel book;

    @Inject
    public BookEdit(BookService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    public void init() throws IOException {
        Optional<Book> book = service.find(id);
        if (book.isPresent()) {
            this.book = factory.bookToEditModel().apply(book.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Book not found");
        }
    }

    public String saveAction() {
        service.update(factory.updateBook().apply(service.find(id).orElseThrow(), book));
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }
}
