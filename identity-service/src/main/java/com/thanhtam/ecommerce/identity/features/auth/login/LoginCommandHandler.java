package com.thanhtam.ecommerce.identity.features.auth.login;

import com.nimbusds.jose.JOSEException;
import com.thanhtam.ecommerce.identity.common.result.Error;
import com.thanhtam.ecommerce.identity.common.result.Result;
import com.thanhtam.ecommerce.identity.common.security.jwt.JwtUtil;
import com.thanhtam.ecommerce.identity.domain.entities.Client;
import com.thanhtam.ecommerce.identity.infrastructure.persistence.IClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginCommandHandler {
    private IClientRepository clientRepository;
    private PasswordEncoder passwordEncoder;
    private JwtUtil tokenGeneration;

    public Result<Login.Response> handler(String clientName, String password) throws JOSEException {
        // kiểm tra tài khoản
        if(!clientRepository.existsByClientName(clientName)){
            return Result.failure(Error.unauthorized("Login.UserNotFound","Client not found"));
        }

        Client client = clientRepository.findByClientName(clientName);

        if(!passwordEncoder.matches(client.getPasswordHash(), password)){
            return Result.failure(Error.unauthorized("Login.PasswordMismatch","Password does not match"));
        }

        String accessToken = tokenGeneration.generateAccessToken(client.getId().toString(),client.getRoles().name());
        String refreshToken = tokenGeneration.generateRefreshToken(client.getId().toString());

        return Result.success(Login.Response.builder()
                        .name(clientName)
                        .jwtToken(accessToken)
                        .JwtRefreshToken(refreshToken)
                .build());
    }
}
