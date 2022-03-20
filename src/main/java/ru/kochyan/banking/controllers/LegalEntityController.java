package ru.kochyan.banking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.kochyan.banking.dtos.EagerLegalEntityDto;
import ru.kochyan.banking.enums.Status;
import ru.kochyan.banking.services.LegalEntityService;

@RestController
@RequestMapping("${api.prefix}/legal-entity")
@CrossOrigin(origins = {"${origin.localhost}"},
        allowedHeaders = "*",
        allowCredentials = "true",
        methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT},
        maxAge = 3600)
public class LegalEntityController {
    private final LegalEntityService legalEntityService;

    @Autowired
    public LegalEntityController(LegalEntityService legalEntityService) {
        this.legalEntityService = legalEntityService;
    }


    @PutMapping
    public ResponseEntity<?> update(@RequestBody EagerLegalEntityDto dto) {
        return ResponseEntity.ok(legalEntityService.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        legalEntityService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/eager")
    public ResponseEntity<?> eagerFindAllActive() {
        return ResponseEntity.ok(legalEntityService.eagerFindAllByStatus(Status.ACTIVE));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(legalEntityService.find(id));
    }
}