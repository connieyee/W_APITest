package com.connieyee.test.task1;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.*;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;


public class Humidity
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
    public void isHumidityUnitValid() {
        Response rest = getApi();
        rest.then()
   			.body("humidity.data",
                hasItem(
                    allOf(
                        hasEntry("unit", "percent")
                    )
                )
            );
    }

    @Test(groups={"test"})
    public void isHumidityValueValid() {
        Response rest = getApi();
        rest.then()
    		.body("humidity.data",
                hasItem(
                    allOf(
                        hasEntry(equalTo("value"), isA(Integer.class))
                    )
                )
            );
    }

    @Test(groups={"test"})
    public void isHumidityPlaceValid() {
        Response rest = getApi();
        rest.then()
    		.body("humidity.data",
                hasItem(
                    allOf(
                        hasEntry(equalTo("place"), isA(String.class))
                    )
                )
            );
    }

    @Test(groups={"test"})
    public void isHumidityRecordTimeValid() {
        Response rest = getApi();
        rest.then()
            .body("$", hasKey("humidity"));
    		// .body("humidity.recordTime", matchesPattern(^(-?(?:[1-9][0-9]*)?[0-9]{4})-(1[0-2]|0[1-9])-(3[01]|0[1-9]|[12][0-9])T(2[0-3]|[01][0-9]):([0-5][0-9]):([0-5][0-9])(\\.[0-9]+)?(Z)?$));
    }
   
}