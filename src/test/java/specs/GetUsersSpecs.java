package specs;

import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.http.ContentType.JSON;

public class GetUsersSpecs extends BaseSpecs {

    public static final String BASE_PATH = "/api/users";

    @Override
    public RequestSpecification getRequestSpecification() {
        return super.getRequestSpecification()
                .basePath(BASE_PATH)
                .contentType(JSON)
                .accept(JSON);
    }

    @Override
    public ResponseSpecification getResponseSpecification() {
        return super.getResponseSpecification()
                .contentType(JSON)
                .statusCode(200);
    }
}
