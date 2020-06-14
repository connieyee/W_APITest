package com.connieyee.test.task1;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.*;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;


public class RainFall
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
    public void isRainfallUnitValid() {
        Response rest = getApi();
        rest.then()
   			// .body("$", hasKey("rainfall"))
   			.body("rainfall.data",
                hasItem(
                    allOf(
                        hasEntry("unit", "mm")
                    )
                )
            );
    }

    @Test(groups={"test"})
    public void isRainfallPlaceValid() {
        Response rest = getApi();
        rest.then()
    		// .body("$", hasKey("rainfall"))
    		.body("rainfall.data",
                hasItem(
                    allOf(
                        hasEntry("place", isA(String.class))
                    )
                )
            );
    }

    @Test(groups={"test"})
    public void isRainfallMaxExist() {
        Response rest = getApi();
        rest.then()
    		// .body("$", hasKey("rainfall"))
            .body("rainfall.data[0]", hasKey("max"));
    }

    @Test(groups={"test"})
    public void isRainfallMaxValid() {
        Response rest = getApi();
        rest.then()
    		// .body("$", hasKey("rainfall"))
            .body("rainfall.data[0].max", any(Integer.class));
    }

    @Test(groups={"test"})
    public void isRainfallMinExist() {
        Response rest = getApi();
        rest.then()
    		// .body("$", hasKey("rainfall"))
            .body("$", hasKey("rainfall.data[0].min"));
    }
    

    @Test(groups={"test"})
    public void isRainfallMinValid() {
        Response rest = getApi();
        rest.then()
    		// .body("$", hasKey("rainfall"))
            .body("rainfall.data[0].min", any(Integer.class));
    }

    @Test(groups={"test"})
    public void isRainfallMainValid() {
        Response rest = getApi();
        rest.then()
    		// .body("$", hasKey("rainfall"))
            .body("rainfall.data[0].main", anyOf(equalTo("TRUE"), equalTo("FALSE")));
    }

    @Test(groups={"test"})
    public void isRainfallStartTimeValid() {
        Response rest = getApi();
        rest.then()
    		.body("$", hasKey("rainfall"));
            // .body("rainfall.statTime", matchesPattern(^(-?(?:[1-9][0-9]*)?[0-9]{4})-(1[0-2]|0[1-9])-(3[01]|0[1-9]|[12][0-9])T(2[0-3]|[01][0-9]):([0-5][0-9]):([0-5][0-9])(\\.[0-9]+)?(Z)?$));
    }

}