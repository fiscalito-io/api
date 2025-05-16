package online.tufactura.api.infrastructure.utils;

public final class ApiConstants {
    private ApiConstants() {
    }

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String API_VERSION = "v1";
    public static final String API_BASE_PATH = "/api/" + API_VERSION;
    public static final String AUTH = "/auth";
    public static final String AUTH_BASE_URL = API_BASE_PATH + AUTH;
    public static final String LOGIN = "/login";
    public static final String GOOGLE_LOGIN= "/google/login";
}
