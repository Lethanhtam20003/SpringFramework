package com.thanhtam.ecommerce.identity.common.api;

import java.time.Instant;

public record ApiResponse<T>(
        boolean isSuccess,
        T data,
        ApiError error,
        Instant timestamp
) {
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, null, Instant.now());
    }

    public static <T> ApiResponse<T> failure(String code, String message) {
        return new ApiResponse<>(false, null, new ApiError(code, message), Instant.now());
    }


}
