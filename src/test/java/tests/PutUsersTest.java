package tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

class PutUsersTest {

    public static final DateTimeFormatter UPDATE_AT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api/users/{id}";
    }

    @Test
    void successUpdateUserTest() {
        var userId = 2;
        var requestBody = """
                {
                    "name": "morpheus",
                    "job": "zion resident"
                }
                """;
        var currentDateTime = LocalDateTime.now(ZoneOffset.UTC);

        var response = given()
                .pathParam("id", userId)
                .contentType(JSON)
                .accept(JSON)
                .body(requestBody)
                .log().uri()
                .log().body()
                .when()
                .put()
                .then()
                .log().status()
                .log().body()
                .assertThat().statusCode(200)
                .and().extract().jsonPath().getString("updatedAt");

        assertThat(LocalDateTime.parse(response, UPDATE_AT_FORMAT),
                greaterThanOrEqualTo(currentDateTime));
    }

    @Test
    void updateUserWithoutBodyTest() {
        var userId = 2;
        var currentDateTime = LocalDateTime.now(ZoneOffset.UTC);

        var response = given()
                .pathParam("id", userId)
                .accept(JSON)
                .log().uri()
                .log().body()
                .when()
                .put()
                .then()
                .log().status()
                .log().body()
                .assertThat().statusCode(200)
                .and().extract().jsonPath().getString("updatedAt");

        assertThat(LocalDateTime.parse(response, UPDATE_AT_FORMAT),
                greaterThanOrEqualTo(currentDateTime));
    }
}
