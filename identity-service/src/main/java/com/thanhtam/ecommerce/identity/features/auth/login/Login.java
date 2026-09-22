package com.thanhtam.ecommerce.identity.features.auth.login;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Login {
   public record Command(
           @NotBlank String clientName,
           @NotBlank @Size(min = 16)String password
   ){}

@Builder
    public record Response(
            String name,
            String jwtToken,
            String jwtRefreshToken
    ){}
}
