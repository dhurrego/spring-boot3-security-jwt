package com.deivurca.model.dto;

import jakarta.validation.constraints.NotEmpty;

public record AuthenticationRequest(
        @NotEmpty
        String email,

        @NotEmpty
        String password
) {
}
