package com.shyam.carsaleservice.repository;

import com.shyam.carsaleservice.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car,Long> {
    Car findCarById(Long id);

     List getCarByCarModel(String carModel);
    List getCarByMake(String make);
    List getCarByYear(Integer year);
    List getCarByYearBetween(Integer startYear,Integer endYear);
    List getCarByColour(String colour);
    List getCarByPriceBetween(double startPrice,double endPrice);
    List getCarByRegistration(String registration);

}
