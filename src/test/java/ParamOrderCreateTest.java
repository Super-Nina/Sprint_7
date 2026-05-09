import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.OrderModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class ParamOrderCreateTest extends BaseApiTest{
    private static String firstName;
    private static String lastName;
    private static String address;
    private static int metroStation;
    private static String phone;
    private static int rentTime;
    private static String deliveryDate;
    private static String comment;
    private static List<String> color;

    public ParamOrderCreateTest(String firstName, String lastName, String address, int metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color ) {
        this.firstName=firstName;
        this.lastName=lastName;
        this.address=address;
        this.metroStation=metroStation;
        this.phone=phone;
        this.rentTime=rentTime;
        this.deliveryDate=deliveryDate;
        this.comment=comment;
        this.color=color;
    }

    @Parameterized.Parameters(name = "выбор цвета #{0}")
    public static Collection<Object[]> data() {
        return java.util.Arrays.asList(
                new Object[] {"Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", Arrays.asList("BLACK")},
                new Object[]  {"Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", Arrays.asList("GREY")},
                new Object[]   {"Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", Arrays.asList("BLACK", "GREY")},
                new Object[]  {"Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", Arrays.asList("")}
                );
    }

    @Test
    @DisplayName("Создание заказа") // имя теста
    @Description("Проверка, что при создании заказа можно указать черный, серый, черный и серый или вовсе не указывать цвет. В ответ вернется track")
    public void checkOrderFormWithColorSelection() {
        OrderModel order = new OrderModel(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        Response response = orderSteps.createOrder(order)
                .then()
                .log().all()
                .statusCode(201)
                .body("track", notNullValue())
                .extract().response();
        int track = orderSteps.getOrderTrack(response);
        orderSteps.deleteOrder(track);
    }
}
