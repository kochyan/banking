package ru.kochyan.banking.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kochyan.banking.entities.IndividualEntity;
import ru.kochyan.banking.enums.EntityStatus;
import ru.kochyan.banking.repos.IndividualEntityRepo;
import ru.kochyan.banking.services.IndividualEntityService;

import java.util.List;

@Service
public class IndividualEntityServiceImpl extends AbstractServiceImpl<IndividualEntity> implements IndividualEntityService {
    private final IndividualEntityRepo individualEntityRepo;

    @Autowired
    public IndividualEntityServiceImpl(IndividualEntityRepo individualEntityRepo) {
        super(individualEntityRepo);
        this.individualEntityRepo = individualEntityRepo;
    }

    @Override
    public List<IndividualEntity> findAllByStatus(EntityStatus status) {
        return individualEntityRepo.findAllByStatus(status.name());
    }
}
