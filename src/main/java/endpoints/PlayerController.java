package endpoints;

public enum PlayerController {
    ENDPOINT_BASE("/player"),
    GET_CREATE_PLAYER("/create/{editor}"),
    DELETE_DELETE_PLAYER("/delete/{editor}"),
    POST_GET_PLAYER_BY_PLAYER_ID("/get"),
    GET_GET_ALL_PLAYERS("/get/all"),
    PATCH_UPDATE_PLAYER("/update/{editor}/{id}");

    private final String value;

    PlayerController(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
