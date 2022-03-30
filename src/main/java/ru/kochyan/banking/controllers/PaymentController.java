package ru.kochyan.banking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.kochyan.banking.entities.Payment;
import ru.kochyan.banking.services.PaymentService;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/log")
@CrossOrigin(origins = {"${origin.localhost}"},
        allowedHeaders = "*",
        allowCredentials = "true",
        methods = {RequestMethod.GET},
        maxAge = 3600)
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    @GetMapping
    public ResponseEntity<List<Payment>> findAll() {
        return ResponseEntity.ok(paymentService.findAll());
    }
}
