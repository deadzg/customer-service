package com.smalwe.controller;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/customer")
public class CustomerController {
    @Get(produces = MediaType.TEXT_PLAIN)
    public String customer() {
        float y = 12.3f;
        double z = 12;
        return "Customer Returned";
    }
}
