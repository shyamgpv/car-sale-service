package com.shyam.carsaleservice.controller;

import com.shyam.carsaleservice.entities.Car;
import com.shyam.carsaleservice.entities.CarListDTO;
import com.shyam.carsaleservice.response.ResponseHandler;
import com.shyam.carsaleservice.secuirity.MyCustomErrorDTO;
import com.shyam.carsaleservice.services.CarServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    Logger logger = LoggerFactory.getLogger(CarController.class);
    @Autowired
    private CarServices carServices;

    @GetMapping("/message")
    public String message(){
        return "Successful test 4";
    }
    @GetMapping("/user")
    public String userMessage(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); //get authentication details
        if(authentication != null){
            //log user name
            logger.info("User "+authentication.getName()+" logged in");
        }
        return "user logged in";
    }
    @GetMapping("/admin")
    public String adminMessage(){
        return "admin logged in";
    }
    @PostMapping("/addListing")
    public ResponseEntity<Object> addCar(@Valid @RequestBody Car car){
        logger.info("New Listing "+car.getYear()+" "+car.getMake()+" "+car.getCarModel()+" added");
        carServices.addCar(car);
        return ResponseHandler.generateResponse("Successfully added data!", HttpStatus.OK, null);
    }
    @PostMapping("/addListings")
    @ResponseBody
    public ResponseEntity<Object> addCars(@RequestBody List<Car> cars){
        System.out.println("Data valid "+cars.size());
        try {
            List res = carServices.addCars(cars);
            return ResponseHandler.generateResponse("Successfully added data!", HttpStatus.OK, res);
        }
        catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }


    @PostMapping("/updateListing/{carID}")
    public ResponseEntity<Object> updateCar(@PathVariable @Min(1) Long carID, @RequestBody Car car){
        logger.info(" Listing "+carID+" Updated");
        try{
         carServices.updateCar(carID,car);
         return ResponseHandler.generateResponse("Successfully updated Listing "+carID, HttpStatus.OK, null);
        }
        catch (Exception e) {
         return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @GetMapping("/getListing/{carID}")
    public ResponseEntity<Object> getCar(@PathVariable @Min(1) Long carID){

        try{
            Car result = carServices.getCar(carID);
            if(result != null)
                return ResponseHandler.generateResponse("Retrieved listing "+carID, HttpStatus.OK, result);
            else
                return ResponseHandler.generateResponse("Unable to retrieve data", HttpStatus.BAD_REQUEST, null);
        }
        catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @DeleteMapping("/deleteListing/{carID}")
    public ResponseEntity<Object> deleteCar(@PathVariable @Min(1) Long carID){

        try{
            String res =carServices.deleteCar(carID);
            return ResponseHandler.generateResponse("Successfully deleted Listing "+carID, HttpStatus.OK, null);
        }
        catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
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
