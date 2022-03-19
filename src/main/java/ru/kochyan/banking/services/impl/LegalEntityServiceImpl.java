package ru.kochyan.banking.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kochyan.banking.dtos.EagerLegalEntityDto;
import ru.kochyan.banking.entities.LegalEntity;
import ru.kochyan.banking.repos.LegalEntityRepo;
import ru.kochyan.banking.services.LegalEntityService;
import ru.kochyan.banking.utils.mappers.LegalEntityMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LegalEntityServiceImpl extends AbstractServiceImpl<LegalEntity> implements LegalEntityService {
    private final LegalEntityRepo legalEntityRepo;

    @Autowired
    public LegalEntityServiceImpl(LegalEntityRepo legalEntityRepo) {
        super(legalEntityRepo);
        this.legalEntityRepo = legalEntityRepo;
    }

    @Override
    public List<EagerLegalEntityDto> findAllEager() {
        return legalEntityRepo.findAll().stream()
                .map(LegalEntityMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public EagerLegalEntityDto update(EagerLegalEntityDto dto) {
        // update logic
        return null;
    }

    @Override
    public void delete(Long id) {
        // delete logic
    }
}