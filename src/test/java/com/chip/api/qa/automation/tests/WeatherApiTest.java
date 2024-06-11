package com.chip.api.qa.automation.tests;

import com.chip.api.qa.automation.BaseApiClient;
import com.chip.api.qa.automation.util.ApiClientUtil;
import com.chip.api.qa.automation.util.AssertionUtil;
import com.chip.api.qa.automation.util.JsonUtil;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class WeatherApiTest extends BaseApiClient {
    private static final String WEATHER_DATA_SUCCESS_RESPONSE_FILE_PATH_LONDON = "src/test/resources/data/weather/success_response_body_london.json";
    private static final String WEATHER_DATA_SUCCESS_RESPONSE_FILE_PATH_NEW_YORK = "src/test/resources/data/weather/success_response_body_new_york.json";
    private static final String WEATHER_DATA_SUCCESS_RESPONSE_FILE_PATH_TOKYO = "src/test/resources/data/weather/success_response_body_tokyo.json";
    private static final String WEATHER_DATA_SUCCESS_RESPONSE_FILE_PATH_MUMBAI = "src/test/resources/data/weather/success_response_body_mumbai.json";

    @Value("${weather.baseUrl}")
    private String weatherApiBaseUrl;

    @Value("${weather.apiKey}")
    private String weatherApiKey;

    @Value("${weather.version}")
    private String version;

    @DataProvider(name = "cities")
    public Object[][] createCityData() {
        return new Object[][]{
                {"London"},
                {"New York"},
                {"Tokyo"},
                {"Mumbai"}
        };
    }

    @Test(dataProvider = "cities")
    public void testGetWeatherByCity_Success(String city) {
        //Arrange
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("q", city);
        queryParams.put("appid", weatherApiKey);

        //Act
        Response response = ApiClientUtil.get(weatherApiBaseUrl, version + "/weather", null, queryParams);

        // Assert
        AssertionUtil.assertResponseStatus(response, 200);
        switch (city) {
            case "London":
                AssertionUtil.assertApiResponseStructure(response.getBody().asString(),
                        JsonUtil.readJsonNodeFromFile(
                                WEATHER_DATA_SUCCESS_RESPONSE_FILE_PATH_LONDON));
                break;
            case "New York":
                AssertionUtil.assertApiResponseStructure(response.getBody().asString(),
                        JsonUtil.readJsonNodeFromFile(
                                WEATHER_DATA_SUCCESS_RESPONSE_FILE_PATH_NEW_YORK));
            case "Tokyo":
                AssertionUtil.assertApiResponseStructure(response.getBody().asString(),
                        JsonUtil.readJsonNodeFromFile(
                                WEATHER_DATA_SUCCESS_RESPONSE_FILE_PATH_TOKYO));
            case "Mumbai":
                AssertionUtil.assertApiResponseStructure(response.getBody().asString(),
                        JsonUtil.readJsonNodeFromFile(
                                WEATHER_DATA_SUCCESS_RESPONSE_FILE_PATH_MUMBAI));
        }
    }

    @DataProvider(name = "inValidData")
    public Object[][] inValidCityData() {
        return new Object[][]{
                {"InvalidCity"},
                {"abc"},
                {";;;]{`¬"},
                {"231231232"}
        };
    }

    @Test(dataProvider = "inValidData")
    public void testGetWeatherByCity_ResourceNotFound(String city) {
        // Arrange - Invalid city name
        String filePath = "src/test/resources/data/weather/not_found_response.json";
        Map<String, Object> expectedResponseBody = JsonUtil.readMapJsonFromFile(filePath);
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("q", city);
        queryParams.put("appid", weatherApiKey);

        // Act
        Response response = ApiClientUtil.get(weatherApiBaseUrl, version + "/weather", null, queryParams);

        // Assert
        AssertionUtil.assertResponseStatus(response, 404);
        AssertionUtil.assertResponseBody(response, expectedResponseBody);
    }

    @Test
    public void testGetWeatherByCity_InvalidApiKey() {
        // Arrange - Incorrect API key
        String filePath = "src/test/resources/data/weather/invalid_api_key_response.json";
        Map<String, Object> expectedResponseBody = JsonUtil.readMapJsonFromFile(filePath);
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("q", "London");
        queryParams.put("appid", "invalidApiKey");

        // Act
        Response response = ApiClientUtil.get(weatherApiBaseUrl, version + "/weather", null, queryParams);

        // Assert
        AssertionUtil.assertResponseStatus(response, 401);
        AssertionUtil.assertResponseBody(response, expectedResponseBody);
    }
}
