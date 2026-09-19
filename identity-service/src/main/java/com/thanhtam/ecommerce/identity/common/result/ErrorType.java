package com.thanhtam.ecommerce.identity.common.result;

public enum ErrorType  {
    None,
    Failure,        // Lỗi logic chung (400)
    Validation,     // Lỗi dữ liệu đầu vào (400)
    Unauthorized,   // Lỗi xác thực (401)
    Forbidden,      // Lỗi phân quyền (403)
    NotFound,       // Không tìm thấy tài nguyên (404)
    Conflict,       // Xung đột dữ liệu (409)
    BadRequest
}
