package ru.kochyan.banking.repos;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;
import ru.kochyan.banking.entities.LegalEntity;

import java.util.List;

@Repository
public interface LegalEntityRepo extends AbstractRepo<LegalEntity> {

    @Override
    @EntityGraph(value = "legalEntity.eager", type = EntityGraph.EntityGraphType.LOAD)
    List<LegalEntity> findAll();
}
