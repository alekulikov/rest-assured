package tests;

import io.qameta.allure.*;
import models.UpdateUserRequest;
import models.UpdateUserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.UsersSpecs.getRequestSpecification;
import static specs.UsersSpecs.getResponseSpecification;

@Feature("Обновление данных пользователя")
@Owner("alekulikov")
@Link(value = "Testing", url = "https://github.com/alekulikov/rest-assured")
class PutUsersTest extends TestBase {

    @Severity(SeverityLevel.BLOCKER)
    @Tags({
            @Tag("SMOKE"),
            @Tag("API")
    })
    @Test
    @DisplayName("Успешное обновление")
    void successUpdateUserTest() {
        var userId = 2;
        var requestBody = new UpdateUserRequest("morpheus", "zion resident");
        var currentDateTime = LocalDateTime.now(ZoneOffset.UTC);

        var response = step("Сделать запрос", () -> given()
                .spec(getRequestSpecification())
                .pathParam("id", userId)
                .body(requestBody)
                .when()
                .put("/user/{id}")
                .then()
                .spec(getResponseSpecification())
                .statusCode(200)
                .and().extract().as(UpdateUserResponse.class));

        step("Проверить ответ", () -> assertThat(response.getUpdatedAt())
                .isAfter(currentDateTime));
    }

    @Severity(SeverityLevel.CRITICAL)
    @Tag("API")
    @Test
    @DisplayName("Успешное обновление с пустым телом запроса")
    void updateUserWithoutBodyTest() {
        var userId = 2;
        var currentDateTime = LocalDateTime.now(ZoneOffset.UTC);

        var response = step("Сделать запрос", () -> given()
                .spec(getRequestSpecification())
                .pathParam("id", userId)
                .when()
                .put("/user/{id}")
                .then()
                .spec(getResponseSpecification())
                .statusCode(200)
                .and().extract().as(UpdateUserResponse.class));

        step("Проверить ответ", () -> assertThat(response.getUpdatedAt())
                .isAfter(currentDateTime));
    }
}
