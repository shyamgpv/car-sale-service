package com.shyam.carsaleservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CarSaleServiceApplication {

    @GetMapping("/message")
    public String message(){
        return "Successfull and Changed";
    }
    public static void main(String[] args) {
        SpringApplication.run(CarSaleServiceApplication.class, args);
    }

}
