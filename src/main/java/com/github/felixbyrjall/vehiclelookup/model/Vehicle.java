package com.github.felixbyrjall.vehiclelookup.model;

public class Vehicle {
    private String licensePlate;
    private String make;
    private String model;
    private String year;

    // Default constructor
    public Vehicle() {}

    // Constructor with parameters
    public Vehicle(String licensePlate, String make, String model, String year) {
        this.licensePlate = licensePlate;
        this.make = make;
        this.model = model;
        this.year = year;
    }

    // Getters and setters
    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Vehicle [licensePlate=" + licensePlate + ", make=" + make + ", model=" + model + ", year=" + year + "]";
    }
}
