package com.connieyee.test.task1;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.*;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;


public class MessageOfTropicalCyclonePosition
{
    
    private static RequestSpecification requestSpec;

    @BeforeClass(groups={"test"})
    public static void createRequestSpecification() {
    	requestSpec = new RequestSpecBuilder()
    		.setBaseUri("https://data.weather.gov.hk/weatherAPI/opendata/weather.php")
    		.build();
    }

    private Response getApi() {
        return RestAssured.given()
            .relaxedHTTPSValidation()
    	       .spec(requestSpec)
    	    .when()
    	    	.get("?dataType=rhrread&lang=en");
    }


    @Test(groups={"test"})
    public void isMessageOfTropicalCyclonePositionExist() {
        Response rest = getApi();
        rest.then()
            .body("$", hasKey("tcmessage"));
    }

    @Test(groups={"test"})
    public void isMessageOfTropicalCyclonePositionValid() {
        Response rest = getApi();
        rest.then()
    		.body("tcmessage", 
                anyOf(
                    isEmptyString(),
                    hasItem(
                        allOf(
                            isA(String.class)
                        )
                    )
                )
            );
    }

}