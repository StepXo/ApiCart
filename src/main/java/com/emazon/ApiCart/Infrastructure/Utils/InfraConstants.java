package com.emazon.ApiCart.Infrastructure.Utils;

public class InfraConstants {
    public static final String ZERO = "0 ";
    public static final String TEN = "10 ";
    public static final int SEVEN = 7;

    public static final String SPRING = "Spring";
    public static final String BEARER = "Bearer ";
    public static final String AUTHORIZATION = "Authorization";
    public static final String MESSAGE = "Message";
    public static final String USER_API = "Api-User";
    public static final String USER = "/{id}";
    public static final String CART = "/cart";
    public static final String DELETE = "/delete";
    public static final String ORDER = "/{order}";
    public static final String TYPE_ORDER = "/{order}/{variable}";
    public static final String BUY = "/buy";

    public static final String ITEM_NOT_FOUND = "No item was found with that name";
    public static final String QUANTITY_IS_NOT_ENOUGH = "The quantity is not available";
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_USER = "USER";

    private InfraConstants() {
        throw new UnsupportedOperationException("This is a constants class and cannot be instantiated.");
    }
}
