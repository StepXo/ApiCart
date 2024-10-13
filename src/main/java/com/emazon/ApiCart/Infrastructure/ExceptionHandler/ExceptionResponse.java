package com.emazon.ApiCart.Infrastructure.ExceptionHandler;


public enum ExceptionResponse {
    ITEM_NOT_FOUND("No item was found with that name"),
    QUANTITY_IS_NOT_ENOUGH("The quantity is not available"),
    INVALID_ITEM("Item ID is invalid"),
    INVALID_QUANTITY("The quantity must be greater than or equal to 1"),
    INVALID_USER("The user must not be null or empty"),
    CART_IS_NULL("The cart cannot be null"),
    EMPTY_CART("The cart is empty")
    ;


    private String message;

    ExceptionResponse(String message) {
            this.message = message;
        }
        public String getMessage() {
            return this.message;
        }

}
