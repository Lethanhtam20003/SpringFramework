package com.thanhtam.ecommerce.identity.common.security.jwt;

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
        String token = extractTokenFromRequest(request);

        if (StringUtils.hasText(token)) {
            try {
                SignedJWT signedJWT = jwtUtil.verifyAndParseToken(token);
                JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

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
}
