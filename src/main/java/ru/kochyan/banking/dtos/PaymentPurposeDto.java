package ru.kochyan.banking.dtos;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.kochyan.banking.enums.Status;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class PaymentPurposeDto {
    private Long id;
    private String name;
    private Status status;
}