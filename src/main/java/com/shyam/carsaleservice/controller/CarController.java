package com.shyam.carsaleservice.controller;

import com.shyam.carsaleservice.entities.Car;
import com.shyam.carsaleservice.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping(value="/getListings",params = "make")
    public List getCarByMake(@RequestParam("make") String make){
        return carRepository.getCarByMake(make);
    }
    @GetMapping(value="/getListings",params = "model")
    public List getCarByCarModel(@RequestParam("model") String carModel){
        return carRepository.getCarByCarModel(carModel);
    }
    @GetMapping(value="/getListings",params = "registration")
    public List getCarByRegistration(@RequestParam("registration") String registration){
        return carRepository.getCarByRegistration(registration);
    }
    @GetMapping(value="/getListings",params = "colour")
    public List getCarByColour(@RequestParam("colour") String colour){
        return carRepository.getCarByColour(colour);
    }
    @GetMapping(value="/getListings",params = "year")
    public List getCarByYear(@RequestParam("year") Integer year){
        return carRepository.getCarByYear(year);
    }
    @GetMapping(value="/getListings",params = {"fromPrice","toPrice"})
    public List getCarByPriceRange(@RequestParam("fromPrice") Double fromPrice,@RequestParam("toPrice") Double toPrice){
        return carRepository.getCarByPriceBetween(fromPrice,toPrice);
    }
    @GetMapping(value="/getListings",params = {"fromYear","toYear"})
    public List getCarByYearRange(@RequestParam("fromYear") Integer fromYear,@RequestParam("toYear") Integer toYear){
        return carRepository.getCarByYearBetween(fromYear,toYear);
    }
}
