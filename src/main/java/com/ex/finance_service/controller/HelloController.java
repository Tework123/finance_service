package com.ex.finance_service.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello")
    public String sayHello(@RequestParam(defaultValue = "World") String name) {
        return "Hello, " + name + "!";
    }

    @PostMapping("/echo")
    public String echo(@RequestBody String body) {
        return "You said: " + body;
    }
}