package com.thanhtam.ecommerce.identity.features.auth.changePassword;

import com.thanhtam.ecommerce.identity.common.result.Error;
import com.thanhtam.ecommerce.identity.common.result.Result;
import com.thanhtam.ecommerce.identity.domain.entities.Client;
import com.thanhtam.ecommerce.identity.infrastructure.persistence.IClientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class changePasswordCommandHandler {
    private final IClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public Result<ChangePassword.Response> handle(UUID clientId, String oldPassword, String newPassword) {
        if (oldPassword.equals(newPassword)) {
            return Result.failure(Error.validation("ChangePassword.SamePassword", "New password cannot be the same as old password"));
        }

        Client client = clientRepository.findById(clientId)
                .orElse(null);
        if (client == null) {
            return Result.failure(Error.notFound("ChangePassword.UserNotFound", "User not found"));
        }

        if (!passwordEncoder.matches(oldPassword, client.getPasswordHash())) {
            return Result.failure(Error.validation("ChangePassword.InvalidOldPassword", "Current password does not match"));
        }

        client.setPasswordHash(passwordEncoder.encode(newPassword));
        client.setPasswordChangeAt(Instant.now());
        clientRepository.save(client);

        return Result.success(ChangePassword.Response.builder()
                .message("Password changed successfully")
                .build());
    }
}
