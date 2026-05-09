import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.CourierModel;
import org.junit.BeforeClass;
import org.junit.Test;

import static data.CourierData.BASE_URI;
import static data.CourierData.COURIER_CREATE_ENDPOINT;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class AuthCourierTest extends BaseApiTest {
    private static CourierModel courierForAuth;

    @BeforeClass
    public static void createCourierForAuth() {
        RestAssured.baseURI = BASE_URI;
         courierForAuth = new CourierModel("login" + System.currentTimeMillis(), "password", "first_name");
        Response response = given()
                .log().all()
                .header("Content-type", "application/json")
                .and()
                .body(courierForAuth)
                .when()
                .post(COURIER_CREATE_ENDPOINT);
        System.out.println(response.asString());
    }

    @Test
    @DisplayName("Курьер может авторизоваться") // имя теста
    @Description("Проверка, что курьер может авторизоваться с валидными данными и успешный запрос возвращает id")
    public void TestCourierAuthorization() {
        courierSteps.courierAuth(courierForAuth)
                .then()
                .log().all()
                .statusCode(200)
                .body("id", notNullValue());
        int courierId = courierSteps.getCourierId(courierForAuth);
        courierSteps.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Попытка авторизации без логина") // имя теста
    @Description("Проверка, что нельзя авторизоваться без логина, и такой запрос вернет ошибку")
    public void TestCourierAuthorizationWithoutLogin() {
        CourierModel courierWithoutLogin = new CourierModel(null, "password", "first_name");
        Response response = courierSteps.courierAuth(courierWithoutLogin);
              response.then()
                .log().all()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Попытка авторизации без пароля")
    @Description("Проверка, что нельзя авторизоваться без пароля, и такой запрос вернет ошибку")
    public void TestCourierAuthorizationWithoutPassword() {
        String login = courierForAuth.getLogin();
        CourierModel courierWithoutPassword = new CourierModel(login, null, "first_name");
        Response response = courierSteps.courierAuth(courierWithoutPassword);
        response.then()
                .log().all()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Попытка авторизации под несуществующим пользователем")
    @Description("Проверка, что нельзя авторизоваться под несуществующим пользователем, и такой запрос вернет ошибку")
    public void testAuthorizationWithNonexistentUser() {
        CourierModel courierWithoutPassword = new CourierModel("login" + System.currentTimeMillis(), "password", "first_name");
        Response response = courierSteps.courierAuth(courierWithoutPassword);
        response.then()
                .log().all()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Попытка авторизации с неверным логином")
    @Description("Проверка, что система вернет ошибку при авторизации с неверным логином")
    public void TestCourierAuthorizationWithWrongLogin() {
            String login = courierForAuth.getLogin();
            String wrongLogin = login +"_wrong";
            CourierModel courierWithWrongLogin = new CourierModel(wrongLogin, "password", "first_name");
            Response response = courierSteps.courierAuth(courierWithWrongLogin);
            response.then()
                    .log().all()
                    .statusCode(404)
                    .body("message", notNullValue());
        }

    @Test
    @DisplayName("Попытка авторизации с неверным паролем")
    @Description("Проверка, что система вернет ошибку при  авторизации с неверным паролем")
    public void TestCourierAuthorizationWithWrongPassword() {
            String login = courierForAuth.getLogin();
            String password = courierForAuth.getPassword();
            String wrongPassword = password +"_wrong";
            CourierModel courierWithWrongLogin = new CourierModel(login, wrongPassword, "first_name");
            Response response = courierSteps.courierAuth(courierWithWrongLogin);
            response.then()
                    .log().all()
                    .statusCode(404)
                    .body("message", notNullValue());
        }
}
