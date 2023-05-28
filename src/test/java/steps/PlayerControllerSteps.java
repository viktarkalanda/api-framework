package steps;

import configuration.Settings;
import endpoints.PlayerController;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public final class PlayerControllerSteps {
    private final String baseUrl;

    public PlayerControllerSteps() {
        this.baseUrl = Settings.getBaseUrl();
    }

    private RequestSpecification getRequestSpec() {
        return RequestSpecs.getRequestSpec(baseUrl, PlayerController.ENDPOINT_BASE.getValue());
    }

    @Step("Get request get All Players")
    public Response getAllPlayers() {
        return given()
                .spec(getRequestSpec())
                .and()
                .when()
                .get(PlayerController.GET_GET_ALL_PLAYERS.getValue());
    }

    @Step("GET request create Player by editor {pathParam}")
    public Response createPlayer(Map<String, String> queryParameters, String pathParam) {
        String endpoint = PlayerController.GET_CREATE_PLAYER.getValue();
        return given()
                .spec(getRequestSpec())
                .and()
                .queryParams(queryParameters)
                .pathParam("editor", pathParam)
                .when()
                .get(endpoint);
    }

    @Step("DELETE request delete Player by playerId {playerId} and editor {pathParam}")
    public Response deletePlayer(int playerId, String pathParam) {
        String endpoint = PlayerController.DELETE_DELETE_PLAYER.getValue();
        return given()
                .spec(getRequestSpec())
                .and()
                .body("{\"playerId\": " + playerId + "}")
                .pathParam("editor", pathParam)
                .when()
                .get(endpoint);
    }

}
