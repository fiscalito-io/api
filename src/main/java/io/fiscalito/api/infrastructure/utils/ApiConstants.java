package io.fiscalito.api.infrastructure.utils;

public final class ApiConstants {
    private ApiConstants() {
    }

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String TOKEN_TYPE = "Bearer";
    public static final String API_VERSION = "v1";
    public static final String API_BASE_PATH = "/api/" + API_VERSION;
    public static final String AUTH = "/auth";

    public static final String AUTH_BASE_PATH = API_BASE_PATH + AUTH;
    public static final String WHATSAPP= "/whatsapp";
    public static final String WHATSAPP_PATH = API_BASE_PATH + WHATSAPP;
    public static final String ME ="/me";
    public static final String FORGOT_PASSWORD = "/forgot-password";
    public static final String RESET_PASSWORD = "/reset-password";
    public static final String UPDATE_PASSWORD = "/update-password";
    public static final String VALIDATE_ACCOUNT = "/validate-account";
}
