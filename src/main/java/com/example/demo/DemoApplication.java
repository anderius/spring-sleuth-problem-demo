package com.example.demo;

import brave.baggage.BaggageField;
import brave.handler.SpanHandler;
import brave.http.HttpRequestParser;
import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@RestController
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    HttpRequestParser sleuthHttpServerRequestParser() {
        return (request, context, span) -> {
            String value = Objects.requireNonNullElse(request.header("customHeader"), "Header was not set!");
            BaggageField.create("customField").updateValue(context, value);
        };
    }

    @Bean
    SpanHandler spanHandler() {
        return SpanHandler.NOOP;
    }

    @GetMapping("/mdc")
    Map<String, String> getMdc() {
        return MDC.getCopyOfContextMap();
    }
}
