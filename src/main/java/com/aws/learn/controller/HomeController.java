package com.aws.learn.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String welcone() {
        return "Hello, welcome to ec2 instance running API";
    }
}
