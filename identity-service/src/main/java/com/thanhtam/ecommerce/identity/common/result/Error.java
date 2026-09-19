package com.thanhtam.ecommerce.identity.common.result;

public record Error(String code, String message, ErrorType errorType) {

    public static final Error NONE = new Error("","",ErrorType.None);
    public static Error failure(String code, String message) {
        return new Error(code, message, ErrorType.Failure);
    }
    public static Error notFound(String code, String message) {
        return new Error(code, message, ErrorType.NotFound);
    }
    public static Error validation(String code, String message) {
        return new Error(code, message, ErrorType.Validation);
    }
    public static Error conflict(String code, String message) {
        return new Error(code, message, ErrorType.Conflict);
    }
    public static Error unauthorized(String code, String message) {
        return new Error(code, message, ErrorType.Unauthorized);
    }
    public static Error forbidden(String code, String message) {
        return new Error(code, message, ErrorType.Forbidden);
    }
}
