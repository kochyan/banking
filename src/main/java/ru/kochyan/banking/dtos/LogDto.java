package ru.kochyan.banking.dtos;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.kochyan.banking.enums.LogLevel;

import java.util.Date;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class LogDto {
    private Long id;
    private String author;
    private Date created;
    private String message;
    private LogLevel level;
}
