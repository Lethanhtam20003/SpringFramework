package com.thanhtam.ecommerce.identity.common.result;

import lombok.Getter;

import java.util.Objects;

public class Result<T> {
    @Getter
    private final boolean isSuccess;
    @Getter
    private final Error error;
    private final T value;

    // Constructor private để ép sử dụng static factory methods
    private Result(boolean isSuccess, Error error, T value) {
        if (isSuccess && !Objects.equals(error, Error.NONE)) {
            throw new IllegalStateException("Thành công không thể chứa lỗi.");
        }
        if (!isSuccess && Objects.equals(error, Error.NONE)) {
            throw new IllegalStateException("Thất bại phải đi kèm với lỗi.");
        }
        this.isSuccess = isSuccess;
        this.error = error;
        this.value = value;
    }

    public boolean isFailure() { return !isSuccess; }

    public T getValue() {
        if (isFailure()) {
            throw new IllegalStateException("Không thể lấy giá trị từ kết quả thất bại.");
        }
        return value;
    }

    public static Result<Void> success() {
        return new Result<>(true, Error.NONE, null);
    }

    public static <T> Result<T> success(T value) {
        return new Result<>(true, Error.NONE, value);
    }

    public static <T> Result<T> failure(Error error) {
        return new Result<>(false, error, null);
    }
}