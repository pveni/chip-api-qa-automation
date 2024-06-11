package com.chip.api.qa.automation.util;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;

public final class ApiClientUtil {
    private static final Logger logger = LoggerFactory.getLogger(ApiClientUtil.class);

    public static Response get(String baseUrl, String endpoint, Map<String, String> headers, Map<String, String> queryParams) {
        if (headers == null) {
            headers = Collections.emptyMap();
        }
        if (queryParams == null) {
            queryParams = Collections.emptyMap();
        }

        try {
            logger.info("GET Request to URL: {}{}", baseUrl, endpoint);
            Response response = RestAssured
                    .given()
                    .baseUri(baseUrl)
                    .headers(headers)
                    .queryParams(queryParams)
                    .get(endpoint)
                    .then()
                    .extract()
                    .response();
            logger.info("Response: {}", response.asString());
            return response;
        } catch (Exception e) {
            logger.error("Error in GET request: ", e);
            throw e;
        }
    }

    public static Response post(String baseUrl, String endpoint, Map<String, String> headers, Object body) {
        if (headers == null) {
            headers = Collections.emptyMap();
        }

        try {
            logger.info("POST Request to URL: {}{}", baseUrl, endpoint);
            Response response = RestAssured
                    .given()
                    .baseUri(baseUrl)
                    .headers(headers)
                    .body(body)
                    .post(endpoint)
                    .then()
                    .extract()
                    .response();
            logger.info("Response: {}", response.asString());
            return response;
        } catch (Exception e) {
            logger.error("Error in POST request: ", e);
            throw e;
        }
    }

}
