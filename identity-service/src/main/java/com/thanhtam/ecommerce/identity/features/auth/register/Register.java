package com.thanhtam.ecommerce.identity.features.auth.register;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

public class Register {
    public record RegisterCommand(
        @NotBlank String clientName,
        @NotBlank @Size(min = 16)String password,
        @NotBlank @Email String email
    ){}

    @Builder
    public record Response(
            String email,
            String message
    ){}
}
