package com.connieyee.test.task1;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.*;

import io.restassured.response.Response;

import static org.hamcrest.Matchers.*;

public class Lightning
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
    public void isLightningExist() {
        RestAssured.given()
        .relaxedHTTPSValidation()
    	    .spec(requestSpec)
    	.when()
    		.get("?dataType=rhrread&lang=en")
   		.then()
   			.body("$", hasKey("lightning"));
    }

    @Test(groups={"test"})
        public void isLightningNotExist() {
            RestAssured.given()
            .relaxedHTTPSValidation()
        	    .spec(requestSpec)
        	.when()
        		.get("?dataType=rhrread&lang=en")
       		.then()
       			.body("lightning", not(hasKey("lightning")));
        }
}