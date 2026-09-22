package com.thanhtam.ecommerce.identity.features.auth.changePassword;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

public class ChangePassword {
    public record Command(

            @NotBlank @Size(min = 8,max = 32) String passwordOld,
            @NotBlank @Size(min = 8,max = 32) String passwordNew
    ){}

    @Builder
    public record Response(
            String message
    ){}

}
