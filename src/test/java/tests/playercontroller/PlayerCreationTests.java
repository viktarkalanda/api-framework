package tests.playercontroller;

import com.fasterxml.jackson.databind.JsonNode;
import configuration.Settings;
import constants.JsonSchemas;
import dtos.playercontroller.response.PlayerGetAllResponseDto;
import io.qameta.allure.Description;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.testng.annotations.Test;
import steps.PlayerControllerSteps;
import steps.ResponseSteps;
import tests.BaseTest;
import utilities.JsonUtils;
import utilities.configuration.ResourceFile;

public class PlayerCreationTests extends BaseTest {

    private static final String PLAYER_CONTROLLER_TEST_DATA_JSON = "testdata/player-controller-test-data.json";
    private static final String EDITOR = Settings.getEditor();
    private final PlayerControllerSteps playerControllerSteps = new PlayerControllerSteps();

    @BeforeMethod
    public void beforeMethod() {
        PlayerGetAllResponseDto playerGetAllResponseDto = playerControllerSteps
                .getAllPlayers()
                .body()
                .as(PlayerGetAllResponseDto.class);

        Arrays.stream(playerGetAllResponseDto.getPlayers()).forEach(player -> {
            if (!Objects.isNull(player.getScreenName()) && player.getScreenName().equals("TestUser")) {
                playerControllerSteps.deletePlayer(player.getId(), EDITOR);
            }
        });
    }

    @Test(dataProvider = "playerData")
    @Description("Create player with valid data")
    public void createPlayerPositiveTest(Map<String, String> playerData) {
        Response response = playerControllerSteps.createPlayer(playerData, EDITOR);
        ResponseSteps.checkStatusCode(response, HttpStatus.SC_OK);
        ResponseSteps.checkJsonSchema(response, JsonSchemas.PLAYER_JSON_SCHEMA_RESPONSE);
    }

    @Test(dataProvider = "playerData")
    @Description("Verify that user age is validated (should be between 16 and 60)")
    public void testCreatePlayerAgeValidation(Map<String, String> playerData) {
        Response response = playerControllerSteps.createPlayer(playerData, EDITOR);
        ResponseSteps.checkStatusCode(response, HttpStatus.SC_BAD_REQUEST);
    }

    @Test(dataProvider = "playerData")
    @Description("Verify that user creation is restricted to 'supervisor' or 'admin' roles")
    public void testCreatePlayerRoleValidation(Map<String, String> playerData) {
        Response response = playerControllerSteps.createPlayer(playerData, "user");
        ResponseSteps.checkStatusCode(response, HttpStatus.SC_FORBIDDEN);
    }

    @Test(dataProvider = "playerData")
    @Description("Verify that user creation is restricted to 'admin' or 'user' roles")
    public void testCreatePlayerValidRoles(Map<String, String> playerData) {
        Response response = playerControllerSteps.createPlayer(playerData, EDITOR);
        ResponseSteps.checkStatusCode(response, HttpStatus.SC_BAD_REQUEST);
    }

    @Test(dataProvider = "playerData")
    @Description("Verify that user creation fails when the login already exists")
    public void testCreatePlayerUniqueLogin(Map<String, String> playerData) {
        // Create a user with the given login
        Response firstResponse = playerControllerSteps.createPlayer(playerData, EDITOR);
        ResponseSteps.checkStatusCode(firstResponse, HttpStatus.SC_OK);

        // Attempt to create a user with the same login
        Response secondResponse = playerControllerSteps.createPlayer(playerData, EDITOR);
        ResponseSteps.checkStatusCode(secondResponse, HttpStatus.SC_BAD_REQUEST);
    }

    @Test(dataProvider = "playerData")
    @Description("Verify that user creation fails when the screenName already exists")
    public void testCreatePlayerUniqueScreenName(Map<String, String> playerData) {
        // Create a user with the given screenName
        Response firstResponse = playerControllerSteps.createPlayer(playerData, EDITOR);
        ResponseSteps.checkStatusCode(firstResponse, HttpStatus.SC_OK);

        // Attempt to create a user with the same screenName
        Response secondResponse = playerControllerSteps.createPlayer(playerData, EDITOR);
        ResponseSteps.checkStatusCode(secondResponse, HttpStatus.SC_BAD_REQUEST);
    }

    @Test(dataProvider = "playerData")
    @Description("Verify that password validation is performed during user creation")
    public void testCreatePlayerPasswordValidation(Map<String, String> playerData) {
        Response response = playerControllerSteps.createPlayer(playerData, EDITOR);
        ResponseSteps.checkStatusCode(response, HttpStatus.SC_BAD_REQUEST);
    }

    @Test(dataProvider = "playerData")
    @Description("Verify that gender validation is performed during user creation")
    public void testCreatePlayerGenderValidation(Map<String, String> playerData) {
        Response response = playerControllerSteps.createPlayer(playerData, EDITOR);
        ResponseSteps.checkStatusCode(response, HttpStatus.SC_BAD_REQUEST);
    }

    @DataProvider(name = "playerData")
    public Object[][] getPlayerData(Method testMethod) {
        ResourceFile resourceFile = new ResourceFile(PLAYER_CONTROLLER_TEST_DATA_JSON);
        JsonNode jsonNode = JsonUtils
                .readTree(resourceFile.getFileContent())
                .get(testMethod.getName());

        return new Object[][]{{JsonUtils.parseObject(jsonNode, HashMap.class)}};
    }

}
