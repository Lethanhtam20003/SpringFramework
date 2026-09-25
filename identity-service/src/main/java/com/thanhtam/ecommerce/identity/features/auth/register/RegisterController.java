package com.thanhtam.ecommerce.identity.features.auth.register;

import com.thanhtam.ecommerce.identity.common.api.ApiResponse;
import com.thanhtam.ecommerce.identity.common.api.BaseController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class RegisterController extends BaseController {
    private final RegisterCommandHandler handler;
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Register.Response>> register(@Valid @RequestBody Register.Command registerRequest) {
        var result = handler.handle(registerRequest);
        return handleResult(result);
    }
}
