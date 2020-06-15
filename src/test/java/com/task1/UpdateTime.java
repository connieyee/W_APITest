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


public class iconUpdateTime
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
    public void isIconUpdateTimeValid() throws ParseException {
        Response rest = getApi();
        JsonPath path = JsonPath.from(rest.getBody().asInputStream());
        String iconUpdateTimeString = path.get("iconUpdateTime");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd'T'HH:mm:ssX");
        Date iconUpdateTime = format.parse(iconUpdateTimeString);
        assertThat("Icon Update time is not valid", iconUpdateTime instanceof Date);

        //Check iconUpdateTime is earlier than current time
        assertThat((iconUpdateTime.compareTo(new Date()) < 0), is(true));
    }

}