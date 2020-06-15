package com.connieyee.test.task1;

import java.sql.Timestamp;
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

public class Lightning
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
    public void isLightningExist() {
        Response rest = getApi();
        rest.then()
   			.body("$", hasKey("lightning"));
    }

    @Test(groups={"test"})
    public void isLightningPlaceValid() {
        Response rest = getApi();
        rest.then()
    		.body("lightning.data",
                hasItem(
                    allOf(
                        hasEntry(equalTo("place"), isA(String.class))
                    )
                )
            );
    }

    @Test(groups={"test"})
    public void isLightningOccurValid() {
        Response rest = getApi();
        rest.then()
    		.body("lightning.data",
                hasItem(
                    allOf(
                        hasEntry("occur", "true")
                    )
                )
            );
    }

    @Test(groups={"test"})
    public void isLightningStartTimeValid() throws ParseException {
        Response rest = getApi();
        JsonPath path = JsonPath.from(rest.getBody().asInputStream());
        String startTimeString = path.get("lightning.startTime");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd'T'HH:mm:ssX");
        Date startTime = format.parse(startTimeString);
        assertThat("Start time is not valid", startTime instanceof Date);
    }

     @Test(groups={"test"})
     public void isLightningEndTimeValid() throws ParseException {
         Response rest = getApi();
         JsonPath path = JsonPath.from(rest.getBody().asInputStream());
         String endTimeString = path.get("lightning.endTime");
         SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd'T'HH:mm:ssX");
         Date endTime = format.parse(endTimeString);
         assertThat("End time is not valid", endTime instanceof Date);
     }
}