package com.thanhtam.ecommerce.identity.features.auth.changePassword;

import com.thanhtam.ecommerce.identity.common.api.ApiResponse;
import com.thanhtam.ecommerce.identity.common.api.BaseController;
import com.thanhtam.ecommerce.identity.common.security.UserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class ChangePasswordController extends BaseController {
    private final changePasswordCommandHandler handler;
    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse<ChangePassword.Response>> changePassword(
            @AuthenticationPrincipal UserPrincipal userDetails,
            @Valid @RequestBody ChangePassword.Command request) {
        String clientId = userDetails.getUsername();
        var res = handler.handle(UUID.fromString(clientId),request.passwordOld(), request.passwordNew());
        return handleResult(res);
    }
}
