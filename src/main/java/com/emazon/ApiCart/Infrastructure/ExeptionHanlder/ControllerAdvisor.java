package com.emazon.ApiCart.Infrastructure.ExeptionHanlder;

import com.emazon.ApiCart.Domain.Exeptions.*;
import com.emazon.ApiCart.Infrastructure.Utils.InfraConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleItemNotFoundException(
            ItemNotFoundException itemNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(InfraConstants.MESSAGE, ExceptionResponse.ITEM_NOT_FOUND.getMessage()));
    }

    @ExceptionHandler(InvalidItemException.class)
    public ResponseEntity<Map<String, String>> invalidItemException(
            InvalidItemException invalidItemException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(InfraConstants.MESSAGE, ExceptionResponse.INVALID_ITEM.getMessage()));
    }
    @ExceptionHandler(InvalidQuantityException.class)
    public ResponseEntity<Map<String, String>> invalidQuantityException(
            InvalidQuantityException invalidQuantityException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(InfraConstants.MESSAGE, ExceptionResponse.INVALID_QUANTITY.getMessage()));
    }
    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<Map<String, String>> invalidUserException(
            InvalidUserException invalidUserException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(InfraConstants.MESSAGE, ExceptionResponse.INVALID_USER.getMessage()));
    }
    @ExceptionHandler(CartIsNullExeption.class)
    public ResponseEntity<Map<String, String>> cartIsNullExeption(
            CartIsNullExeption cartIsNullExeption) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(InfraConstants.MESSAGE, ExceptionResponse.CART_IS_NULL.getMessage()));
    }
    @ExceptionHandler(QuantityIsNotEnough.class)
    public ResponseEntity<Map<String, String>> quantityIsNotEnough(
            QuantityIsNotEnough quantityIsNotEnough) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(InfraConstants.MESSAGE, ExceptionResponse.QUANTITY_IS_NOT_ENOUGH.getMessage()));
    }

}

