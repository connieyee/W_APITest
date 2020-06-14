package com.connieyee.test.task1;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.*;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;


public class Icon
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
    public void isIconValid() {
        // WIP
        Response rest = getApi();
        rest.then()
    		.body("icon",
                hasItem(
                    anyOf(
                        allOf(
                            hasEntry(equalTo("value"), greaterThanOrEqualTo(50)),
                            hasEntry(equalTo("value"), lessThanOrEqualTo(54))
                        ),
                        allOf(
                            hasEntry(equalTo("value"), greaterThanOrEqualTo(60)),
                            hasEntry(equalTo("value"), lessThanOrEqualTo(65))
                        )
                        allOf(
                            hasEntry(equalTo("value"), greaterThanOrEqualTo(70)),
                            hasEntry(equalTo("value"), lessThanOrEqualTo(77))
                        ),
                        allOf(
                            hasEntry(equalTo("value"), greaterThanOrEqualTo(80)),
                            hasEntry(equalTo("value"), lessThanOrEqualTo(85))
                        ),
                        allOf(
                            hasEntry(equalTo("value"), greaterThanOrEqualTo(90)),
                            hasEntry(equalTo("value"), lessThanOrEqualTo(93))
                        )
                    )
                )
            );
    }

}