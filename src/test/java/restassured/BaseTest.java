package restassured;

import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;


public abstract class BaseTest {

    protected static Dotenv dotEnv = Dotenv.configure().load();
    protected static final String API_KEY = dotEnv.get("API_KEY");
    private static final String BASE_URL = dotEnv.get("BASE_URL");
    protected static final RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBaseUri(BASE_URL)
            .setContentType(ContentType.JSON)
            .addQueryParam("apikey", API_KEY)
            .build();

    @BeforeClass
    void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
