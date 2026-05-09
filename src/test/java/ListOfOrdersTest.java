import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class ListOfOrdersTest extends BaseApiTest {
    @Test
    @DisplayName("Проверка списка заказов")
    @Description("Проверка, что возвращается список заказов")
    public void getListOfOrders_ShouldReturnJsonArray() {
        Response response = given()
                .log().all()
                .header("Content-type", "application/json")
                .when()
                .get("/api/v1/orders");

        response
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("orders[0].id", notNullValue());
    }
}