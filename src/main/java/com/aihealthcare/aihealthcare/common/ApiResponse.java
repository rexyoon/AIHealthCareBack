package com.aihealthcare.aihealthcare.common;

public class ApiResponse<T> {

    private boolean success;
    private T data;
    private String message;

    public static <T> ApiResponse<T> ok(T data) {
        ApiResponse<T> res = new ApiResponse<>();
        res.success = true;
        res.data = data;
        res.message = null;
        return res;
    }

    public static <T> ApiResponse<T> fail(String message) {
        ApiResponse<T> res = new ApiResponse<>();
        res.success = false;
        res.data = null;
        res.message = message;
        return res;
    }

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
