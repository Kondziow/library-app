package com.demo.rest.book.view;

import com.demo.rest.book.model.AuthorModel;
import com.demo.rest.book.model.BookCreateModel;
import com.demo.rest.book.service.AuthorService;
import com.demo.rest.book.service.BookService;
import com.demo.rest.component.ModelFunctionFactory;
import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.extern.java.Log;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ConversationScoped
@Named
@Log
public class BookCreate implements Serializable {
    private final BookService bookService;
    private final AuthorService authorService;
    private final ModelFunctionFactory factory;
    private final Conversation conversation;

    @Getter
    BookCreateModel book;

    @Getter
    private List<AuthorModel> authors;

    @Inject
    public BookCreate(BookService bookService, AuthorService authorService, ModelFunctionFactory factory, Conversation conversation) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.factory = factory;
        this.conversation = conversation;
    }

    public void init() {
        if (conversation.isTransient()) {
            authors = authorService.findAll().stream()
                    .map(factory.authorToModel())
                    .collect(Collectors.toList());
            book = BookCreateModel.builder()
                    .id(UUID.randomUUID())
                    .build();
            conversation.begin();
        }
    }

    public String goToAuthorAction() {
        return "/book/book_create__author.xhtml?faces-redirect=true";
    }
    public Object goToBasicAction() {
        return "/book/book_create__basic.xhtml?faces-redirect=true";
    }

    public String cancelAction() {
        conversation.end();
        return "/book/book_list.xhtml?faces-redirect=true";
    }

    public String goToConfirmAction() {
        return "/book/book_create__confirm.xhtml?faces-redirect=true";
    }

    public String saveAction() {
        bookService.create(factory.modelToBook().apply(book));
        conversation.end();
        return "/book/book_list.xhtml?faces-redirect=true";
    }

    public String getConversationId() {
        return conversation.getId();
    }
}
