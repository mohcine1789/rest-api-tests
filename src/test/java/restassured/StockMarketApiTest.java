package restassured;

import io.restassured.module.jsv.JsonSchemaValidator;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import java.io.InputStream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class StockMarketApiTest extends BaseTest {

    private static String TIME_SERIES_DAILY_FUNCTION = "TIME_SERIES_DAILY";

    @Test
    void testGetStockSuccessfully_function_TIME_SERIES_DAIL() {
        String IBMSymbol = "IBM";
        String symbolJsonPath = "'Meta Data'.'2. Symbol'";
        InputStream getStockJsonSchema = getClass().getClassLoader()
                .getResourceAsStream("json-schemas/get-stock-validator.json");

        given()
                .spec(requestSpecification)
                .when()
                .queryParam("function", TIME_SERIES_DAILY_FUNCTION)
                .queryParam("symbol", IBMSymbol)
                .get("query")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body(JsonSchemaValidator.matchesJsonSchema(getStockJsonSchema))
                .body(symbolJsonPath, is(equalTo(IBMSymbol)))
                .time(lessThan(1000L));
    }

    @Test
    void testGetStockWithError_apikey_missing() {
        String MBGSymbol = "MBG.DEX";
        String emptyApiKey = "";
        String missingApikeyErrorMessage = "the parameter apikey is invalid or missing";

        given()
                .spec(requestSpecification)
                .when()
                .queryParam("function", TIME_SERIES_DAILY_FUNCTION)
                .queryParam("symbol", MBGSymbol)
                .queryParam("apikey", emptyApiKey)
                .get("query")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .time(lessThan(1000L))
                .body("'Error Message'", containsString(missingApikeyErrorMessage));
    }
}
