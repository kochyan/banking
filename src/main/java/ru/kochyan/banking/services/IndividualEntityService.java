package ru.kochyan.banking.services;

import ru.kochyan.banking.dtos.IndividualEntityDto;
import ru.kochyan.banking.entities.IndividualEntity;
import ru.kochyan.banking.enums.EntityStatus;

import java.util.List;

public interface IndividualEntityService extends AbstractService<IndividualEntity> {
    List<IndividualEntity> findAllByStatus(EntityStatus status);

    void delete(Long id);

    IndividualEntityDto update(IndividualEntityDto dto);
}
