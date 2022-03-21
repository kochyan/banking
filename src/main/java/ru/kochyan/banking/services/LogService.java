package ru.kochyan.banking.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.kochyan.banking.entities.Log;

public interface LogService extends AbstractService<Log>{
    Page<Log> findAll(Pageable pageable);
}
