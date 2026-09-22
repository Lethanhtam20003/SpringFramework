package com.thanhtam.ecommerce.identity.features.auth.refreshToken;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

public class RefreshToken {
    public record Command(
            @NotBlank String refreshToken
    ){}

    @Builder
    public record Response(
            String accessToken,
            String refreshToken
    ){}
}
