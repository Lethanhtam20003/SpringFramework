package com.thanhtam.ecommerce.identity.features.auth.login;

import com.nimbusds.jose.JOSEException;
import com.thanhtam.ecommerce.identity.common.result.Error;
import com.thanhtam.ecommerce.identity.common.result.Result;
import com.thanhtam.ecommerce.identity.common.security.jwt.JwtUtil;
import com.thanhtam.ecommerce.identity.domain.entities.Client;
import com.thanhtam.ecommerce.identity.domain.enums.AccountStatus;
import com.thanhtam.ecommerce.identity.infrastructure.persistence.IClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginCommandHandler {
    private final IClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil tokenGeneration;

    public Result<Login.Response> handler(String clientName, String password) throws JOSEException {
        // lấy client
        Optional<Client> clientOpt = clientRepository.findByClientName(clientName);
        if (clientOpt.isEmpty() || !passwordEncoder.matches(password, clientOpt.get().getPasswordHash())) {
            return Result.failure(Error.unauthorized("Auth.InvalidCredentials", "Invalid username or password"));
        }
        Client client = clientOpt.get();
        if (client.getAccountStatus() != AccountStatus.ACTIVE && client.getAccountStatus() != AccountStatus.PENDING_VERIFICATION) {
            return Result.failure(Error.forbidden("Auth.AccountLocked", "Account is locked or inactive"));
        }

        // tạo jwt token
        try {
            String accessToken = tokenGeneration.generateAccessToken(client.getId().toString(), client.getRoles().name());
            String refreshToken = tokenGeneration.generateRefreshToken(client.getId().toString());

            return Result.success(Login.Response.builder()
                    .name(client.getClientName())
                    .jwtToken(accessToken)
                    .jwtRefreshToken(refreshToken) // camelCase
                    .build());
        } catch (JOSEException e) {
            return Result.failure(Error.failure("Auth.TokenGenerationError", "Could not generate authentication tokens"));
        }
    }
}
