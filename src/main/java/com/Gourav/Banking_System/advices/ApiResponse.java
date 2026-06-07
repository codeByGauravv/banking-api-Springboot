package com.Gourav.Banking_System.advices;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiResponse<T> {

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timeStamp;

    private T data;

    private ApiError errors;

    public ApiResponse() {
        this.timeStamp = LocalDateTime.now();
    }

    public ApiResponse(T data) {
        this();
        this.data = data;
    }

    public ApiResponse(ApiError errors) {
        this();
        this.errors = errors;
    }
}