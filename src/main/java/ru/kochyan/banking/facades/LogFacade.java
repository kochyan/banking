package ru.kochyan.banking.facades;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kochyan.banking.entities.Log;
import ru.kochyan.banking.entities.User;
import ru.kochyan.banking.enums.LogLevel;
import ru.kochyan.banking.services.LogService;

@Service
@Slf4j
public class LogFacade {
    private final LogService logService;

    @Autowired
    public LogFacade(LogService logService) {
        this.logService = logService;
    }

    public void write(String message, LogLevel level, User author) {
        writeSLf4j(message, level);

        logService.save(
                Log.builder()
                        .message(message)
                        .author(author)
                        .level(level)
                        .build()
        );
    }

    public void write(String message, LogLevel level) {
        writeSLf4j(message, level);

        logService.save(
                Log.builder()
                        .message(message)
                        .level(level)
                        .build()
        );
    }

    private void writeSLf4j(String message, LogLevel level) {
        if (level.equals(LogLevel.INFO)) {
            log.info(message);
        } else if (level.equals(LogLevel.WARN)) {
            log.warn(message);
        } else if (level.equals(LogLevel.ERROR)) {
            log.error(message);
        }
    }

}
