package ru.kochyan.banking.services.impl;

import org.springframework.stereotype.Service;
import ru.kochyan.banking.entities.Log;
import ru.kochyan.banking.repos.LogRepo;
import ru.kochyan.banking.services.LogService;

@Service
public class LogServiceImpl extends AbstractServiceImpl<Log> implements LogService {
    private final LogRepo logRepo;

    public LogServiceImpl(LogRepo logRepo) {
        super(logRepo);
        this.logRepo = logRepo;
    }
}
