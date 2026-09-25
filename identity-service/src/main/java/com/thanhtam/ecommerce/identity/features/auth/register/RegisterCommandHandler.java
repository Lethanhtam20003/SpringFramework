package com.thanhtam.ecommerce.identity.features.auth.register;

import com.thanhtam.ecommerce.identity.common.result.Error;
import com.thanhtam.ecommerce.identity.common.result.Result;
import com.thanhtam.ecommerce.identity.domain.entities.Client;
import com.thanhtam.ecommerce.identity.infrastructure.persistence.IClientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterCommandHandler {
    private final IClientRepository userRepository;
    private final PasswordEncoder passwordHasher;

    @Transactional
    public Result<Register.Response> handle(Register.Command registerRequest) {
        // 1. Kiểm tra tồn tại qua IUserRepository
        if(userRepository.existsByEmail(registerRequest.email())){
            return Result.failure(Error.conflict("USER.DuplicateEmail", registerRequest.email()));
        }
        // kiểm tra client name
        if(userRepository.existsByClientName(registerRequest.clientName())) {
            return Result.failure(Error.conflict("USER.DuplicateName", registerRequest.clientName()));
        }
        // 2. Hash mật khẩu qua IPasswordHasher
        String passwordHash = passwordHasher.encode(registerRequest.password());
        // 3. Khởi tạo Domain Entity User
        Client user = Client.create(registerRequest.clientName(),passwordHash,registerRequest.email());
        // 4. Gọi userRepository.save(user)
        userRepository.save(user);
        // 5. Map sang RegisterResponse
        return Result.success(Register.Response.builder()
                .email(user.getEmail())
                .message("Registration successful")
                .build());
    }

}
