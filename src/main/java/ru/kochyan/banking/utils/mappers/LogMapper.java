package ru.kochyan.banking.utils.mappers;

import ru.kochyan.banking.dtos.LogDto;
import ru.kochyan.banking.entities.Log;

public class LogMapper {
    public static LogDto toDto(Log entity){
        return LogDto.builder()
                .id(entity.getId())
                .author(entity.getAuthor().getUsername())
                .message(entity.getMessage())
                .created(entity.getCreated())
                .build();
    }
}
