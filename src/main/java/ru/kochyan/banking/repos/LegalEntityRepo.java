package ru.kochyan.banking.repos;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;
import ru.kochyan.banking.entities.LegalEntity;
import ru.kochyan.banking.enums.Status;

import java.util.List;

@Repository
public interface LegalEntityRepo extends AbstractRepo<LegalEntity> {

    @EntityGraph(value = "legalEntity.eager", type = EntityGraph.EntityGraphType.LOAD)
    List<LegalEntity> findAllByStatus(Status status);
}
