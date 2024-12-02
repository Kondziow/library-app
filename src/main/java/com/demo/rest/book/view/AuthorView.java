package com.demo.rest.book.view;

import com.demo.rest.book.entity.Author;
import com.demo.rest.book.model.AuthorModel;
import com.demo.rest.book.model.BooksModel;
import com.demo.rest.book.service.AuthorService;
import com.demo.rest.book.service.BookService;
import com.demo.rest.component.ModelFunctionFactory;
import jakarta.ejb.EJB;
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
public class AuthorView implements Serializable {
    private AuthorService authorService;
    private BookService bookService;
    private final ModelFunctionFactory factory;

    @Getter
    @Setter
    private UUID id;

    @Getter
    private AuthorModel author;

    @Getter
    private BooksModel books;

    @Inject
    public AuthorView(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    private void setBookService(BookService service) {
        this.bookService = service;
    }

    @EJB
    private void setAuthorService(AuthorService service) {
        this.authorService = service;
    }

    public void init() throws IOException {
        Optional<Author> author = authorService.find(id);
        if (author.isPresent()) {
            this.author = factory.authorToModel().apply(author.get());
            this.books = factory.booksToModel().apply(bookService.findByAuthorForCallerPrincipal(id));
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Author not found");
        }
    }


    public String deleteBook(BooksModel.Book book) {
        bookService.delete(book.getId());
        return "author_view?faces-redirect=true&id=" + this.id;
    }
}
