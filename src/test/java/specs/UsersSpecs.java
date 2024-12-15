package specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.AllureHelper.withAllureListener;
import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;

public class UsersSpecs {

    public static RequestSpecification getRequestSpecification() {
        return new RequestSpecBuilder()
                .addFilter(withAllureListener())
                .log(ALL)
                .build();
    }

    public static ResponseSpecification getResponseSpecification() {
        return new ResponseSpecBuilder()
                .log(ALL)
                .expectContentType(JSON)
                .build();
    }
}
