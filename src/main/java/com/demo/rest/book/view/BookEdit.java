package com.demo.rest.book.view;

import com.demo.rest.book.entity.Book;
import com.demo.rest.book.model.AuthorModel;
import com.demo.rest.book.model.BookEditModel;
import com.demo.rest.book.service.AuthorService;
import com.demo.rest.book.service.BookService;
import com.demo.rest.component.ModelFunctionFactory;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.OptimisticLockException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.TransactionalException;
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
    private BookService bookService;
    private AuthorService authorService;
    private final ModelFunctionFactory factory;
    private final FacesContext facesContext;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private BookEditModel book;

    @Getter
    private BookEditModel unsavedBook;

    @Getter
    private List<AuthorModel> authors;

    @Inject
    public BookEdit(ModelFunctionFactory factory, FacesContext facesContext) {
        this.factory = factory;
        this.facesContext = facesContext;
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
        Optional<Book> book = bookService.findForCallerPrincipal(id);
        if (book.isPresent()) {
            authors = authorService.findAll().stream()
                    .map(factory.authorToModel())
                    .collect(Collectors.toList());
            this.book = factory.bookToEditModel().apply(book.get());
        } else {
            facesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Book not found or user is not the owner");
        }
    }


    public String saveAction() throws IOException {
        try {
            bookService.update(factory.updateBook().apply(bookService.find(id).orElseThrow(), book));
            String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
            return viewId + "?faces-redirect=true&includeViewParams=true";
//            return "/book/book_list.xhtml?faces-redirect=true";
        } catch (Exception ex) {
            if (ex.getCause() instanceof OptimisticLockException) {
                unsavedBook = book;
                init();
                facesContext.addMessage(null, new FacesMessage("Version collision."));
            }
            return null;
        }
    }
}
