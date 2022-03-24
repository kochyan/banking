package ru.kochyan.banking.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import ru.kochyan.banking.entities.Log;

import java.util.List;

public interface LogRepo extends AbstractRepo<Log> {
    @Override
    @NonNull
    @Query(value = "SELECT * FROM find_log()", nativeQuery = true)
    List<Log> findAll();
}
