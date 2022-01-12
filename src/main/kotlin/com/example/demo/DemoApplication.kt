package com.example.demo

import brave.baggage.BaggageField
import brave.handler.SpanHandler
import brave.http.HttpRequestParser
import org.slf4j.MDC
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@SpringBootApplication
class DemoApplication {

    @Bean
    fun sleuthHttpServerRequestParser() = HttpRequestParser { request, context, _ ->
        request.header("customHeader")
            ?.let { BaggageField.create("customField").updateValue(context, it) }
    }

    @Bean
    fun spanHandler(): SpanHandler = SpanHandler.NOOP

    @GetMapping("/mdc")
    fun getMdc(): Map<String, String> = MDC.getCopyOfContextMap()
}

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}
