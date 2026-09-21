package com.thanhtam.ecommerce.identity.common.api;

import com.thanhtam.ecommerce.identity.common.result.Error;
import com.thanhtam.ecommerce.identity.common.result.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public abstract class BaseController {

    protected <T> ResponseEntity<ApiResponse<T>> handleResult(Result<T> result){
        if(result.isSuccess()){
            return ResponseEntity.ok(ApiResponse.success(result.getValue()));
        }
        return mapErrorToActionResult(result.getError());
    }

    private <T> ResponseEntity<ApiResponse<T>> mapErrorToActionResult(Error error) {
        ApiResponse<T> response = ApiResponse.failure(error.code(),  error.message());

        HttpStatus status = switch (error.errorType()){
            case Failure, Validation, BadRequest -> HttpStatus.BAD_REQUEST;
            case Unauthorized -> HttpStatus.UNAUTHORIZED;
            case Forbidden -> HttpStatus.FORBIDDEN;
            case NotFound -> HttpStatus.NOT_FOUND;
            case Conflict -> HttpStatus.CONFLICT;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
        return ResponseEntity.status(status).body(response);
    }
//    protected UUID getAuthenticatedUserId() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return null;
//        }
//
//        Object principal = authentication.getName(); // Hoặc trích xuất từ CustomUserDetails
//        try {
//            return UUID.fromString(principal.toString());
//        } catch (IllegalArgumentException | NullPointerException ex) {
//            return null;
//        }
//    }
}
