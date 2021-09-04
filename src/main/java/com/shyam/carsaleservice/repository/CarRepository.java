package com.shyam.carsaleservice.repository;

import com.shyam.carsaleservice.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car,Long> {
}
