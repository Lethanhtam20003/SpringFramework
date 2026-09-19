package com.thanhtam.ecommerce.identity.features.auth.register;

import com.thanhtam.ecommerce.identity.common.api.ApiResponse;
import com.thanhtam.ecommerce.identity.common.api.BaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/vi/auth")
@RequiredArgsConstructor
public class RegisterController extends BaseController {
    private final RegisterCommandHandler handler;
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Register.response>> register(@RequestBody Register.command registerRequest) {
        var result = handler.handler(registerRequest);
        return HandleResult(result);
    }
}
