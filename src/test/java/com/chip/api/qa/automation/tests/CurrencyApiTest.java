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
public class CurrencyApiTest extends BaseApiClient {

    @Value("${currency.baseUrl}")
    private String currencyApiBaseUrl;

    @DataProvider(name = "currencyDataProvider")
    public Object[][] currencyDataProvider() {
        return new Object[][]{
                {"USD", 200},
                {"InvalidCurrency", 200},
                {"!=_<,~¬`", 200}
        };
    }

    @Test(dataProvider = "currencyDataProvider")
    public void testGetExchangeRates_Success(String key, int value) {
        //Arrange
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("base", key);

        //Act
        Response response = ApiClientUtil.get(currencyApiBaseUrl, "/latest", null, queryParams);

        // Assert
        AssertionUtil.assertResponseStatus(response, value);
    }

    @Test
    public void testCurrencyInvalidApiKey() {
        // Arrange
        String filePath = "src/test/resources/data/currency/invalid_api_key_response.json";
        Map<String, Object> expectedResponseBody = JsonUtil.readMapJsonFromFile(filePath);

        // Simulate an invalid API key
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("apikey", "invalid_api_key");

        // Act
        Response response = ApiClientUtil.get(currencyApiBaseUrl, "/latest", null, queryParams);

        // Assert
        AssertionUtil.assertResponseStatus(response, 200);
        AssertionUtil.assertResponseBody(response, expectedResponseBody);
    }
}
