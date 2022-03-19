package ru.kochyan.banking.services;

import ru.kochyan.banking.dtos.EagerLegalEntityDto;
import ru.kochyan.banking.entities.LegalEntity;

import java.util.List;

public interface LegalEntityService extends AbstractService<LegalEntity> {
    List<EagerLegalEntityDto> findAllEager();

    EagerLegalEntityDto update(EagerLegalEntityDto dto);

    void delete(Long id);
}
