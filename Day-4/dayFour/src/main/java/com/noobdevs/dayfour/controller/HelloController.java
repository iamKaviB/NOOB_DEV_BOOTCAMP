package com.noobdevs.dayfour.controller;

import com.noobdevs.dayfour.model.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String sayHello(){
        return "Hello noobs";
    }

    @GetMapping("/sample-product")
    public Product getSampleProduct(){
        return new Product(101,"Laptop",150000.00);
    }
}
