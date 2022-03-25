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
import ru.kochyan.banking.dtos.IndividualEntityDto;
import ru.kochyan.banking.enums.EntityStatus;
import ru.kochyan.banking.services.IndividualEntityService;
import ru.kochyan.banking.utils.mappers.IndividualEntityMapper;

import java.util.stream.Collectors;

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
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(
                individualEntityService.findAllByStatus(EntityStatus.ACTIVE).stream()
                        .map(IndividualEntityMapper::toDto)
                        .collect(Collectors.toList())
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        individualEntityService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody IndividualEntityDto dto) {
        return ResponseEntity.ok(individualEntityService.update(dto));
    }
}
