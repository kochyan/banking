package ru.kochyan.banking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.kochyan.banking.dtos.UserCredentialsDto;
import ru.kochyan.banking.utils.AuthenticationUtil;
import ru.kochyan.banking.utils.responsebuilders.AuthenticationResponseBuilder;


@RestController
@RequestMapping("${api.prefix}/auth")
@CrossOrigin(origins = {"${origin.localhost}"},
        allowedHeaders = "*",
        allowCredentials = "true",
        methods = {RequestMethod.POST, RequestMethod.OPTIONS},
        maxAge = 3600)
public class AuthenticationController {

    private final AuthenticationUtil authenticationUtil;

    @Autowired
    public AuthenticationController(AuthenticationUtil authenticationUtil) {
        this.authenticationUtil = authenticationUtil;
    }


    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody UserCredentialsDto dto) {
        Authentication authentication = authenticationUtil.authenticate(dto);

        return ResponseEntity.ok().body(AuthenticationResponseBuilder.buildUserAuthority(authentication));
    }
}