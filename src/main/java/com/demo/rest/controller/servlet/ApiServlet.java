package com.demo.rest.controller.servlet;

import com.demo.rest.book.controller.api.AuthorController;
import com.demo.rest.book.controller.api.BookController;
import com.demo.rest.book.dto.PatchAuthorRequest;
import com.demo.rest.book.dto.PutAuthorRequest;
import com.demo.rest.user.avatar.controller.api.AvatarController;
import com.demo.rest.user.controller.api.UserController;
import com.demo.rest.user.dto.PatchUserRequest;
import com.demo.rest.user.dto.PutUserRequest;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(
        urlPatterns = {
                "api/*"
        }
)
@MultipartConfig(maxFileSize = 200 * 1024)
public class ApiServlet extends HttpServlet {
    private final AuthorController authorController;
    private final BookController bookController;
    private final UserController userController;
    private final AvatarController avatarController;
    private final Jsonb jsonb = JsonbBuilder.create();

    public static final class Paths {
        public static final String API = "/api";
    }

    public static final class Patterns {
        private static final Pattern UUID = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");
        public static final Pattern AUTHOR = Pattern.compile("/authors/(%s)".formatted(UUID.pattern()));
        public static final Pattern AUTHORS = Pattern.compile("/authors/?");
        public static final Pattern BOOK = Pattern.compile("/books/(%s)".formatted(UUID.pattern()));
        public static final Pattern BOOKS = Pattern.compile("/books/?");
        public static final Pattern USER = Pattern.compile("/users/(%s)".formatted(UUID.pattern()));
        public static final Pattern USERS = Pattern.compile("/users/?");
        public static final Pattern USER_AVATAR = Pattern.compile("/users/(%s)/avatar".formatted(UUID.pattern()));
    }

    @Inject
    public ApiServlet(AuthorController authorController, BookController bookController, UserController userController, AvatarController avatarController) {
        this.authorController = authorController;
        this.bookController = bookController;
        this.userController = userController;
        this.avatarController = avatarController;
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equals("PATCH")) {
            doPatch(request, response);
        } else {
            super.service(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.AUTHORS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(authorController.getAuthors()));
                return;
            } else if (path.matches(Patterns.AUTHOR.pattern())) {
                response.setContentType("application/json");
                UUID id = extractUuid(Patterns.AUTHOR, path);
                response.getWriter().write(jsonb.toJson(authorController.getAuthor(id)));
                return;
            } else if (path.matches(Patterns.BOOKS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(bookController.getBooks()));
                return;
            } else if (path.matches(Patterns.BOOK.pattern())) {
                response.setContentType("application/json");
                UUID id = extractUuid(Patterns.BOOK, path);
                response.getWriter().write(jsonb.toJson(bookController.getBook(id)));
                return;
            } else if (path.matches(Patterns.USERS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(userController.getUsers()));
                return;
            } else if (path.matches(Patterns.USER.pattern())) {
                response.setContentType("application/json");
                UUID id = extractUuid(Patterns.USER, path);
                response.getWriter().write(jsonb.toJson(userController.getUser(id)));
                return;
            } else if (path.matches(Patterns.USER_AVATAR.pattern())) {
                response.setContentType("image/png");
                UUID id = extractUuid(Patterns.USER_AVATAR, path);
                byte[] avatar = avatarController.getAvatar(id);
                response.setContentLength(avatar.length);
                response.getOutputStream().write(avatar);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.AUTHOR.pattern())) {
                UUID id = extractUuid(Patterns.AUTHOR, path);
                authorController.putAuthor(id, jsonb.fromJson(request.getReader(), PutAuthorRequest.class));
                response.addHeader("Location", createUrl(request, Paths.API, "users", id.toString()));
                return;
            } else if (path.matches(Patterns.USER.pattern())) {
                UUID id = extractUuid(Patterns.USER, path);
                userController.putUser(id, jsonb.fromJson(request.getReader(), PutUserRequest.class));
                response.addHeader("Location", createUrl(request, Paths.API, "users", id.toString()));
                return;
            } else if (path.matches(Patterns.USER_AVATAR.pattern())) {
                UUID id = extractUuid(Patterns.USER_AVATAR, path);
                avatarController.putAvatar(id, request.getPart("avatar").getInputStream());
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.AUTHOR.pattern())) {
                UUID uuid = extractUuid(Patterns.AUTHOR, path);
                authorController.updateAuthor(uuid, jsonb.fromJson(request.getReader(), PatchAuthorRequest.class));
                return;
            } else if (path.matches(Patterns.USER.pattern())) {
                UUID uuid = extractUuid(Patterns.USER, path);
                userController.updateUser(uuid, jsonb.fromJson(request.getReader(), PatchUserRequest.class));
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.AUTHOR.pattern())) {
                UUID id = extractUuid(Patterns.AUTHOR, path);
                authorController.deleteAuthor(id);
                return;
            } else if (path.matches(Patterns.USER.pattern())) {
                UUID id = extractUuid(Patterns.USER, path);
                userController.deleteUser(id);
                return;
            } else if (path.matches(Patterns.USER_AVATAR.pattern())) {
                UUID id = extractUuid(Patterns.USER_AVATAR, path);
                avatarController.deleteAvatar(id);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private static UUID extractUuid(Pattern pattern, String path) {
        Matcher matcher = pattern.matcher(path);
        if (matcher.matches()) {
            return UUID.fromString(matcher.group(1));
        } else {
            throw new IllegalArgumentException("No UUID in path.");
        }
    }

    private String parseRequestPath(HttpServletRequest request) {
        String path = request.getPathInfo();
        path = path != null ? path : "";
        return path;
    }

    public static String createUrl(HttpServletRequest request, String... paths) {
        StringBuilder builder = new StringBuilder();
        builder.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append(request.getContextPath());

        for (String path : paths) {
            builder.append("/")
                    .append(path, path.startsWith("/") ? 1 : 0, path.endsWith("/") ? path.length() - 1 : path.length());
        }

        return builder.toString();
    }
}
