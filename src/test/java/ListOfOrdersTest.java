import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.CoreMatchers.notNullValue;

public class ListOfOrdersTest extends BaseApiTest {
    @Test
    @DisplayName("Проверка списка заказов")
    @Description("Проверка, что возвращается список заказов")
    public void getListOfOrders_ShouldReturnJsonArray() {
        Response response = orderSteps.detListOfOrders();
        response.then()
                .log().all()
                .statusCode(HTTP_OK)
                .contentType("application/json")
                .body("orders[0].id", notNullValue());
    }
}