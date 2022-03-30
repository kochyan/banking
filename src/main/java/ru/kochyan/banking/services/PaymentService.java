package ru.kochyan.banking.services;

import ru.kochyan.banking.entities.Payment;

import java.util.List;

public interface PaymentService extends AbstractService<Payment>{
    List<Payment> findAllByLegalId(Long id);

    List<Payment> findByIndividualId(Long id);
}
