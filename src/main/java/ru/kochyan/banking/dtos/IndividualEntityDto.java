package ru.kochyan.banking.dtos;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.kochyan.banking.enums.EntityStatus;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class IndividualEntityDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String patronymic;
    private EntityStatus status;
}
