package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.CourierModel;
import static data.CourierData.*;
import static io.restassured.RestAssured.given;

public class CourierSteps {

    @Step("Создание курьера")
    public static Response createCourier(CourierModel courier) {
        return given()
                .log().all()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(COURIER_CREATE_ENDPOINT);
    }

    @Step("Получение id курьера")
    public int getCourierId(CourierModel courier) {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(COURIER_LOGIN_ENDPOINT);
        int id = response.jsonPath().get("id");
        return id;
    }

    @Step("Авторизация курьера")
    public Response courierAuth(CourierModel courier) {
        return given()
                .log().all()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(COURIER_LOGIN_ENDPOINT);
    }

    @Step("Удаление курьера")
    public void deleteCourier(int courierId) {
        Response responseId = given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .delete(COURIER_CREATE_ENDPOINT + courierId);
    }

    @Step("Создание курьера для авторизации")
    public static Response createCourierForAuth(CourierModel courierForAuth) {
        return  given()
                .log().all()
                .header("Content-type", "application/json")
                .and()
                .body(courierForAuth)
                .when()
                .post(COURIER_CREATE_ENDPOINT);
    }
    @Step("Удаление курьера для авторизации")
    public static Response deleteCourierForAuth(int courierId) {
        Response responseId = given()
                .log().all()
                .header("Content-type", "application/json")
                .and()
                .when()
                .delete(COURIER_CREATE_ENDPOINT + courierId);
        return responseId;
    }
}
