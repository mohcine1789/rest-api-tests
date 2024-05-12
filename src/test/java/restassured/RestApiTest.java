package restassured;

import io.restassured.module.jsv.JsonSchemaValidator;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;


import java.io.InputStream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class RestApiTest extends BaseTest {

    @Test
    void testGetStockSuccessfully_function_TIME_SERIES_DAIL() {
        String symbol = "IBM";
        String function = "TIME_SERIES_DAILY";
        String symbolJsonPath = "'Meta Data'.'2. Symbol'";
        InputStream getStockJsonSchema = getClass ().getClassLoader ()
                .getResourceAsStream ("json-format/get-stock-validator.json");

        given()
                .spec(requestSpecification)
        .when()
                .queryParam("function", function)
                .queryParam("symbol", symbol)
                .get("query")
        .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body(JsonSchemaValidator.matchesJsonSchema(getStockJsonSchema))
                .body(symbolJsonPath, is(equalTo(symbol)))
                .time(lessThan(1000L));
    }

    @Test
    void testGetStockWithError_apikey_missing() {
        String symbol = "IBM";
        String function = "TIME_SERIES_DAILY";
        String missingApikeyErrorMessage = "the parameter apikey is invalid or missing";

        given()
                .spec(requestSpecification)
                .when()
                .queryParam("function", function)
                .queryParam("symbol", symbol)
                .queryParam("apikey", "")
                .get("query")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .time(lessThan(1000L))
                .body("'Error Message'", containsString(missingApikeyErrorMessage));
    }
}
