package com.shyam.carsaleservice.controller;

import com.shyam.carsaleservice.entities.Car;
import com.shyam.carsaleservice.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/car")
public class CarController {
    @Autowired
    private CarRepository carRepository;

    @PostMapping("/addListing")
    public Car addCar(@RequestBody Car car){
        return carRepository.save(car);
    }

    @PostMapping("/getListing")
    public Car getCar(@RequestBody Long carID){
        return carRepository.getById(carID);
    }
}
