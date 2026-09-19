package com.thanhtam.ecommerce.identity.features.auth.login;

import com.thanhtam.ecommerce.identity.common.api.ApiResponse;
import com.thanhtam.ecommerce.identity.common.api.BaseController;
import com.thanhtam.ecommerce.identity.infrastructure.persistence.IClientRepository;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@NoArgsConstructor
public class LoginController extends BaseController {
    private LoginCommandHandler handler;

    public ResponseEntity<ApiResponse<Login.Response>> login(@RequestBody Login.Command request){
        var res = handler.handler(request.clientName(),request.password());
        return HandleResult(res);
    }
}
