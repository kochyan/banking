package ru.kochyan.banking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.kochyan.banking.enums.EntityStatus;
import ru.kochyan.banking.services.IndividualEntityService;

@RestController
@RequestMapping("${api.prefix}/individual-entity")
@CrossOrigin(origins = {"${origin.localhost}"},
        allowedHeaders = "*",
        allowCredentials = "true",
        methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT},
        maxAge = 3600)
public class IndividualEntityController {

    private final IndividualEntityService individualEntityService;

    @Autowired
    public IndividualEntityController(IndividualEntityService individualEntityService) {
        this.individualEntityService = individualEntityService;
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(individualEntityService.findAllByStatus(EntityStatus.ACTIVE));
    }
}
