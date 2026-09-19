package com.thanhtam.ecommerce.identity.common.security.jwt;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtUtil {
    // Khai báo biến final và inject qua constructor
    private final JwtProperties jwtProperties;
    private final JWSVerifier verifier;
    private final JWSSigner signer;


    public String generateAccessToken(String userId, String role) throws JOSEException {
        // Sử dụng thuật toán RS256
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256)
                .type(JOSEObjectType.JWT)
                .keyID("key-id-01") // Hữu ích khi hệ thống có nhiều key (Key Rotation)
                .build();

        Date now = new Date();
        Date expiration = new Date(now.getTime() + (jwtProperties.getAccessTokenExpiration() * 1000));

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .jwtID(UUID.randomUUID().toString()) // jti: Định danh duy nhất để phục vụ Blacklist
                .subject(userId)                     // sub: ID của User
                .issuer(jwtProperties.getIssuer())   // iss: Đơn vị cấp phát
                .issueTime(now)                      // iat: Thời điểm cấp
                .expirationTime(expiration)          // exp: Thời điểm hết hạn
                .claim("role", role)                 // Custom claim
                .build();

        SignedJWT signedJWT = new SignedJWT(header, claimsSet);
        signedJWT.sign(signer); // Tốc độ xử lý cực nhanh vì Signer đã được khởi tạo sẵn

        return signedJWT.serialize();
    }

    public SignedJWT verifyAndParseToken(String token) throws ParseException, JOSEException {
        SignedJWT signedJWT = SignedJWT.parse(token);

        // Kiểm tra chữ ký (bằng Public Key bên trong Verifier)
        if (!signedJWT.verify(verifier)) {
            throw new JOSEException("Invalid JWT signature");
        }

        // Kiểm tra hết hạn
        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        if (expirationTime != null && new Date().after(expirationTime)) {
            throw new JOSEException("JWT is expired");
        }

        // Trả về object thay vì boolean để Filter dễ dàng trích xuất thông tin
        return signedJWT;
    }

}
