package ru.kochyan.banking.utils.mappers;

import ru.kochyan.banking.dtos.EagerCheckingAccountDto;
import ru.kochyan.banking.entities.CheckingAccount;

import java.util.stream.Collectors;

public class CheckingAccountMapper {
    public static EagerCheckingAccountDto toDto(final CheckingAccount entity) {
        return EagerCheckingAccountDto.builder()
                .id(entity.getId())
                .value(entity.getValue())
                .status(entity.getStatus())
                .paymentPurposes(
                        entity.getPaymentPurposes().stream()
                                .map(PaymentPurposeMapper::toDto)
                                .collect(Collectors.toSet())
                )
                .build();
    }
}
