package com.thanhtam.ecommerce.identity.common.security.jwt;

import ch.qos.logback.core.net.SMTPAppenderBase;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {
    private Keystore keystore = new Keystore();
    private long accessTokenExpiration; // thời gian sống của access token
    private long refreshTokenExpiration; // thời gin sống của refresh token

    private String issuer; // địa chỉ phát hành token (iss)
    private String audience; // ứng dụng được phép dùng



//    private String header; // tên heahder chứa token
//    private String prefix; // tiên tố của token

    @Data
    public static class Keystore {
        private String path;
        private String password;
        private String alias;
    }
}
