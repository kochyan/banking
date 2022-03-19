package ru.kochyan.banking.utils.mappers;

import ru.kochyan.banking.dtos.PaymentPurposeDto;
import ru.kochyan.banking.entities.PaymentPurpose;

public class PaymentPurposeMapper {
    public static PaymentPurposeDto toDto(PaymentPurpose entity) {
        return PaymentPurposeDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .status(entity.getStatus())
                .build();
    }
}
