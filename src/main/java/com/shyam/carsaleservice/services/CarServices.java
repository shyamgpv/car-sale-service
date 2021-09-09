package com.shyam.carsaleservice.services;

import com.shyam.carsaleservice.entities.Car;
import com.shyam.carsaleservice.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;
import java.util.List;

@Service
public class CarServices {
    @Autowired
    private CarRepository carRepository;
    public Car addCar(Car car){
        return carRepository.save(car);
    }
    public Car updateCar(Long carID,Car car){
        Car existingCar = getCar(carID);
        try { // conditions added to prevent values going empty
            if(car.getRegistration() != null && car.getRegistration().length() != 0)
                existingCar.setRegistration(car.getRegistration());
            if(car.getYear() != null && car.getYear() >= 1900 && car.getYear() <= 2021)
                existingCar.setYear(car.getYear());
            if(car.getMake() != null && car.getMake().length() != 0)
                existingCar.setMake(car.getMake());
            if(car.getCarModel() != null && car.getCarModel().length() != 0)
                existingCar.setCarModel(car.getCarModel());
            if(car.getColour() != null && car.getColour().length() != 0)
                existingCar.setColour(car.getColour());
            if(car.getPrice() != null && car.getPrice() > 0)
                existingCar.setPrice(car.getPrice());
        } catch(Exception e){
            throw new NullPointerException(e.getMessage());
        }
        return carRepository.save(existingCar);
    }
    public List getCarByCarModel(String carModel){
        return carRepository.getCarByCarModel(carModel);

    }
    public List getCarByMake(String make) {
        return carRepository.getCarByMake(make);
    }
    public List getCarByYear(Integer year) {
        return carRepository.getCarByYear(year);
    }
    public List getCarByYearBetween(Integer startYear,Integer endYear) {
        return carRepository.getCarByYearBetween(startYear,endYear);
    }
    public List getCarByColour(String colour) {
        return carRepository.getCarByColour(colour);
    }
    public List getCarByPriceBetween(double startPrice,double endPrice) {
        return carRepository.getCarByPriceBetween(startPrice,endPrice);
    }
    public List getCarByRegistration(String registration) {
        return carRepository.getCarByRegistration(registration);
    }
    public Car getCar(Long carID){

        return carRepository.findCarById(carID);
    }

    public String deleteCar(Long carID){

        carRepository.deleteById(carID);
        return "Lisitng deleted";
    }


}
