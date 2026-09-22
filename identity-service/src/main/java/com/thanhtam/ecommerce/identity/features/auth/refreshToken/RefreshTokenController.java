package com.thanhtam.ecommerce.identity.features.auth.refreshToken;

import com.thanhtam.ecommerce.identity.common.api.ApiResponse;
import com.thanhtam.ecommerce.identity.common.api.BaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("refresh-token")
@RequiredArgsConstructor
public class RefreshTokenController extends BaseController {
    @PostMapping
    public ResponseEntity<ApiResponse<RefreshToken.Response>> refreshToken(){

        return null;
    }
}
