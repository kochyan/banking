package ru.kochyan.banking.dtos;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.kochyan.banking.enums.Status;

import java.util.Set;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class EagerLegalEntityDto {
    private Long id;
    private String name;
    private Status status;
    private Set<EagerCheckingAccountDto> checkingAccounts;
}