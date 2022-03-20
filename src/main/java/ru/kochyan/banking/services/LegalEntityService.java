package ru.kochyan.banking.services;

import ru.kochyan.banking.dtos.EagerLegalEntityDto;
import ru.kochyan.banking.entities.LegalEntity;
import ru.kochyan.banking.enums.Status;

import java.util.List;

public interface LegalEntityService extends AbstractService<LegalEntity> {
    List<EagerLegalEntityDto> eagerFindAll();

    EagerLegalEntityDto update(EagerLegalEntityDto dto);

    void delete(Long id);

    List<EagerLegalEntityDto> eagerFindAllByStatus(Status status);
}
