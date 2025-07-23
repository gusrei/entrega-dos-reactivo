package cl.tenpo.learning.reactive.tasks.task2.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    public static String parsePercentage(String valueStr) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(valueStr);
            String percentageStr = node.get("percentage").asText();
            return percentageStr.replace(",", ".");
        } catch (Exception e) {
            throw new RuntimeException("Invalid percentage format: " + valueStr, e);
        }
    }
}