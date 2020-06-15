package com.task2;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.*;

import static org.hamcrest.Matchers.*;
import java.util.Arrays;

public class FixedEndpointTest
{
    @Test(groups={"test"})
    public void verifyCorrectHTTPStatusCode() {
        // set default path as google.com
        String path = System.getProperty("path") == null ? "https://www.google.com" : System.getProperty("path");
        RequestSpecification rest;
        if (path.contains("https")) {
            rest = RestAssured.given()
                .relaxedHTTPSValidation();
        } else {
            rest = RestAssured.given();
        }
        rest
            .when()
            .get(path)
            .then()
            .statusCode(200)
            .body("$", equalTo("it works!!!"));
    }

}