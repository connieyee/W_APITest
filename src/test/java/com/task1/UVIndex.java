package com.connieyee.test.task1;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.*;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;


public class UVIndex
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
    public void isUVIndexEmpty() {
        Response rest = getApi();
        rest.then()
    		.body("uvindex", isEmptyString());
    }


    @Test(groups={"test"})
    public void isUVIndexPlaceValid() {
        Response rest = getApi();
        rest.then()
    		.body("uvindex.data",
                hasItem(
                    allOf(
                        hasEntry(equalTo("place"), isA(String.class))
                    )
                )
            );
    }

    @Test(groups={"test"})
    public void isUVIndexValueValid() {
        Response rest = getApi();
        rest.then()
    		.body("uvindex.data",
                hasItem(
                    allOf(
                        hasEntry(equalTo("value"), isA(Integer.class))
                    )
                )
            );
    }

    @Test(groups={"test"})
    public void isUVIndexDescValid() {
        Response rest = getApi();
        rest.then()
    		.body("uvindex.data",
                hasItem(
                    allOf(
                        hasEntry(equalTo("desc"), isA(String.class))
                    )
                )
            );
    }

    @Test(groups={"test"})
    public void isUVIndexMessageExist() {
        Response rest = getApi();
        rest.then()
            .body("uvindex.data",
                hasItem(
                    allOf(
                        hasKey("message")
                    )
                )
            );
    }

    @Test(groups={"test"})
    public void isUVIndexMessageValid() {
        Response rest = getApi();
        rest.then()
    		.body("uvindex.data",
                hasItem(
                    allOf(
                        hasEntry(equalTo("message"), isA(String.class))
                    )
                )
            );
    }

    @Test(groups={"test"})
    public void isUVIndexRecordDescValid() {
        Response rest = getApi();
        rest.then()
    		.body("uvindex.recordDesc", isA(String.class));
    }
   
}