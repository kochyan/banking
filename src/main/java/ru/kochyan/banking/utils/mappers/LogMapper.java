package ru.kochyan.banking.utils.mappers;

import ru.kochyan.banking.dtos.LogDto;
import ru.kochyan.banking.entities.Log;
import ru.kochyan.banking.entities.User;

public class LogMapper {
    public static LogDto toDto(Log entity){
        return LogDto.builder()
                .id(entity.getId())
                .author(safeExtractUsername(entity.getAuthor()))
                .message(entity.getMessage())
                .level(entity.getLevel())
                .created(entity.getCreated())
                .build();
    }

    private static String safeExtractUsername(User author){
        return author != null ? author.getUsername() : "";
    }
}
