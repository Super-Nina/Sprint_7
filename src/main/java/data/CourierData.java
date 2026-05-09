package data;

import com.github.javafaker.Faker;

public class CourierData {
    public static final String BASE_URI = "https://qa-scooter.praktikum-services.ru";

    static Faker user = new Faker();
    public static  String LOGIN = user.name().lastName() ;
    public static final String PASSWORD = user.regexify("[0-9]{5}");
    public static final String FIRST_NAME = user.name().lastName();

    public static final String COURIER_CREATE_ENDPOINT = "/api/v1/courier/";
    public static final String COURIER_LOGIN_ENDPOINT = "/api/v1/courier/login";
    public static final String ORDER_DELETE_ENDPOINT = "/api/v1/orders/cancel?track=";
}
