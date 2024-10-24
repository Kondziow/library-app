package com.demo.rest.book.view;

import com.demo.rest.book.model.AuthorCreateModel;
import com.demo.rest.book.service.AuthorService;
import com.demo.rest.component.ModelFunctionFactory;
import jakarta.enterprise.context.Conversation;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;

@ViewScoped
@Named
public class AuthorCreate implements Serializable {
    private final AuthorService service;
    private final ModelFunctionFactory factory;
    private final Conversation conversation;

    @Getter
    AuthorCreateModel author;

    @Inject
    public AuthorCreate(AuthorService service, ModelFunctionFactory factory, Conversation conversation) {
        this.service = service;
        this.factory = factory;
        this.conversation = conversation;
    }

    public void init() {
        if (conversation.isTransient()) {
            author = AuthorCreateModel.builder()
                    .id(UUID.randomUUID())
                    .build();
            conversation.begin();
        }
    }

    public String cancelAction() {
        conversation.end();
        return "/author/author_list.xhtml?faces-redirect=true";
    }

    public String saveAction() {
        service.create(factory.modelToAuthor().apply(author));
        conversation.end();
        System.out.println("==========================");
        System.out.println(author);
        System.out.println("==========================");
        return "/author/author_list.xhtml?faces-redirect=true";
    }

    public String getConversationId() {
        return conversation.getId();
    }
}
