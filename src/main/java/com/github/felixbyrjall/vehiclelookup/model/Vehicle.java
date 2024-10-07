package com.github.felixbyrjall.vehiclelookup.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "vehicle_cache")
public class Vehicle {
    @Id
    @Column(name = "vehicle_id", unique = true, nullable = false)
    private String vehicleId; //

    //Other fields
    private String licensePlate;  // regnr
    private String vin;  // understellsnummer
    private String firstRegistrationDateInNorway;  // registrertForstegangNorgeDato
    private String make;  // Merke
    private String model;  // Handelsbetegnelse / modell
    private String registrationStatus;  // Registreringsstatus
    private String registrationDateOnCurrentOwner;  // Registreringsdato på nåværende eier
    private String importCountry;  // Importland
    private String nextControlDate;  // Kontrollfrist eu godkjenning
    private String lastControlDate;  // Siste eu godkjenning
    private String fuelType;  // Drivstofftype
    private String color;  // Farge
    private String emissionsClass;  // Euro utslippsklasse

    private Double co2Emission;  // CO2 utslipp i blandet kjøring
    private Double fuelConsumption;  // Forbruk blandet kjøring
    private Double maxNetPower;  // Motor maks nettoeffekt
    private Double engineVolume;  // Motor slagvolum

    private Integer importMilage;  // Kilometerstand
    private Integer seatCount;  // Antall sitteplasser
    private Integer curbWeight; // egenvekt
    private Integer minimumCurbWeight; // egenvektMinimum
    private Integer payloadCapacity; // nyttelast
    private Integer maxTrailerWeightWithBrakes; // tillattTilhengervektMedBrems
    private Integer maxTrailerWeightWithoutBrakes; // tillattTilhengervektUtenBrems
    private Integer maxTotalWeight; // tillattTotalvekt
    private Integer maxVerticalCouplingLoad; // tillattVertikalKoplingslast
    private Integer maxGrossWeight; // tillattVogntogvekt

    private String county;
    private String geographicalArea;
    private String vehicleType;
}
