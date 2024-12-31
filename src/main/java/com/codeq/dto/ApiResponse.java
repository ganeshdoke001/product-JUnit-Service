package com.codeq.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiResponse<T> {

    private boolean isSuccess;
    private String message;
    private T data;

    public ApiResponse(boolean isSuccess, String message, T data) {

        super();
        this.isSuccess = isSuccess;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(
            String message,
            T data) {

        return new ApiResponse<T>(true, message, data);
    }

    public static <T> ApiResponse<T> success(
            T data) {

        return new ApiResponse<T>(true, data);
    }

    public static <T> ApiResponse<T> error(
            String message,
            T data) {

        return new ApiResponse<T>(false, message, data);
    }

    public ApiResponse(boolean isSuccess, T data) {

        super();
        this.isSuccess = isSuccess;
        this.data = data;
    }

}
