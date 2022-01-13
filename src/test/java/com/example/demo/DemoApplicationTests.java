package com.example.demo;

import io.restassured.RestAssured;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

    @LocalServerPort
    private int port;

    @Test
    void singleTest() {
        assert_mdc_is_set("some value");
    }

    @Test
    void manyTests() {
        for (int i = 0; i < 100; i++) {
            assert_mdc_is_set("test number " + i);
        }
    }

    private void assert_mdc_is_set(String value) {
        RestAssured.given()
                .header("customHeader", value)
                .when()
                .get("http://localhost:" + port + "/mdc")
                .then()
                .statusCode(200)
                .body("customField", IsEqual.equalTo(value));
    }
}
