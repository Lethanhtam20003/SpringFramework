package com.thanhtam.ecommerce.identity.features.auth.login;

import com.nimbusds.jose.JOSEException;
import com.thanhtam.ecommerce.identity.common.api.ApiResponse;
import com.thanhtam.ecommerce.identity.common.api.BaseController;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/login")
@RequiredArgsConstructor
public class LoginController extends BaseController {
    private final LoginCommandHandler handler;
    @PostMapping
    public ResponseEntity<ApiResponse<Login.Response>> login(@Valid @RequestBody Login.Command request) throws JOSEException {
        var res = handler.handler(request.clientName(),request.password());
        return handleResult(res);
    }
}
