package ru.kochyan.banking.utils.mappers;

import ru.kochyan.banking.dtos.EagerLegalEntityDto;
import ru.kochyan.banking.entities.LegalEntity;

import java.util.stream.Collectors;

public class LegalEntityMapper {
    public static EagerLegalEntityDto toDto(final LegalEntity entity) {
        return EagerLegalEntityDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .status(entity.getStatus())
                .checkingAccounts(
                        entity.getCheckingAccounts().stream()
                                .map(CheckingAccountMapper::toDto)
                                .collect(Collectors.toSet())
                )
                .build();
    }
}
