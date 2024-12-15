package specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.AllureHelper.withAllureListener;
import static io.restassured.filter.log.LogDetail.ALL;

public class BaseSpecs {

    public static final String BASE_URL = "https://reqres.in";

    protected RequestSpecification getRequestSpecification() {
        return new RequestSpecBuilder()
                .addFilter(withAllureListener())
                .log(ALL)
                .setBaseUri(BASE_URL)
                .build();
    }

    protected ResponseSpecification getResponseSpecification() {
        return new ResponseSpecBuilder()
                .log(ALL)
                .build();
    }
}
