package com.in_memory.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/private")
public class PrivateController {

    @GetMapping
    public String helloWorldPrivate(){
        return "Hello World ! from private endpoint";
    }

    @GetMapping("/admin")
    public String admin(){
        return "Hello World! from admin private endpoint";
    }

    @GetMapping("/user")
    public String user(){
        return "Hello World! from user private endpoint";
    }

}
