package ru.kochyan.banking.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kochyan.banking.dtos.IndividualEntityDto;
import ru.kochyan.banking.entities.IndividualEntity;
import ru.kochyan.banking.enums.EntityStatus;
import ru.kochyan.banking.enums.LogLevel;
import ru.kochyan.banking.facades.LogFacade;
import ru.kochyan.banking.repos.IndividualEntityRepo;
import ru.kochyan.banking.services.IndividualEntityService;
import ru.kochyan.banking.utils.SecurityUtil;
import ru.kochyan.banking.utils.mappers.IndividualEntityMapper;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class IndividualEntityServiceImpl extends AbstractServiceImpl<IndividualEntity> implements IndividualEntityService {
    private final IndividualEntityRepo individualEntityRepo;
    private final LogFacade logFacade;

    @Autowired
    public IndividualEntityServiceImpl(IndividualEntityRepo individualEntityRepo, LogFacade logFacade) {
        super(individualEntityRepo);
        this.individualEntityRepo = individualEntityRepo;
        this.logFacade = logFacade;
    }

    @Override
    public List<IndividualEntity> findAllByStatus(EntityStatus status) {
        return individualEntityRepo.findAllByStatus(status.name());
    }

    @Override
    public void delete(Long id) {
        Optional<IndividualEntity> individualEntity = individualEntityRepo.findById(id);
        try {
            if (individualEntity.isPresent()) {
                IndividualEntity entity = individualEntity.get();
                entity.setStatus(EntityStatus.DELETED);
                individualEntityRepo.save(entity);

                logFacade.write(
                        String.format("Успешное удаление сущности '%s %s %s'",
                                entity.getLastname(), entity.getFirstname(), entity.getPatronymic()
                        ),
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
    public IndividualEntityDto update(IndividualEntityDto dto) {
        Optional<IndividualEntity> optionalEntity = individualEntityRepo.findById(dto.getId());
        try {
            if (optionalEntity.isPresent()) {
                IndividualEntity entity = optionalEntity.get();

                entity.setFirstname(dto.getFirstname());
                entity.setLastname(dto.getLastname());
                entity.setPatronymic(dto.getPatronymic());

                IndividualEntity updated = individualEntityRepo.save(entity);

                logFacade.write(
                        String.format("Обновление физ.лица с ФИО '%s %s %s'",
                                dto.getLastname(), dto.getFirstname(), dto.getPatronymic()
                        ),
                        LogLevel.INFO,
                        SecurityUtil.getLoggedUser()
                );

                return IndividualEntityMapper.toDto(updated);
            } else {
                throw new EntityNotFoundException(
                        String.format("Ошибка обновления. Сущность с id '%s' и ФИО '%s %s %s' не найдена",
                                dto.getId(), dto.getLastname(), dto.getFirstname(), dto.getPatronymic()
                        )
                );
            }
        } catch (EntityNotFoundException e) {
            logFacade.write(e.getMessage(), LogLevel.ERROR, SecurityUtil.getLoggedUser());
        }

        return dto;
    }
}
