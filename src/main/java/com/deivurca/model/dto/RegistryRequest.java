package com.deivurca.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record RegistryRequest(
        @NotEmpty
//        @Pattern(regexp = "[A-Za-zÁÉÍÓÚáéíóúÑñ\s]*$")
        String nombre,

        @NotEmpty
        @Email
        String email,

        @NotEmpty
        String password
) {
}
