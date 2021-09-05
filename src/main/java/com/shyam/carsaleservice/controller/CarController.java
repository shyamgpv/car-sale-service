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
        return "Successfull test 4";
    }

    @PostMapping("/addListing")
    public Car addCar(@RequestBody Car car){
        return carRepository.save(car);
    }

    @GetMapping("/getListing/{carID}")
    public Car getCar(@PathVariable Long carID){
        return carRepository.findCarById(carID);
    }
}
