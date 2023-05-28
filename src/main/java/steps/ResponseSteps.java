package steps;


import io.qameta.allure.Step;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class ResponseSteps {

    @Step("Check that status code is {statusCode}")
    public static void checkStatusCode(Response response, int statusCode) {
        response.then()
                .assertThat()
                .statusCode(statusCode);
    }

    @Step("Check that response has correct JSON schema")
    public static void checkJsonSchema(Response response, String pathToSchema) {
        JsonSchemaValidator.matchesJsonSchemaInClasspath(pathToSchema).matches(response);
    }

}
