package com.deivurca.controller;

import com.deivurca.model.dto.AuthenticationRequest;
import com.deivurca.model.dto.AuthenticationResponse;
import com.deivurca.model.dto.RegistryRequest;
import com.deivurca.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegistryRequest registryRequest) {
        return ResponseEntity.ok(service.register(registryRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody AuthenticationRequest registryRequest) {
        return ResponseEntity.ok(service.login(registryRequest));
    }
}
