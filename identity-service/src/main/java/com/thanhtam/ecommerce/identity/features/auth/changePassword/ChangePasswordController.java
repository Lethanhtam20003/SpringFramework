package com.thanhtam.ecommerce.identity.features.auth.changePassword;

import com.thanhtam.ecommerce.identity.common.api.ApiResponse;
import com.thanhtam.ecommerce.identity.common.api.BaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("change-password")
@RequiredArgsConstructor
public class ChangePasswordController extends BaseController {
    private final changePasswordCommandHandler handler;
    @PostMapping
    public ResponseEntity<ApiResponse<ChangePassword.response>> changePassword(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ChangePassword.command request) {
        String clientId = userDetails.getUsername();
        var res = handler.handler(clientId ,request.passwordOld(), request.passwordNew());
        return handleResult(res);
    }
}
