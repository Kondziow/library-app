package com.demo.rest.book.view;

import com.demo.rest.book.entity.Book;
import com.demo.rest.book.model.AuthorModel;
import com.demo.rest.book.model.BookEditModel;
import com.demo.rest.book.service.AuthorService;
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
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ViewScoped
@Named
public class BookEdit implements Serializable {
    private final BookService service;
    private final AuthorService authorService;
    private final ModelFunctionFactory factory;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private BookEditModel book;

    @Getter
    private List<AuthorModel> authors;

    @Inject
    public BookEdit(BookService service, AuthorService authorService, ModelFunctionFactory factory) {
        this.service = service;
        this.authorService = authorService;
        this.factory = factory;
    }

    public void init() throws IOException {
        Optional<Book> book = service.find(id);
        if (book.isPresent()) {
            authors = authorService.findAll().stream()
                    .map(factory.authorToModel())
                    .collect(Collectors.toList());
            this.book = factory.bookToEditModel().apply(book.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Book not found");
        }
    }

    public String saveAction() {
        System.out.println(book);
        service.update(factory.updateBook().apply(service.find(id).orElseThrow(), book));
        return "/book/book_list.xhtml?faces-redirect=true";
    }
}
