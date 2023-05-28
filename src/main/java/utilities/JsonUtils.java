package utilities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;


@UtilityClass
public class JsonUtils {
    private static final ObjectMapper OBJECT_MAPPER = getConfiguration();

    public static <T> T parseObject(JsonNode node, Class<T> tClass) {
        return OBJECT_MAPPER.convertValue(node, tClass);
    }

    @SneakyThrows
    public JsonNode readTree(String stringValue) {
        return OBJECT_MAPPER.readTree(stringValue);
    }

    private static ObjectMapper getConfiguration() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
        objectMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
        return objectMapper;
    }
}
