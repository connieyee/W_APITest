package com.connieyee.test.task1;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.*;

import io.restassured.response.Response;

import static org.hamcrest.Matchers.*;

public class RainFall
{
    private static RequestSpecification requestSpec;

    @BeforeClass(groups={"test"})
    public static void createRequestSpecification() {
        System.out.println("BeforeClassBeforeClassBeforeClassBeforeClassBeforeClass");
    	requestSpec = new RequestSpecBuilder()
    		.setBaseUri("https://data.weather.gov.hk/weatherAPI/opendata/weather.php")
    		.build();
    }

    @Test(groups={"test"})
    public void isRainfallUnitValid() {
        RestAssured.given()
        .relaxedHTTPSValidation()
    	    .spec(requestSpec)
    	.when()
    		.get("?dataType=rhrread&lang=en")
   		.then()
   			.body("$", hasKey("rainfall"))

   			.body("rainfall.data[0].unit", equalTo("mm"));
    }

    @Test(groups={"test"})
    public void isRainfallPlaceValid() {
        RestAssured.given()
        .relaxedHTTPSValidation()
    	    .spec(requestSpec)
    	.when()
    		.get("?dataType=rhrread&lang=en")
    	.then()
    		.body("$", hasKey("rainfall"))
    		.body("rainfall.data[0].place", any(String.class));
    }

    @Test(groups={"test"})
    public void isRainfallMaxValid() {
        RestAssured.given()
        .relaxedHTTPSValidation()
    	    .spec(requestSpec)
    	.when()
    		.get("?dataType=rhrread&lang=en")
    	.then()
    		.body("$", hasKey("rainfall"))
            .body("rainfall.data[0].max", any(Integer.class));
    }

        @Test(groups={"test"})
    public void isRainfallMinValid() {
        RestAssured.given()
        .relaxedHTTPSValidation()
    	    .spec(requestSpec)
    	.when()
    		.get("?dataType=rhrread&lang=en")
    	.then()
    		.body("$", hasKey("rainfall"))
            .body("rainfall.data[0].min", any(Integer.class));
    }
}