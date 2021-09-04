package com.shyam.carsaleservice.controller;

import com.shyam.carsaleservice.entities.Car;
import com.shyam.carsaleservice.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/car")
public class CarController {
    @Autowired
    private CarRepository carRepository;

    @GetMapping("/message")
    public String message(){
        return "Successfull test 2";
    }

    @PostMapping("/addListing")
    public Car addCar(@RequestBody Car car){
        return carRepository.save(car);
    }

    @PostMapping("/getListing")
    public Car getCar(@RequestBody Long carID){
        return carRepository.getById(carID);
    }
}