package com.chip.api.qa.automation.util;

import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.Iterator;
import java.util.Map;

public final class AssertionUtil {

    public static void assertResponseStatus(Response response, int expectedStatusCode) {
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode, "Status code mismatch");
    }

    public static void assertResponseBody(Response response, Map<String, Object> expectedResponseBody) {
        Map<String, Object> actualResponseBody = response.getBody().as(Map.class);
        Assert.assertEquals(actualResponseBody, expectedResponseBody, "Response body mismatch");
    }

    public static void assertApiResponseStructure(String actualResponseBody, JsonNode expected) {
        JsonNode actual = JsonUtil.getJsonNode(actualResponseBody);
        assertJsonStructure(expected, actual);
    }

    private static void assertJsonStructure(JsonNode expected, JsonNode actual) {
        if (expected.isObject() && actual.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = expected.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                Assert.assertTrue(actual.has(field.getKey()), "Missing field: " + field.getKey());
                assertJsonStructure(field.getValue(), actual.get(field.getKey()));
            }
        } else if (expected.isArray() && actual.isArray()) {
            Assert.assertEquals(expected.size(), actual.size(), "Array size mismatch");
            for (int i = 0; i < expected.size(); i++) {
                assertJsonStructure(expected.get(i), actual.get(i));
            }
        } else {
            // If it is a value node, we do not compare values, only structure
            Assert.assertTrue(expected.isValueNode() && actual.isValueNode(), "Node type mismatch");
        }
    }
}
