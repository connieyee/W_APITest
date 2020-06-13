package com.connieyee.test.task1;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.*;

import static org.hamcrest.Matchers.*;

import java.util.regex.Matcher;

public class General
{
    private static RequestSpecification requestSpec;

    @BeforeClass(groups={"test"})
    public static void createRequestSpecification() {
    	requestSpec = new RequestSpecBuilder().
    		setBaseUri("https://data.weather.gov.hk/weatherAPI/opendata/weather.php").
    		build();
    }

    @Test(groups={"test"})
    public void verifyCorrectHTTPStatusCode() {
        RestAssured.given().
        relaxedHTTPSValidation().
    	    spec(requestSpec).
    	when().
    		get("?dataType=rhrread&lang=en").
   		then().
   			assertThat().
   			statusCode(200);
    }

    @Test(groups={"test"})
    public void verifyResponsePayload() {
        RestAssured.given().
        relaxedHTTPSValidation().
    	    spec(requestSpec).
    	when().
    		get("?dataType=rhrread&lang=en").
   		then().
   		body("isEmpty()", equalTo(false));
    }

    @Test(groups={"test"})
    public void verifyResponseHeader() {
        Response response = RestAssured.given().
        relaxedHTTPSValidation().
    	    spec(requestSpec).
    	when().
    		get("?dataType=rhrread&lang=en");

    	// TODO: CHECK headers logic
    }

}