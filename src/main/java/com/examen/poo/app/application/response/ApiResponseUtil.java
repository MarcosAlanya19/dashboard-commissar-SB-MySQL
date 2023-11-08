package com.examen.poo.app.application.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ApiResponseUtil {
  public static ResponseEntity<ApiResponseError> responseApi(HttpStatus status, Object error) {
    return ResponseEntity.status(status).body(new ApiResponseError(status.value(), error));
  }

  public static ResponseEntity<ApiResponseSuccess> responseSuccess(HttpStatus status, Object data) {
    return ResponseEntity.status(status).body(new ApiResponseSuccess(status.value(), data));
  }

  public static ResponseEntity<ApiResponseError> responseError(HttpStatus status, BindingResult bindingResult) {
    List<String> errorMessages = new ArrayList<>();
    for (FieldError fieldError : bindingResult.getFieldErrors()) {
      String errorMessage = fieldError.getField() + " - " + fieldError.getDefaultMessage();
      errorMessages.add(errorMessage);
    }
    return responseApi(status, errorMessages);
  }
}
