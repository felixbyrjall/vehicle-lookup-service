package com.github.felixbyrjall.vehiclelookup.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Vehicle {
    private String licensePlate;  // regnr
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
    private String seatCount;  // Antall sitteplasser
    private String emissionsClass;  // Euro utslippsklasse

    private double co2Emission;  // CO2 utslipp i blandet kjøring
    private double fuelConsumption;  // Forbruk blandet kjøring
    private double maxNetPower;  // Motor maks nettoeffekt
    private double engineVolume;  // Motor slagvolum

    private int curbWeight; // egenvekt
    private int minimumCurbWeight; // egenvektMinimum
    private int payloadCapacity; // nyttelast
    private int maxTrailerWeightWithBrakes; // tillattTilhengervektMedBrems
    private int maxTrailerWeightWithoutBrakes; // tillattTilhengervektUtenBrems
    private int maxTotalWeight; // tillattTotalvekt
    private int maxVerticalCouplingLoad; // tillattVertikalKoplingslast
    private int maxGrossWeight; // tillattVogntogvekt
}
