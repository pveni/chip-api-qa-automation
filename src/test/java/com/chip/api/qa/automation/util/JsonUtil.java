package com.chip.api.qa.automation.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public final class JsonUtil {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static JsonNode getJsonNode(String data) {
        try {
            return objectMapper.readTree(data);
        } catch (JsonProcessingException e) {
            logger.info("Invalid json data");
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Object> readMapJsonFromFile(String filePath) {
        try {
            return objectMapper.readValue(new File(filePath), Map.class);
        } catch (IOException e) {
            logger.info("Invalid json data in file {}", filePath);
            throw new RuntimeException(e);
        }
    }

    public static JsonNode readJsonNodeFromFile(String filePath) {
        try {
            return objectMapper.readTree(new File(filePath));
        } catch (IOException e) {
            logger.info("Invalid json data in file {}", filePath);
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Object> buildResponse(String data) {
        try {
            return objectMapper.readValue(data, Map.class);
        } catch (IOException e) {
            logger.info("Invalid json data ");
            throw new RuntimeException(e);
        }
    }

    public static String getJsonString(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            logger.info("Invalid object");
            throw new RuntimeException(e);
        }
    }
}
