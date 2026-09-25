package com.thanhtam.ecommerce.identity.features.auth.login;

import com.nimbusds.jose.JOSEException;
import com.thanhtam.ecommerce.identity.common.api.ApiResponse;
import com.thanhtam.ecommerce.identity.common.api.BaseController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class LoginController extends BaseController {
    private final LoginCommandHandler handler;
    @PostMapping("login")
    public ResponseEntity<ApiResponse<Login.Response>> login(@Valid @RequestBody Login.Command request) throws JOSEException {
        var res = handler.handle(request.clientName(),request.password());
        return handleResult(res);
    }
}
