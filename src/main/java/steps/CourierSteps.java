import io.restassured.response.Response;
import model.CourierModel;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Steps {

//    Получение id курьера
//    НАДО СТЕРЕТЬ ЛИШНЕЕ
    public int getCourierId() {
        CourierModel courier = new CourierModel("Vovchik", "1234", "Vladimir");
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier/login");
        System.out.println(response.body().asString());
        int id = response.jsonPath().get("id");
        System.out.println("Полученный ID: " + id);
        return id;
    }

//    Удаление курьера
//    НАДО СТЕРЕТЬ ЛИШНЕЕ
    public void deleteCourier(){
        Response responseId = given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .delete("/api/v1/courier/" + getCourierId());
        System.out.println(responseId.body().asString());
        responseId.then().assertThat().body("ok",equalTo(true));

    }
}
