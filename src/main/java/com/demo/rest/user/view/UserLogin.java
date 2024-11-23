package com.demo.rest.user.view;

import com.demo.rest.user.service.UserService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.Password;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import static jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams;

@RequestScoped
@Named
@Log
public class UserLogin {

    private final HttpServletRequest request;

    private final SecurityContext securityContext;

    private final FacesContext facesContext;

    private final UserService userService;

    @Inject
    public UserLogin(
            HttpServletRequest request,
            @SuppressWarnings("CdiInjectionPointsInspection") SecurityContext securityContext,
            FacesContext facesContext,
            UserService userService) {
        this.request = request;
        this.securityContext = securityContext;
        this.facesContext = facesContext;
        this.userService = userService;
    }

    @Getter
    @Setter
    private String login;

    @Getter
    @Setter
    private String password;

    @SneakyThrows
    public void loginAction() {
        Credential credential = new UsernamePasswordCredential(login, new Password(password));
        AuthenticationStatus status = securityContext.authenticate(request, extractResponseFromFacesContext(),
                withParams().credential(credential));
//        if (status == AuthenticationStatus.SUCCESS) {
//            //ATM there is no build-in CDI event to observe.
//            userService.updateCallerPrincipalLastLoginDateTime();
//        }
        facesContext.responseComplete();
    }

    private HttpServletResponse extractResponseFromFacesContext() {
        return (HttpServletResponse) facesContext.getExternalContext().getResponse();
    }
}