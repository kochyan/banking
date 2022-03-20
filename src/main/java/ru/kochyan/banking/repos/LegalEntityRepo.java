package ru.kochyan.banking.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kochyan.banking.entities.LegalEntity;

import java.util.List;

@Repository
public interface LegalEntityRepo extends AbstractRepo<LegalEntity> {

    //@EntityGraph(value = "legalEntity.eager", type = EntityGraph.EntityGraphType.LOAD)
    @Query(value = "SELECT * FROM find_legal_entity_by_status(:status)", nativeQuery = true)
    List<LegalEntity> findAllByStatus(String status);
}
