package tests;

import io.qameta.allure.*;
import models.GetUsersResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;
import static specs.UsersSpecs.getRequestSpecification;
import static specs.UsersSpecs.getResponseSpecification;

@Feature("Получение списка пользователей")
@Owner("alekulikov")
@Link(value = "Testing", url = "https://github.com/alekulikov/rest-assured")
class GetUsersTest extends TestBase {

    @Severity(SeverityLevel.BLOCKER)
    @Tags({
            @Tag("SMOKE"),
            @Tag("API")
    })
    @Test
    @DisplayName("Успешное получение страницы с пользователями")
    void successGetExistUsersPageTest() {
        var pageNumber = 2;
        var usersPerPage = 6;

        var response = step("Сделать запрос", () -> given()
                .spec(getRequestSpecification())
                .param("page", pageNumber)
                .when()
                .get("/users")
                .then()
                .spec(getResponseSpecification())
                .statusCode(200)
                .and().extract().as(GetUsersResponse.class));

        step("Проверить ответ", () -> assertThat(response)
                .hasFieldOrPropertyWithValue("page", pageNumber)
                .hasFieldOrPropertyWithValue("perPage", usersPerPage)
                .extracting(GetUsersResponse::getData)
                .asInstanceOf(LIST)
                .hasSize(usersPerPage));

    }

    @Severity(SeverityLevel.NORMAL)
    @Tag("API")
    @Test
    @DisplayName("Успешное получение первой страницы пользователей, если иное не указано в запросе")
    void getUsersWithoutPageNumberTest() {
        var pageNumber = 1;

        var response = step("Сделать запрос", () -> given()
                .spec(getRequestSpecification())
                .when()
                .get("/users")
                .then()
                .spec(getResponseSpecification())
                .statusCode(200)
                .and().extract().as(GetUsersResponse.class));

        step("Проверить ответ", () -> assertThat(response)
                .hasFieldOrPropertyWithValue("page", pageNumber));
    }

    @Severity(SeverityLevel.BLOCKER)
    @Tags({
            @Tag("SMOKE"),
            @Tag("API")
    })
    @Test
    @DisplayName("Ошибка при попытке получения несуществующей страницы с пользователями")
    void getUsersWithIncorrectPageNumberTest() {
        var pageNumber = 10001;

        var response = step("Сделать запрос", () -> given()
                .spec(getRequestSpecification())
                .param("page", pageNumber)
                .when()
                .get("/users")
                .then()
                .spec(getResponseSpecification())
                .statusCode(200)
                .and().extract().as(GetUsersResponse.class));

        step("Проверить ответ", () -> assertThat(response)
                .hasFieldOrPropertyWithValue("page", pageNumber)
                .extracting(GetUsersResponse::getData)
                .asInstanceOf(LIST)
                .isEmpty());
    }
}
