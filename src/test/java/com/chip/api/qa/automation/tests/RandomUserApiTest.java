package com.chip.api.qa.automation.tests;

import com.chip.api.qa.automation.BaseApiClient;
import com.chip.api.qa.automation.util.ApiClientUtil;
import com.chip.api.qa.automation.util.AssertionUtil;
import com.chip.api.qa.automation.util.JsonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class RandomUserApiTest extends BaseApiClient {

    @Value("${randomUser.baseUrl}")
    private String randomUserApiBaseUrl;

    @DataProvider(name = "numberOfRandomUsers")
    public Object[][] numberOfRandomUsersData() {
        return new Object[][]{
                {"5"},
                {"10"}
        };
    }

    @Test
    public void testGetRandomUser_Success() {
        //Arrange
        String filePath = "src/test/resources/data/random_user/success_response.json";
        JsonNode expectedResponse=JsonUtil.readJsonNodeFromFile(filePath);
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("results", "1");

        //Act
        Response response = ApiClientUtil.get(randomUserApiBaseUrl, "/api", null, queryParams);

        // Assert
        AssertionUtil.assertResponseStatus(response, 200);
        AssertionUtil.assertApiResponseStructure(response.getBody().asString(), expectedResponse);
    }

    @Test(dataProvider = "numberOfRandomUsers")
    public void testGetRandomUsers_Success(String numberOfUsers) {
        //Arrange
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("results", numberOfUsers);

        //Act
        Response response = ApiClientUtil.get(randomUserApiBaseUrl, "/api", null, queryParams);

        // Assert
        AssertionUtil.assertResponseStatus(response, 200);
    }

    @Test
    public void testGetRandomUsers_ResourceNotFound() {
        //Arrange
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("results", "5");

        //Act
        Response response = ApiClientUtil.get(randomUserApiBaseUrl, "/api/api", null, queryParams);

        // Assert
        AssertionUtil.assertResponseStatus(response, 404);
        Assert.assertEquals(response.getBody().asString(), "Not Found");
    }
}
