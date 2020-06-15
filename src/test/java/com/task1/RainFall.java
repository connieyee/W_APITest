package com.connieyee.test.task1;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import io.restassured.path.json.JsonPath;
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
    		.body("rainfall.data",
                hasItem(
                    allOf(
                        hasEntry(equalTo("place"), isA(String.class))
                    )
                )
            );
    }

    @Test(groups={"test"})
    public void isRainfallMaxExist() {
        Response rest = getApi();
        rest.then()
            .body("rainfall.data",
                hasItem(
                    allOf(
                        hasKey("max")
                    )
                )
            );
    }

    @Test(groups={"test"})
    public void isRainfallMaxValid() {
        Response rest = getApi();
        rest.then()
    		.body("rainfall.data",
                hasItem(
                    allOf(
                        hasEntry(equalTo("max"), isA(Integer.class))
                    )
                )
            );
    }

    @Test(groups={"test"})
    public void isRainfallMinExist() {
        Response rest = getApi();
        rest.then()
            .body("rainfall.data",
                hasItem(
                    allOf(
                        hasKey("min")
                    )
                )
            );
    }

    @Test(groups={"test"})
    public void isRainfallMinValid() {
        Response rest = getApi();
        rest.then()
    		.body("rainfall.data",
                hasItem(
                    allOf(
                        hasEntry(equalTo("min"), isA(Integer.class))
                    )
                )
            );
    }

    @Test(groups={"test"})
    public void isRainfallMainValid() {
        Response rest = getApi();
        rest.then()
            .body("rainfall.data[0].main", anyOf(equalTo("TRUE"), equalTo("FALSE")));
    }

    @Test(groups={"test"})
    public void isRainfallStartTimeValid() throws ParseException {
        Response rest = getApi();
        JsonPath path = JsonPath.from(rest.getBody().asInputStream());
        String startTimeString = path.get("rainfall.startTime");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd'T'HH:mm:ssX");
        Date startTime = format.parse(startTimeString);
        assertThat("Start time is not valid", startTime instanceof Date);
    }

    @Test(groups={"test"})
    public void isRainfallEndTimeValid() throws ParseException {
        Response rest = getApi();
        JsonPath path = JsonPath.from(rest.getBody().asInputStream());
        String endTimeString = path.get("rainfall.endTime");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd'T'HH:mm:ssX");
        Date endTime = format.parse(endTimeString);
        assertThat("End time is not valid", endTime instanceof Date);
    }

}