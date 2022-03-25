package ru.kochyan.banking.utils.mappers;

import ru.kochyan.banking.dtos.IndividualEntityDto;
import ru.kochyan.banking.entities.IndividualEntity;

public class IndividualEntityMapper {
    public static IndividualEntityDto toDto(IndividualEntity entity) {
        return IndividualEntityDto.builder()
                .id(entity.getId())
                .firstname(entity.getFirstname())
                .lastname(entity.getLastname())
                .patronymic(entity.getPatronymic())
                .status(entity.getStatus())
                .build();
    }
}
