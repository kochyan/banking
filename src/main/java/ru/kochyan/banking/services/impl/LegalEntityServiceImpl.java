package ru.kochyan.banking.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kochyan.banking.dtos.EagerLegalEntityDto;
import ru.kochyan.banking.entities.LegalEntity;
import ru.kochyan.banking.enums.Status;
import ru.kochyan.banking.repos.LegalEntityRepo;
import ru.kochyan.banking.services.LegalEntityService;
import ru.kochyan.banking.utils.mappers.LegalEntityMapper;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
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
    public List<EagerLegalEntityDto> eagerFindAll() {
        return legalEntityRepo.findAll().stream()
                .map(LegalEntityMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public EagerLegalEntityDto update(EagerLegalEntityDto dto) {

        return null;
    }

    @Override
    public void delete(Long id) throws EntityNotFoundException {
        Optional<LegalEntity> optionalEntity = legalEntityRepo.findById(id);
        if (optionalEntity.isPresent()) {
            LegalEntity entity = optionalEntity.get();
            entity.setStatus(Status.DELETED);
            legalEntityRepo.save(entity);
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public List<EagerLegalEntityDto> eagerFindAllByStatus(Status status) {
        return legalEntityRepo.findAllByStatus(status).stream()
                .map(LegalEntityMapper::toDto)
                .collect(Collectors.toList());
    }
}