package com.produo.urlcatter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Calendar;

@RestController
public class ExampleController {
    @GetMapping("/example")
    public String index() {
        return "Greetings from Spring Boot!";
    }
}
