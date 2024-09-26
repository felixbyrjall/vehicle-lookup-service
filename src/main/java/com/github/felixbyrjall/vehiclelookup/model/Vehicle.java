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
@Table(name = "vehicle cache")
public class Vehicle {
    @Id
    @Column(name = "license_plate")
    private String licensePlate;  // regnr

    //Other fields
    private String vin;  // understellsnummer
    private String firstRegistrationDateInNorway;  // registrertForstegangNorgeDato
    private String make;  // Merke
    private String model;  // Handelsbetegnelse / modell
    private String registrationStatus;  // Registreringsstatus
    private String registrationDateOnCurrentOwner;  // Registreringsdato på nåværende eier
    private String importCountry;  // Importland
    private String importMilage;  // Kilometerstand
    private String nextControlDate;  // Kontrollfrist eu godkjenning
    private String lastControlDate;  // Siste eu godkjenning
    private String fuelType;  // Drivstofftype
    private String color;  // Farge
    private String emissionsClass;  // Euro utslippsklasse

    private double co2Emission;  // CO2 utslipp i blandet kjøring
    private double fuelConsumption;  // Forbruk blandet kjøring
    private double maxNetPower;  // Motor maks nettoeffekt
    private double engineVolume;  // Motor slagvolum

    private int seatCount;  // Antall sitteplasser
    private int curbWeight; // egenvekt
    private int minimumCurbWeight; // egenvektMinimum
    private int payloadCapacity; // nyttelast
    private int maxTrailerWeightWithBrakes; // tillattTilhengervektMedBrems
    private int maxTrailerWeightWithoutBrakes; // tillattTilhengervektUtenBrems
    private int maxTotalWeight; // tillattTotalvekt
    private int maxVerticalCouplingLoad; // tillattVertikalKoplingslast
    private int maxGrossWeight; // tillattVogntogvekt
}
