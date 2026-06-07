package com.Gourav.Banking_System.advices;

import com.Gourav.Banking_System.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseNotFound.class)
    public ResponseEntity<ApiResponse<?>> handleNotFound(
            ResponseNotFound exception){

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .build();

        return buildErrorResponse(apiError);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ApiResponse<?>> handleInsufficientBalance(
            InsufficientBalanceException exception){

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(exception.getMessage())
                .build();

        return buildErrorResponse(apiError);
    }

    @ExceptionHandler(WithdrawalLimitExceededException.class)
    public ResponseEntity<ApiResponse<?>> handleWithdrawalLimit(
            WithdrawalLimitExceededException exception){

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(exception.getMessage())
                .build();

        return buildErrorResponse(apiError);
    }

    @ExceptionHandler(OverdraftLimitExceededException.class)
    public ResponseEntity<ApiResponse<?>> handleOverdraft(
            OverdraftLimitExceededException exception){

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(exception.getMessage())
                .build();

        return buildErrorResponse(apiError);
    }

    @ExceptionHandler(InvalidTransactionException.class)
    public ResponseEntity<ApiResponse<?>> handleInvalidTransaction(
            InvalidTransactionException exception){

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(exception.getMessage())
                .build();

        return buildErrorResponse(apiError);
    }

    @ExceptionHandler(SelfTransferException.class)
    public ResponseEntity<ApiResponse<?>> handleSelfTransfer(
            SelfTransferException exception){

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(exception.getMessage())
                .build();

        return buildErrorResponse(apiError);
    }

    @ExceptionHandler(FdNotMaturedException.class)
    public ResponseEntity<ApiResponse<?>> handleFdNotMatured(
            FdNotMaturedException exception){

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(exception.getMessage())
                .build();

        return buildErrorResponse(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidation(
            MethodArgumentNotValidException exception){

        Map<String,String> fieldErrors = new HashMap<>();

        for(FieldError error :
                exception.getBindingResult().getFieldErrors()){

            fieldErrors.put(
                    error.getField(),
                    error.getDefaultMessage());
        }

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Validation Failed")
                .fieldErrors(fieldErrors)
                .build();

        return buildErrorResponse(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(
            Exception exception){

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(exception.getMessage())
                .build();

        return buildErrorResponse(apiError);
    }

    private ResponseEntity<ApiResponse<?>> buildErrorResponse(
            ApiError apiError){

        return new ResponseEntity<>(
                new ApiResponse<>(apiError),
                apiError.getStatus());
    }
}