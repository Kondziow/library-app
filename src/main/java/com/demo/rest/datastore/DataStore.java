package com.demo.rest.datastore;

import com.demo.rest.book.entity.Author;
import com.demo.rest.book.entity.Book;
import com.demo.rest.serialization.CloningUtility;
import com.demo.rest.user.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class DataStore {
    private static final String avatarDirPath = "pictureDirectory";
    private final Path avatarPath;
    private final CloningUtility cloningUtility;

    private final Set<Author> authors = new HashSet<>();
    private final Set<Book> books = new HashSet<>();
    private final Set<User> users = new HashSet<>();

    @Inject
    public DataStore(CloningUtility cloningUtility) {
        this.avatarPath = Paths.get(avatarDirPath);
        this.cloningUtility = cloningUtility;
        System.out.println(avatarPath);

        createAvatarDirectory();
    }

    private void createAvatarDirectory() {
        try {
            Files.createDirectories(this.avatarPath);
            System.out.println("Directory created: " + this.avatarPath.toAbsolutePath());
        } catch (IOException e) {
            throw new IllegalStateException("Exception when creating picture directory", e);
        }
    }

    public List<Author> findAllAuthors() {
        return authors.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public void createAuthor(Author value) throws IllegalArgumentException {
        if (authors.stream().anyMatch((author) -> author.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The author id \"%s\" is not unique".formatted(value.getId()));
        } else {
            authors.add(cloningUtility.clone(value));
        }
    }

    public void updateAuthor(Author value) throws IllegalArgumentException {
        if (authors.removeIf((author) -> author.getId().equals(value.getId()))) {
            authors.add(cloningUtility.clone(value));
        } else {
            throw new NotFoundException("The author with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    public void deleteAuthor(UUID id) {
        books.removeIf(book -> book.getAuthor().getId().equals(id));

        if (!authors.removeIf(author -> author.getId().equals(id))) {
            throw new NotFoundException("The author with id \"%s\" does not exist".formatted(id));
        }
    }

    public List<Book> findAllBooks() {
        return books.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public void createBook(Book value) throws IllegalArgumentException {
        if (books.stream().anyMatch((book) -> book.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The book id \"%s\" is not unique".formatted(value.getId()));
        } else {
            Book entity = cloneBookWithRelationships(value);
            books.add(cloningUtility.clone(entity));
        }
    }

    public void updateBook(Book value) throws IllegalArgumentException {
        Book entity = cloneBookWithRelationships(value);
        if (books.removeIf((book) -> book.getId().equals(value.getId()))) {
            books.add(entity);
        } else {
            throw new NotFoundException("The book with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    public void deleteBook(UUID id) {
        if (!books.removeIf(book -> book.getId().equals(id))) {
            throw new NotFoundException("The book with id \"%s\" does not exist".formatted(id));
        }
    }

    public List<User> findAllUsers() {
        return users.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public void createUser(User value) throws IllegalArgumentException {
        if (users.stream().anyMatch((user) -> user.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The user id \"%s\" is not unique".formatted(value.getId()));
        } else {
            users.add(cloningUtility.clone(value));
        }
    }

    public void updateUser(User value) throws IllegalArgumentException {
        if (users.removeIf((user) -> user.getId().equals(value.getId()))) {
            users.add(cloningUtility.clone(value));
        } else {
            throw new NotFoundException("The user with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    public void deleteUser(UUID id) {
        if (!users.removeIf(user -> user.getId().equals(id))) {
            throw new NotFoundException("The user with id \"%s\" does not exist".formatted(id));
        }
    }

    public Optional<byte[]> getAvatar(UUID id) {
        Path avatarPath = getAvatarPath(id);
        try {
            if (Files.exists(avatarPath)) {
                return Optional.of(Files.readAllBytes(avatarPath));
            } else {
                return Optional.empty();
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not get avatar with id \"%s\"".formatted(id), e);
        }
    }

    public void updateAvatar(UUID id, byte[] avatarData) {
        Path avatarPath = getAvatarPath(id);
        try {
            Files.write(avatarPath, avatarData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAvatar(UUID id) {
        Path avatarPath = getAvatarPath(id);
        try {
            if (Files.exists(avatarPath)) {
                Files.delete(avatarPath);
            } else {
                throw new NotFoundException("There is no avatar with that id: \"%s\"".formatted(id));
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not delete avatar for id \"%s\"".formatted(id), e);
        }
    }

    public Path getAvatarPath(UUID userId) {
        return avatarPath.resolve(userId.toString() + ".png");
    }

    private Book cloneBookWithRelationships(Book value) {
        Book entity = cloningUtility.clone(value);

        if (entity.getUser() != null) {
            entity.setUser(users.stream()
                    .filter(user -> user.getId().equals(value.getUser().getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No user with id \"%s\".".formatted(value.getUser().getId()))));
        }

        if (entity.getAuthor() != null) {
            entity.setAuthor(authors.stream()
                    .filter(author -> author.getId().equals(value.getAuthor().getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No author with id \"%s\".".formatted(value.getAuthor().getId()))));
        }

        return entity;
    }
}
