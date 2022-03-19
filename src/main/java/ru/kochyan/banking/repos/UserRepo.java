package ru.kochyan.banking.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kochyan.banking.entities.User;

import java.util.Optional;

@Repository
public interface UserRepo extends AbstractRepo<User> {
    @Query(value = "SELECT * FROM find_user_by_credentials(:username, :password) LIMIT 1", nativeQuery = true)
    Optional<User> FIND_USER_BY_CREDENTIALS(@Param("username") String username, @Param("password") String password);

    User findByUsername(String username);
}
