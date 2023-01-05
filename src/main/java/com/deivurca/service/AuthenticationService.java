package com.deivurca.service;

import com.deivurca.model.dto.AuthenticationRequest;
import com.deivurca.model.dto.AuthenticationResponse;
import com.deivurca.model.dto.RegistryRequest;

public interface AuthenticationService {
    AuthenticationResponse register(RegistryRequest registryRequest);
    AuthenticationResponse login(AuthenticationRequest authenticationRequest);
}
