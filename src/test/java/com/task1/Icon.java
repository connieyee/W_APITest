package com.connieyee.test.task1;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
        JsonPath path = JsonPath.from(rest.getBody().asInputStream());
        List<Integer> icons = path.get("icon");
        List<Integer> acceptableIcons = Arrays.asList(50,51,52,53,54,60,61,62,63,64,65,70,71,72,73,74,75,76,77,80,81,82,83,84,85,90,91,92,93);

       assertThat("Icon is not valid", acceptableIcons.containsAll(icons));

    }

}