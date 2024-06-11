# Random User API Test Suite

## Overview
This project contains automated tests for the below API(s) using JUnit, TestNG and RestAssured. It includes both happy path and unhappy path tests.

- Weather api: https://api.openweathermap.org/data/
- Random user api: https://randomuser.me
- Currency api: https://api.exchangeratesapi.io

allure:
results:
directory: target/allure-results

## Prerequisites
- Java 17
- Maven
- Docker

## Running the Tests

### Locally
1. Clone the repository. <git clone https://github.com/pveni/chip-api-qa-automation.git>
2. Navigate to the project directory.
3. Run the tests with Maven:
   ```bash
   mvn clean test
4. Run below command to generate allure report with Maven:
   ```bash
   mvn allure:serve

## CI/CD
- Implemented using Github Actions - please find below link to the pipeline 
   <https://github.com/pveni/chip-api-qa-automation/actions>