package com.connieyee.test.task1;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.*;

import io.restassured.response.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class UpdateTime
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
    public void isUpdateTimeValid() throws ParseException {
        Response rest = getApi();
        JsonPath path = JsonPath.from(rest.getBody().asInputStream());
        String updateTimeString = path.get("updateTime");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd'T'HH:mm:ssX");
        Date updateTime = format.parse(updateTimeString);
        assertThat("Update time is not in valid time format", updateTime instanceof Date);

        //Check UpdateTime is earlier than current time
        assertThat("Update Time is not earlier than current time",updateTime.compareTo(new Date()) < 0);
    }

}