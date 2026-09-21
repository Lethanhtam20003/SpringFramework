package com.thanhtam.ecommerce.identity.features.auth.changePassword;

import com.thanhtam.ecommerce.identity.common.result.Error;
import com.thanhtam.ecommerce.identity.common.result.Result;
import com.thanhtam.ecommerce.identity.domain.entities.Client;
import com.thanhtam.ecommerce.identity.infrastructure.persistence.IClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class changePasswordCommandHandler {
    private final IClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    public Result<ChangePassword.response> handler(String clientId,String oldPassword,String newPassword) {
        // kiểm user tồn tại
        if(!clientRepository.existsById(UUID.fromString(clientId))){
            return Result.failure(Error.notFound("Changepassword.userNotFound","token hes a problem"));
        }
        // kiểm tra mật khẩu cũ đúng
        if(oldPassword.equals(newPassword)){
            return Result.failure(Error.validation("Changepassword.passwordError","oldPassword and newPassword are equal"));
        }
        Client client = clientRepository.findById(UUID.fromString(clientId)).get();
        if(!passwordEncoder.matches(oldPassword, client.getPasswordHash())){
            return Result.failure(Error.validation("Changepassword.passwordError","oldPassword and password of user are not equal"));
        }
        client.setPasswordHash(passwordEncoder.encode(newPassword));
        return Result.success(ChangePassword.response.builder().message("change password is successful").build());
    }
}
