package ru.kochyan.banking.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kochyan.banking.entities.User;
import ru.kochyan.banking.repos.UserRepo;
import ru.kochyan.banking.services.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl extends AbstractServiceImpl<User> implements UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        super(userRepo);
        this.userRepo = userRepo;
    }


    @Override
    public Optional<User> findByCredentials(String username, String password) {
        return userRepo.FIND_USER_BY_CREDENTIALS(username, password);
    }
}