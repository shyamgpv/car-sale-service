package com.shyam.carsaleservice.controller;

import com.shyam.carsaleservice.entities.Car;
import com.shyam.carsaleservice.secuirity.MyCustomErrorDTO;
import com.shyam.carsaleservice.services.CarServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@RestController
@Validated
@RequestMapping("/car")
public class CarController {
    @Autowired
    private CarServices carServices;

    @GetMapping("/message")
    public String message(){
        return "Successful test 4";
    }
    @GetMapping("/user")
    public String userMessage(){
        return "user logged in";
    }
    @GetMapping("/admin")
    public String adminMessage(){
        return "admin logged in";
    }
    @PostMapping("/addListing")
    public Car addCar(@Valid @RequestBody Car car){
        return carServices.addCar(car);
    }
    @PostMapping("/addListings")
    public MyCustomErrorDTO addCars(@Valid @RequestBody ArrayList<Car> cars){
        return carServices.addCars(cars);
    }
    @PostMapping("/updateListing/{carID}")
    public Car updateCar(@PathVariable @Min(1) Long carID, @RequestBody Car car){
        return carServices.updateCar(carID,car);
    }

    @GetMapping("/getListing/{carID}")
    public Car getCar(@PathVariable @Min(1) Long carID){
        return carServices.getCar(carID);
    }
    @DeleteMapping("/deleteListing/{carID}")
    public String deleteCar(@PathVariable @Min(1) Long carID){
        return carServices.deleteCar(carID);
    }

    @GetMapping(value="/getListings",params = "make")
    public List getCarByMake(@RequestParam("make") String make){
        return carServices.getCarByMake(make);
    }

    @GetMapping(value="/getListings",params = "model")
    public List getCarByCarModel(@RequestParam("model") String carModel){
        return carServices.getCarByCarModel(carModel);
    }

    @GetMapping(value="/getListings",params = "registration")
    public List getCarByRegistration(@RequestParam("registration") String registration){
        return carServices.getCarByRegistration(registration);
    }

    @GetMapping(value="/getListings",params = "colour")
    public List getCarByColour(@RequestParam("colour") String colour){
        return carServices.getCarByColour(colour);
    }

    @GetMapping(value="/getListings",params = "year")
    public List getCarByYear(@RequestParam("year") Integer year){
        return carServices.getCarByYear(year);
    }

    @GetMapping(value="/getListings",params = {"fromPrice","toPrice"})
    public List getCarByPriceRange(@RequestParam("fromPrice") Double fromPrice,@RequestParam("toPrice") Double toPrice){
        return carServices.getCarByPriceBetween(fromPrice,toPrice);
    }

    @GetMapping(value="/getListings",params = {"fromYear","toYear"})
    public List getCarByYearRange(@RequestParam("fromYear") Integer fromYear,@RequestParam("toYear") Integer toYear){
        return carServices.getCarByYearBetween(fromYear,toYear);
    }
}
