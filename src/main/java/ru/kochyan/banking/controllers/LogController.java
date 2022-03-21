package ru.kochyan.banking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.kochyan.banking.services.LogService;
import ru.kochyan.banking.utils.mappers.LogMapper;

import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/log")
@CrossOrigin(origins = {"${origin.localhost}"},
        allowedHeaders = "*",
        allowCredentials = "true",
        methods = {RequestMethod.GET},
        maxAge = 3600)
public class LogController {

    private final LogService logService;

    @Autowired
    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(
                logService.findAll().stream()
                        .map(LogMapper::toDto)
                        .collect(Collectors.toList())
        );
    }
}