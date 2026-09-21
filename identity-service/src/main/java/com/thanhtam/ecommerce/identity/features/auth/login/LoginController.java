package com.thanhtam.ecommerce.identity.features.auth.login;

import com.nimbusds.jose.JOSEException;
import com.thanhtam.ecommerce.identity.common.api.ApiResponse;
import com.thanhtam.ecommerce.identity.common.api.BaseController;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@NoArgsConstructor
public class LoginController extends BaseController {
    private LoginCommandHandler handler;
    @PostMapping
    public ResponseEntity<ApiResponse<Login.Response>> login(@RequestBody Login.Command request) throws JOSEException {
        var res = handler.handler(request.clientName(),request.password());
        return handleResult(res);
    }
}
