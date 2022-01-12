package com.example.demo

import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.core.IsEqual
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

    @LocalServerPort
    private var port: Int = 0

    @Test
    fun `single test`() {
        `assert MDC is set`("some value")
    }

    @Test
    fun `many tests`() {
        for (i in 1..100) {
            `assert MDC is set`("test number $i")
        }
    }

    fun `assert MDC is set`(value: String) {
        Given {
            header("customHeader", value)
        } When {
            get("http://localhost:$port/mdc")
        } Then {
            statusCode(200)
            body("customField", IsEqual(value))
        }
    }

}
