package utilities.logging;

import java.util.Arrays;

public enum TestStatus {
    CREATED(-1),
    SUCCESS(1),
    FAILURE(2),
    SKIP(3),
    SUCCESS_PERCENTAGE_FAILURE(4),
    STARTED(16);

    private final int statusCode;

    TestStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    public static TestStatus getStatus(int statusCode) {
        return Arrays.stream(values())
                .filter(testStatus -> testStatus.statusCode == statusCode)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Could not find test status with status code %s", statusCode)));
    }
}
