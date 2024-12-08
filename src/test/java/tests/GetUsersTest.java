package tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;

class GetUsersTest {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api/users";
    }

    @Test
    void successGetExistUsersPageTest() {
        var pageNumber = 2;
        var usersPerPage = 6;

        given()
                .param("page", pageNumber)
                .accept(JSON)
                .log().uri()
                .when()
                .get()
                .then()
                .log().status()
                .log().body()
                .assertThat().statusCode(200)
                .and().body("page", equalTo(pageNumber))
                .and().body("per_page", equalTo(usersPerPage))
                .and().body("data*.values().size()", equalTo(usersPerPage));
    }

    @Test
    void getUsersWithoutPageNumberTest() {
        var pageNumber = 1;

        given()
                .accept(JSON)
                .log().uri()
                .when()
                .get()
                .then()
                .log().status()
                .log().body()
                .assertThat().statusCode(200)
                .and().body("page", equalTo(pageNumber));
    }

    @Test
    void getUsersWithIncorrectPageNumberTest() {
        var pageNumber = 10001;

        given()
                .param("page", pageNumber)
                .accept(JSON)
                .log().uri()
                .when()
                .get()
                .then()
                .log().status()
                .log().body()
                .assertThat().statusCode(200)
                .and().body("page", equalTo(pageNumber))
                .and().body("data*.values()", empty());
    }
}
