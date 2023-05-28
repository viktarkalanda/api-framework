package steps;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.experimental.UtilityClass;

import java.nio.charset.StandardCharsets;

@UtilityClass
public final class RequestSpecs {
    public static RequestSpecification getRequestSpec(String baseUri, String basePath) {
        return commonBuilder(baseUri, basePath)
                .addHeader("accept", "*/*")
                .build();
    }

    private static RequestSpecBuilder commonBuilder(String baseUri, String basePath) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setBasePath(basePath)
                .addFilter(new AllureRestAssured())
                .setContentType(ContentType.JSON.withCharset(StandardCharsets.UTF_8));
    }
}
