package com.thanhtam.ecommerce.identity.features.auth.login;

import com.thanhtam.ecommerce.identity.common.result.Error;
import com.thanhtam.ecommerce.identity.common.result.Result;
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
    private Object tokenGeneration;

    public Result<Login.Response> handler(String clientName, String password) {
        // kiểm tra tài khoản
        if(!clientRepository.existsByClientName(clientName)){
            return Result.failure(Error.notFound("User.NotFound","Client not found"));
        }
        Client client = clientRepository.findByClientName(clientName);

        if(!passwordEncoder.matches(client.getPasswordHash(), password)){
            return Result.failure(Error.conflict("User.PasswordMismatch","Password does not match"));
        }
        String token = tokenGeneration.generate(Client);

        return Result.success(Login.Response.builder()
                        .name(clientName)
                        .jwtToken(token)
                .build());
    }
}
