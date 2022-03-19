package ru.kochyan.banking.services;

import ru.kochyan.banking.entities.User;

import java.util.Optional;

public interface UserService extends AbstractService<User> {
    Optional<User> findByCredentials(String username, String password);
}
