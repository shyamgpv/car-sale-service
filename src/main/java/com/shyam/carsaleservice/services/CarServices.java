package com.shyam.carsaleservice.services;

import com.shyam.carsaleservice.entities.Car;
import com.shyam.carsaleservice.repository.CarRepository;
import com.shyam.carsaleservice.secuirity.MyCustomErrorDTO;
import com.shyam.carsaleservice.secuirity.SecurityEscape;
import io.swagger.models.auth.In;
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

        return carRepository.save(cleanCar);
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
        return carRepository.save(existingCar);
    }

    public List getCarByCarModel(String carModel){
        return carRepository.getCarByCarModel(SecurityEscape.cleanIt(carModel));

    }

    public List getCarByMake(String make) {
        return carRepository.getCarByMake(SecurityEscape.cleanIt(make));
    }

    public List getCarByYear(Integer year) {
        return carRepository.getCarByYear(SecurityEscape.cleanInt(year));
    }

    public List getCarByYearBetween(Integer startYear,Integer endYear) {
        return carRepository.getCarByYearBetween(SecurityEscape.cleanInt(startYear),SecurityEscape.cleanInt(endYear));
    }

    public List getCarByColour(String colour) {
        return carRepository.getCarByColour(SecurityEscape.cleanIt(colour));
    }

    public List getCarByPriceBetween(double startPrice,double endPrice) {
        return carRepository.getCarByPriceBetween(SecurityEscape.cleanDouble(startPrice),SecurityEscape.cleanDouble(endPrice));
    }

    public List getCarByRegistration(String registration) {
        return carRepository.getCarByRegistration(SecurityEscape.cleanIt(registration));
    }

    public Car getCar(Long carID){

        return carRepository.findCarById(SecurityEscape.cleanLong(carID));
    }

    public String deleteCar(Long carID){

        carRepository.deleteById(SecurityEscape.cleanLong(carID));
        return "Lisitng deleted";
    }


    public MyCustomErrorDTO addCars(ArrayList<Car> cars) {
        Integer count = 0;
     try {
         cars.forEach((car) -> {
             addCar(car);
         });
     }catch (Exception e){
         return  new MyCustomErrorDTO( // prepare a success statement
                 400,
                 "Data not valid",
                 false
         );
     }


        return new MyCustomErrorDTO(
                200,
                "cars added",
                true

        );
    }
}
