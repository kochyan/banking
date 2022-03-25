package ru.kochyan.banking.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kochyan.banking.entities.IndividualEntity;

import java.util.List;

@Repository
public interface IndividualEntityRepo extends AbstractRepo<IndividualEntity> {

    @Query(value = "SELECT * FROM find_individual_entity_by_status(:status)", nativeQuery = true)
    List<IndividualEntity> findAllByStatus(String status);
}
