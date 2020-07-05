package com.smalwe.controller;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/customer")
public class CustomerController {
    @Get(produces = MediaType.TEXT_PLAIN)
    public String customer() {
        return "Customer Returned";
    }
}
