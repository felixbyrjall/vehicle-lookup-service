package com.github.felixbyrjall.vehiclelookup.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleSimpleDTO {

    private String vehicleId;  // Vehicle ID
    private String licensePlate;  // regnr
    private String make;  // Merke
    private String model;  // Handelsbetegnelse / modell
    private String fuelType;  // Drivstofftype
    private String color;  // Farge
}
