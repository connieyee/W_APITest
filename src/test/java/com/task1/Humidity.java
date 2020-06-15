package com.connieyee.test.task1;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
                        hasEntry(equalTo("value"), lessThanOrEqualTo(100)),
                        hasEntry(equalTo("value"), greaterThanOrEqualTo(0))
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
    public void isHumidityRecordTimeValid() throws ParseException {
        Response rest = getApi();
        JsonPath path = JsonPath.from(rest.getBody().asInputStream());
        String recordTimeString = path.get("humidity.recordTime");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd'T'HH:mm:ssX");
        Date recordTime = format.parse(recordTimeString);
        assertThat("Record time is not valid", recordTime instanceof Date);

        //Check recordTime is earlier than current time
        assertThat("Record Time is not earlier than current time",recordTime.compareTo(new Date()) < 0);
    }
   
}