package com.thanhtam.ecommerce.identity.common.security.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.thanhtam.ecommerce.identity.common.security.UserPrincipal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        // NẾU KHÔNG CÓ TOKEN: Bỏ qua việc xác thực và cho phép request đi tiếp tới filter tiếp theo.
        // Các API permitAll() sẽ được truy cập, các API authenticated() sẽ bị chặn ở bước sau.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = extractTokenFromRequest(request);

        if (StringUtils.hasText(token)) {
            try {
                SignedJWT signedJWT = jwtUtil.verifyAndParseToken(token);
                JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
                // Kiểm tra token phải là ACCESS token
                String tokenType = claims.getStringClaim(JwtUtil.TOKEN_TYPE_CLAIM);
                if (!JwtUtil.ACCESS_TOKEN_TYPE.equals(tokenType)) {
                    throw new JOSEException("Invalid token type for authentication");
                }
                UUID userId = UUID.fromString(claims.getSubject());
                String role = claims.getStringClaim("role");

                UserPrincipal principal = UserPrincipal.builder()
                        .id(userId)
                        .role(role)
                        .build();

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        principal,
                        null,
                        principal.getAuthorities()
                );

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception ex) {
                log.warn("Invalid JWT token: {}", ex.getMessage());
                // Không ném exception ở đây để SecurityFilterChain xử lý 401 tự động qua AuthenticationEntryPoint
            }
        }

        filterChain.doFilter(request, response);
    }
    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

//    private static final String[] PUBLIC_API = {
//            "/api/v1/auth/register",
//            "/api/v1/auth/login",
//            "/api/v1/auth/refresh-token"
//    };
//
//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        String path = request.getServletPath();
//        AntPathMatcher pathMatcher = new AntPathMatcher();
//
//        for (String excludePath : PUBLIC_API) {
//            if (pathMatcher.match(excludePath, path)) {
//                return true; // Trả về true -> Không chạy Filter này
//            }
//        }
//        return false;
//    }
}
