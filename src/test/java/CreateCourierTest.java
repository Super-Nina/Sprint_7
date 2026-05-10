import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.CourierModel;
import org.junit.Test;
import static data.CourierData.*;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateCourierTest extends BaseApiTest {

    @Test
    @DisplayName("Создание курьера с валидными данными")
    @Description("Проверка успешного создания курьера при передаче корректных данных")
    public void testCreateCourierFunctionalitySuccess() {
        LOGIN = LOGIN + System.currentTimeMillis();
        CourierModel courier = new CourierModel(LOGIN, PASSWORD, FIRST_NAME);

        Response response = courierSteps.createCourier(courier);
        response.then()
                    .log().all()
                    .statusCode(HTTP_CREATED);
        int courierId = courierSteps.getCourierId(courier);
        courierSteps.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Создание полностью повторяющегося курьера")
    @Description("Проверка, что нельзя создать полностью повторяющегося курьером")
    public void testCreateDuplicateCourier() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD, FIRST_NAME);
        courierSteps.createCourier(courier);
        courierSteps.createCourier(courier)
                .then()
                .log().all()
                .statusCode(HTTP_CONFLICT)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
        }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Проверка, что нельзя создать курьера с пустым логином")
    public void testCreateCourierWithoutLogin() {
        CourierModel courier = new CourierModel(null, PASSWORD, FIRST_NAME);
        courierSteps.createCourier(courier)
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Проверка, что нельзя создать курьера с пустым паролем")
    public void testCreateCourierWithoutPassword() {
        LOGIN = LOGIN + System.currentTimeMillis();
        CourierModel courier = new CourierModel(LOGIN, null, FIRST_NAME);
        courierSteps.createCourier(courier)
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера только с логином и паролем (без firstName)")
    @Description("Проверка, что для создания курьера необходимы только логин и пароль")
    public void testCreateCourierWithoutFirstName() {
        LOGIN = LOGIN + System.currentTimeMillis();
        CourierModel courier = new CourierModel(LOGIN, PASSWORD, null);
        courierSteps.createCourier(courier)
                .then()
                .log().all()
                .statusCode(HTTP_CREATED)
                .body("ok", equalTo(true));
        int courierId = courierSteps.getCourierId(courier);
        courierSteps.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Создание курьера с повторяющимся логином")
    @Description("Проверка, что нельзя создать курьера с повторяющимся логином")
    public void testCreateCourierDuplicateLogin() {
        LOGIN = LOGIN + System.currentTimeMillis();
        CourierModel courier = new CourierModel(LOGIN, PASSWORD, FIRST_NAME);
        courierSteps.createCourier(courier);

        CourierModel courierSecond = new CourierModel(LOGIN, "anotherPassword", FIRST_NAME);
        courierSteps.createCourier(courierSecond)
                .then()
                .log().all()
                .statusCode(HTTP_CONFLICT)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }
    }