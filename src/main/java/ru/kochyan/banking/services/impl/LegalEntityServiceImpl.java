package ru.kochyan.banking.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kochyan.banking.dtos.EagerCheckingAccountDto;
import ru.kochyan.banking.dtos.EagerLegalEntityDto;
import ru.kochyan.banking.entities.CheckingAccount;
import ru.kochyan.banking.entities.LegalEntity;
import ru.kochyan.banking.enums.EntityStatus;
import ru.kochyan.banking.enums.LogLevel;
import ru.kochyan.banking.facades.LogFacade;
import ru.kochyan.banking.repos.LegalEntityRepo;
import ru.kochyan.banking.services.LegalEntityService;
import ru.kochyan.banking.utils.SecurityUtil;
import ru.kochyan.banking.utils.mappers.LegalEntityMapper;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LegalEntityServiceImpl extends AbstractServiceImpl<LegalEntity> implements LegalEntityService {
    private final LegalEntityRepo legalEntityRepo;
    private final LogFacade logFacade;

    @Autowired
    public LegalEntityServiceImpl(LegalEntityRepo legalEntityRepo, LogFacade logFacade) {
        super(legalEntityRepo);
        this.legalEntityRepo = legalEntityRepo;
        this.logFacade = logFacade;
    }

    @Override
    public List<EagerLegalEntityDto> eagerFindAll() {
        logFacade.write(
                "Запрос на получение юр. лиц",
                LogLevel.INFO,
                SecurityUtil.getLoggedUser()
        );

        return legalEntityRepo.findAll().stream()
                .map(LegalEntityMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public EagerLegalEntityDto update(EagerLegalEntityDto dto) {
        Optional<LegalEntity> optionalEntity = legalEntityRepo.findById(dto.getId());
        try {
            if (optionalEntity.isPresent()) {
                LegalEntity entity = optionalEntity.get();

                List<Long> active = dto.getCheckingAccounts().stream()
                        .map(EagerCheckingAccountDto::getId)
                        .collect(Collectors.toList());

                for (CheckingAccount account : entity.getCheckingAccounts()) {
                    if (!active.isEmpty()) {
                        if (!active.contains(account.getId())) {
                            account.setStatus(EntityStatus.DELETED);
                        }
                    } else {
                        account.setStatus(EntityStatus.DELETED);
                    }
                }

                entity.setName(dto.getName());
                LegalEntity updated = legalEntityRepo.save(entity);
                updated.getCheckingAccounts().removeIf(acc -> acc.getStatus().equals(EntityStatus.DELETED));

                logFacade.write(
                        String.format("Обновление юр.лица с названием '%s'", dto.getName()),
                        LogLevel.INFO,
                        SecurityUtil.getLoggedUser()
                );

                return LegalEntityMapper.toDto(updated);
            } else {
                throw new EntityNotFoundException(
                        String.format("Ошибка обновления. Сущность с id '%s' и названием '%s' не найдена", dto.getId(), dto.getName())
                );
            }
        } catch (EntityNotFoundException e) {
            logFacade.write(e.getMessage(), LogLevel.ERROR, SecurityUtil.getLoggedUser());
        }

        return dto;
    }

    @Override
    public void delete(Long id) {
        Optional<LegalEntity> optionalEntity = legalEntityRepo.findById(id);
        try {
            if (optionalEntity.isPresent()) {
                LegalEntity entity = optionalEntity.get();
                entity.setStatus(EntityStatus.DELETED);
                legalEntityRepo.save(entity);

                logFacade.write(
                        String.format("Успешное удаление сущности '%s'", entity.getName()),
                        LogLevel.INFO,
                        SecurityUtil.getLoggedUser()
                );
            } else {
                throw new EntityNotFoundException(
                        String.format("Ошибка обновления. Сущность с id '%s' не найдена", id)
                );
            }
        } catch (EntityNotFoundException e) {
            logFacade.write(e.getMessage(), LogLevel.WARN, SecurityUtil.getLoggedUser());
        }
    }

    @Override
    public List<EagerLegalEntityDto> eagerFindAllByStatus(EntityStatus status) {
        List<LegalEntity> entities = legalEntityRepo.findAllByStatus(status.name());
        entities.forEach(
                legal -> legal.getCheckingAccounts()
                        .removeIf(acc -> acc.getStatus().equals(EntityStatus.DELETED))
        );

        logFacade.write(
                String.format("Запрос на получение юр. лиц со статусом '%s'", status.name()),
                LogLevel.INFO,
                SecurityUtil.getLoggedUser()
        );

        return entities.stream()
                .map(LegalEntityMapper::toDto)
                .collect(Collectors.toList());
    }
}