package com.shyam.carsaleservice.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;

@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Please provide the Registration Number") //mandatory registration validation
    private String	registration;
    @Min(value = 1900,message = "Please provide the valid Model Year")
    @Max(value = 2021,message = "Please provide the valid Model Year")
    private Integer	year;
    @NotEmpty(message = "Please provide the manufacturer name")
    private String	make;
    @NotEmpty(message = "Please provide the model name")
    private String carModel;
    private String	colour;
    @DecimalMin(value = "0.0", message = "Please provide offer price")
    private Double price;

//getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getCarModel() {
        return carModel;
    }
    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

}
