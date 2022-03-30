package ru.kochyan.banking.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kochyan.banking.entities.Payment;
import ru.kochyan.banking.repos.PaymentRepo;
import ru.kochyan.banking.services.PaymentService;

import java.util.List;

@Service
public class PaymentServiceImpl extends AbstractServiceImpl<Payment> implements PaymentService {

    private final PaymentRepo paymentRepo;

    @Autowired
    public PaymentServiceImpl(PaymentRepo paymentRepo) {
        super(paymentRepo);
        this.paymentRepo = paymentRepo;
    }

    @Override
    public List<Payment> findAllByLegalId(Long id) {
        return paymentRepo.findAllByLegalId(id);
    }

    @Override
    public List<Payment> findByIndividualId(Long id) {
        return paymentRepo.findAllByIndividualId(id);
    }

    @Override
    public List<Payment> findAll() {
        return paymentRepo.findAllPayments();
    }
}
