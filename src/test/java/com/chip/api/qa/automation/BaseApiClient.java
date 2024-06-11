package com.chip.api.qa.automation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

public abstract class BaseApiClient extends AbstractTestNGSpringContextTests {

    @BeforeEach
    public void setup() {
        // Common setup code, e.g., setting base URI if needed
    }

    @AfterEach
    public void teardown() {
        // Common teardown code
    }
}