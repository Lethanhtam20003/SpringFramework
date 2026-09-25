package com.thanhtam.ecommerce.identity.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SwaggerOpener {
    // Lấy port từ file cấu hình (nếu không có sẽ mặc định là 8080)
    @Value("${server.port:8080}")
    private String port;

    @EventListener(ApplicationReadyEvent.class)
    public void openSwaggerUI() {
        // Đường dẫn mặc định của Swagger UI trong Springdoc OpenAPI
        String url = "http://localhost:" + port + "/swagger-ui/index.html";
        String os = System.getProperty("os.name").toLowerCase();
        Runtime rt = Runtime.getRuntime();

        try {
            if (os.contains("win")) {
                // Lệnh mở trình duyệt mặc định trên Windows
                rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } else if (os.contains("mac")) {
                // Lệnh mở trình duyệt trên macOS
                rt.exec("open " + url);
            } else if (os.contains("nix") || os.contains("nux")) {
                // Lệnh mở trình duyệt trên Linux
                rt.exec("xdg-open " + url);
            }
        } catch (Exception e) {
            System.err.println("Không thể tự động mở trình duyệt: " + e.getMessage());
        }
    }
}
