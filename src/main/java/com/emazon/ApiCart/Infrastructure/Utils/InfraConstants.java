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
    public static final String USER_PATH = "/{id}";
    public static final String BRAND_PATH = "/brand";
    public static final String CATEGORY_PATH = "/category";
    public static final String ITEM_PATH = "/item";
    public static final String ORDER = "/{order}";
    public static final String TYPE_ORDER = "/{order}/{variable}";
    public static final String SUPPLY_PATH = "/increase";

    public static final String HAS_WAREHOUSE_AUX_OR_ROLE_ADMIN = "hasRole('WAREHOUSE_AUX') or hasRole('ADMIN')";
    public static final String HAS_ROLE_ADMIN = "hasRole('ADMIN')";
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_WAREHOUSE_AUX = "WAREHOUSE_AUX";




    public static String getPath(String basePath, String path) {
        return basePath + path; // Build the complete path
    }




    public InfraConstants() {
        throw new UnsupportedOperationException("This is a constants class and cannot be instantiated.");
    }
}
