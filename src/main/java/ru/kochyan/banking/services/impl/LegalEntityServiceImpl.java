package ru.kochyan.banking.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kochyan.banking.dtos.EagerCheckingAccountDto;
import ru.kochyan.banking.dtos.EagerLegalEntityDto;
import ru.kochyan.banking.entities.CheckingAccount;
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
        Optional<LegalEntity> optionalEntity = legalEntityRepo.findById(dto.getId());
        if (optionalEntity.isPresent()) {
            LegalEntity entity = optionalEntity.get();

            List<Long> active = dto.getCheckingAccounts().stream()
                    .map(EagerCheckingAccountDto::getId)
                    .collect(Collectors.toList());

            for (CheckingAccount account : entity.getCheckingAccounts()) {
                if (!active.isEmpty()) {
                    if (!active.contains(account.getId())) {
                        account.setStatus(Status.DELETED);
                    }
                } else {
                    account.setStatus(Status.DELETED);
                }
            }

            entity.setName(dto.getName());
            LegalEntity updated = legalEntityRepo.save(entity);
            updated.getCheckingAccounts().removeIf(acc -> acc.getStatus().equals(Status.DELETED));
            return LegalEntityMapper.toDto(updated);
        } else {
            throw new EntityNotFoundException();
        }
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
        List<LegalEntity> entities = legalEntityRepo.findAllByStatus(status.name());
        entities.forEach(
                legal -> legal.getCheckingAccounts()
                        .removeIf(acc -> acc.getStatus().equals(Status.DELETED))
        );
        return entities.stream()
                .map(LegalEntityMapper::toDto)
                .collect(Collectors.toList());
    }
}