package com.shyam.carsaleservice.entities;

import java.util.List;

public class CarListDTO {
        private List<Car> cars;

        public List<Car> getCars() { return cars; }

        public void setCars(List<Car> cars) {
            this.cars = cars;
        }
    }

