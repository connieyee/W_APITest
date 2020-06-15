package com.connieyee.test.task1;

import java.sql.Timestamp;

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
    public void isLightningStartTimeValid() {
        // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd'T'HH:mm:ss.SSSZ");
        // String date = simpleDateFormat.format("lightning.data");

        Response rest = getApi();
        rest.then()
    		.body("lightning.startTime", 
                instanceOf(Timestamp.class)
            );
    }

    // @Test(groups={"test"})
    // public void isLightningEndTimeValid() {
    //     Response rest = getApi();
    //     rest.then()
    //             .body("lightning.endTime", matchesPattern(^(-?(?:[1-9][0-9]*)?[0-9]{4})-(1[0-2]|0[1-9])-(3[01]|0[1-9]|[12][0-9])T(2[0-3]|[01][0-9]):([0-5][0-9]):([0-5][0-9])(\\.[0-9]+)?([+/-]\d\d):?(\d\d)?$));?
    // }
}