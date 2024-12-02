package com.demo.rest.authorization.interceptor;

import com.demo.rest.authorization.exception.NoPrincipalException;
import com.demo.rest.authorization.exception.NoRolesException;
import com.demo.rest.authorization.interceptor.biding.AllowAdminOrOwner;
import com.demo.rest.book.entity.Book;
import com.demo.rest.book.service.BookService;
import com.demo.rest.user.entity.UserRoles;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.security.enterprise.SecurityContext;

import java.util.Optional;
import java.util.UUID;

@Interceptor
@AllowAdminOrOwner
@Priority(10)
public class AllowAdminOrOwnerInterceptor {

    private final SecurityContext securityContext;
    private final BookService bookService;

    @Inject
    public AllowAdminOrOwnerInterceptor(SecurityContext securityContext, BookService bookService) {
        this.securityContext = securityContext;
        this.bookService = bookService;
    }

    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception {
        if (securityContext.getCallerPrincipal() == null) {
            throw new NoPrincipalException();
        }
        if (authorized(context)) {
            return context.proceed();
        }
        throw new NoRolesException();
    }

    private boolean authorized(InvocationContext context) {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return true;
        } else if (securityContext.isCallerInRole(UserRoles.USER)) {
            Object provided = context.getParameters()[0];
            Optional<Book> book;
            if (provided instanceof Book) {
                book = bookService.find(((Book) provided).getId());
            } else if (provided instanceof UUID) {
                book = bookService.find((UUID) provided);
            } else {
                throw new IllegalStateException("No author of UUID as first method parameter.");
            }

            return book.isPresent()
                    && book.get().getUser().getLogin().equals(securityContext.getCallerPrincipal().getName());
        }
        return false;
    }
}
