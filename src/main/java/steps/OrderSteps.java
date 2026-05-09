package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.OrderModel;

import static data.CourierData.ORDER_DELETE_ENDPOINT;
import static io.restassured.RestAssured.given;

public class OrderSteps {

    @Step("Создание заказа")
    public static Response createOrder(OrderModel order){
        return  given()
                .log().all()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post("/api/v1/orders");
    }

    @Step("получение track заказа")
    public int getOrderTrack(Response response) {
        System.out.println(response.body().asString());
        int track = response.jsonPath().get("track");
        System.out.println("Полученный track: " + track);
        return track;
    }

    @Step("Удаление заказа")
    public void deleteOrder(int track) {
        Response responseId = given()
                .header("Content-type", "application/json")
                .log().all()
                .and()
                .when()
                .put(ORDER_DELETE_ENDPOINT + track);
    }
}
