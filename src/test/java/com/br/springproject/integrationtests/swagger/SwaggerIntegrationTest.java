package com.br.springproject.integrationtests.swagger;

import com.br.springproject.config.TestConfig;
import com.br.springproject.integrationtests.testcontainer.AbstractIntegrationTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void shouldDisplaySwaggerUiPage() {
        String content =
                RestAssured.given().basePath("/swagger-ui/index.html")
                        .port(TestConfig.SERVER_PORT)
                        .when()
                        .get()
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .asString();
        Assertions.assertTrue(content.contains("Swagger UI"));
    }

}
