package com.shyam.carsaleservice.services;

import com.shyam.carsaleservice.entities.Car;
import com.shyam.carsaleservice.repository.CarRepository;
import com.shyam.carsaleservice.secuirity.SecurityEscape;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//Service/Buisness layer

@Service
public class CarServices {
    @Autowired
    private CarRepository carRepository;

    public Car addCar(Car car){
        //process and standardize input values
        Car cleanCar = new Car();

        cleanCar.setMake(SecurityEscape.cleanIt(car.getMake()));
        cleanCar.setCarModel(SecurityEscape.cleanIt(car.getCarModel()));
        cleanCar.setColour(SecurityEscape.cleanIt(car.getColour()));
        cleanCar.setRegistration(SecurityEscape.cleanIt(car.getRegistration()));
        cleanCar.setPrice(SecurityEscape.cleanDouble(car.getPrice()));
        cleanCar.setYear(SecurityEscape.cleanInt(car.getYear()));
        try {
            return carRepository.save(cleanCar);
        }catch (Exception e){
            throw e;
        }
    }

    public Car updateCar(Long carID,Car car){
        Car existingCar;
        if(carRepository.existsById(carID))
         existingCar = getCar(carID);
        else
            throw new NullPointerException("Listing doesnot exist");
        try { // conditions added to prevent values going empty
            if(car.getRegistration() != null && car.getRegistration().length() != 0)
                existingCar.setRegistration(SecurityEscape.cleanIt(car.getRegistration()));

            if(car.getYear() != null && car.getYear() >= 1900 && car.getYear() <= 2021)
                existingCar.setYear(SecurityEscape.cleanInt(car.getYear()));

            if(car.getMake() != null && car.getMake().length() != 0)
                existingCar.setMake(SecurityEscape.cleanIt(car.getMake()));

            if(car.getCarModel() != null && car.getCarModel().length() != 0)
                existingCar.setCarModel(SecurityEscape.cleanIt(car.getCarModel()));

            if(car.getColour() != null && car.getColour().length() != 0)
                existingCar.setColour(SecurityEscape.cleanIt(car.getColour()));

            if(car.getPrice() != null && car.getPrice() > 0)
                existingCar.setPrice(SecurityEscape.cleanDouble(car.getPrice()));

        } catch(Exception e){
            throw new NullPointerException(e.getMessage());
        }
       try {
           return carRepository.save(existingCar);
        }catch (Exception e){
            throw e;
        }
    }

    public List getCarByCarModel(String carModel){
      try {
          return carRepository.getCarByCarModel(SecurityEscape.cleanIt(carModel));
        }catch (Exception e){
            throw e;
        }

    }

    public List getCarByMake(String make) {
      try {
          return carRepository.getCarByMake(SecurityEscape.cleanIt(make));
        }catch (Exception e){
            throw e;
        }
    }

    public List getCarByYear(Integer year) {
      try {
          return carRepository.getCarByYear(SecurityEscape.cleanInt(year));
      }catch (Exception e){
            throw e;
        }
    }

    public List getCarByYearBetween(Integer startYear,Integer endYear) {
       try {
           return carRepository.getCarByYearBetween(SecurityEscape.cleanInt(startYear),SecurityEscape.cleanInt(endYear));
       }catch (Exception e){
            throw e;
        }
    }

    public List getCarByColour(String colour) {

        try {
            return carRepository.getCarByColour(SecurityEscape.cleanIt(colour));
        }catch (Exception e){
            throw e;
        }
    }

    public List getCarByPriceBetween(double startPrice,double endPrice) {

        try {
            return carRepository.getCarByPriceBetween(SecurityEscape.cleanDouble(startPrice),SecurityEscape.cleanDouble(endPrice));
        }catch (Exception e){
            throw e;
        }
    }

    public List getCarByRegistration(String registration) {

        try {
            return carRepository.getCarByRegistration(SecurityEscape.cleanIt(registration));
        }catch (Exception e){
            throw e;
        }
    }

    public Car getCar(Long carID){
        try {
            return carRepository.findCarById(SecurityEscape.cleanLong(carID));
        }catch (Exception e){
            throw e;
        }
    }

    public String deleteCar(Long carID){

        try {
            carRepository.deleteById(SecurityEscape.cleanLong(carID));
            return "Listing deleted";
        }catch (Exception e){
            throw e;
        }
    }


    public List addCars(List<Car> cars) {
        List<Car> cleanCars = new ArrayList<Car>();
     try {
         for (Car car: cars)
         {
             Car cleanCar = new Car();

             cleanCar.setMake(SecurityEscape.cleanIt(car.getMake()));
             cleanCar.setCarModel(SecurityEscape.cleanIt(car.getCarModel()));
             cleanCar.setColour(SecurityEscape.cleanIt(car.getColour()));
             cleanCar.setRegistration(SecurityEscape.cleanIt(car.getRegistration()));
             cleanCar.setPrice(SecurityEscape.cleanDouble(car.getPrice()));
             cleanCar.setYear(SecurityEscape.cleanInt(car.getYear()));
             cleanCars.add(cleanCar);
         }

         return carRepository.saveAll(cleanCars);

     }catch (Exception e){
       throw e;
     }


    }
}
