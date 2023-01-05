package com.deivurca.service.impl;

import com.deivurca.config.security.JwtService;
import com.deivurca.config.security.UserDetailsImpl;
import com.deivurca.exception.BusinessException;
import com.deivurca.model.dto.AuthenticationRequest;
import com.deivurca.model.dto.AuthenticationResponse;
import com.deivurca.model.dto.RegistryRequest;
import com.deivurca.model.entity.Usuario;
import com.deivurca.model.enums.Role;
import com.deivurca.repository.UsuarioRepository;
import com.deivurca.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @Override
    public AuthenticationResponse register(RegistryRequest registryRequest) {
        repository.findOneByEmail(registryRequest.email())
                .ifPresent(usuario -> {
                    throw new BusinessException("Ya existe un usuario registrado con el mismo email");
                });

        Usuario usuario = Usuario.builder()
                .nombre(registryRequest.nombre())
                .email(registryRequest.email())
                .password(passwordEncoder.encode(registryRequest.password()))
                .role(Role.USER)
                .build();

        final UserDetails userDetails = new UserDetailsImpl(repository.save(usuario));
        return new AuthenticationResponse(jwtService.generateToken(userDetails));
    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.email(),
                        authenticationRequest.password()
                )
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.email());

        return new AuthenticationResponse(jwtService.generateToken(userDetails));
    }
}
