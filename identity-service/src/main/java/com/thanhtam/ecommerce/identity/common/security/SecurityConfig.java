package com.thanhtam.ecommerce.identity.common.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Tham số 12 là độ khó (strength/work factor).
        // Số càng lớn hash càng chậm (chống Brute-force tốt hơn) nhưng sẽ tốn CPU.
        // 10 hoặc 12 là mức lý tưởng cho ứng dụng thông thường.
        return new BCryptPasswordEncoder(12);
    }
}
