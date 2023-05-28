package utilities.logging;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.testng.ITestResult;

import java.util.Arrays;
import java.util.Objects;

@UtilityClass
public class TestResultHelper {

    public String getTestName(ITestResult result) {
        String pureName = result.getName() != null ? result.getName() : result.getMethod().getMethodName();
        Object[] parameters = result.getParameters();
        return parameters != null && parameters.length > 0
                ? String.format("%s%nparameters: [%s]", pureName,
                StringUtils.joinWith(", ",
                        Arrays.stream(result.getParameters())
                                .filter(Objects::nonNull)
                                .map(parameter -> parameter.getClass().isArray()
                                        ? String.format("%n[%s]", StringUtils.join((Object[]) parameter, ", "))
                                        : parameter.toString())
                                .toArray()))
                : pureName;
    }
}
