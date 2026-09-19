package com.thanhtam.ecommerce.identity.common.security.jwt;

import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
public class NimbusJwtConfig {
    private final JwtProperties jwtProperties;
    private final ResourceLoader resourceLoader;

    public NimbusJwtConfig(JwtProperties jwtProperties, ResourceLoader resourceLoader) {
        this.jwtProperties = jwtProperties;
        this.resourceLoader = resourceLoader;
    }

    /**
     * Khởi tạo Bean KeyStore chứa cặp khóa RSA dùng để ký và xác thực JWT.
     *
     * @return Đối tượng KeyStore đã nạp dữ liệu và mở khóa bằng mật khẩu.
     * @throws Exception Nếu không tìm thấy file, sai mật khẩu hoặc lỗi giải mã.
     */
    @Bean
    public KeyStore keyStore() throws Exception {
        KeyStore keyStore = KeyStore.getInstance("JKS");
        try (InputStream is = resourceLoader.getResource(jwtProperties.getKeystore().getPath()).getInputStream()) {
            keyStore.load(is, jwtProperties.getKeystore().getPassword().toCharArray());
        }
        return keyStore;
    }

    @Bean
    public JWSSigner jwsSigner(KeyStore keyStore) throws Exception {
        String password = jwtProperties.getKeystore().getPassword();
        String alias = jwtProperties.getKeystore().getAlias();

        RSAPrivateKey privateKey = (RSAPrivateKey) keyStore.getKey(alias, password.toCharArray());
        return new RSASSASigner(privateKey);
    }

    @Bean
    public JWSVerifier jwsVerifier(KeyStore keyStore) throws Exception {
        String alias = jwtProperties.getKeystore().getAlias();

        // Trích xuất Public Key
        RSAPublicKey publicKey = (RSAPublicKey) keyStore.getCertificate(alias).getPublicKey();
        return new RSASSAVerifier(publicKey);
    }
}
