package ru.kochyan.banking.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kochyan.banking.entities.Payment;

import java.util.List;

@Repository
public interface PaymentRepo extends AbstractRepo<Payment> {

    @Query(value = "SELECT * FROM find_all_payments_by_legal(:legalId)", nativeQuery = true)
    List<Payment> findAllByLegalId(Long legalId);

    @Query(value = "SELECT * FROM find_all_payments_by_individual(:individualId)", nativeQuery = true)
    List<Payment> findAllByIndividualId(Long individualId);

    @Query(value = "SELECT * FROM find_all_payments()", nativeQuery = true)
    List<Payment> findAllPayments();
}
