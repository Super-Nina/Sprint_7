import io.restassured.RestAssured;
import org.junit.Before;
import steps.CourierSteps;
import steps.OrderSteps;
import static data.CourierData.BASE_URI;

public class BaseApiTest {
    protected static  CourierSteps courierSteps;
    protected static OrderSteps orderSteps;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
        courierSteps = new CourierSteps();
        orderSteps = new OrderSteps();
    }
}
