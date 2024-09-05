package com.github.felixbyrjall.vehiclelookup.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.felixbyrjall.vehiclelookup.model.Vehicle;
import org.springframework.stereotype.Service;

@Service
public class VehicleJsonParser {

    private static final Logger log = LoggerFactory.getLogger(VehicleJsonParser.class);

    public Vehicle parseVehicleFromJson(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(json);

        JsonNode vehicleDataList = rootNode.path("kjoretoydataListe");
        if (vehicleDataList.isMissingNode() || !vehicleDataList.isArray() || vehicleDataList.size() == 0) {
            log.warn("Invalid JSON structure: Missing or incorrect 'kjoretoydataListe'");
            throw new RuntimeException("Invalid JSON structure: Missing or incorrect 'kjoretoydataListe'");
        }

        JsonNode vehicleNode = vehicleDataList.get(0);
        if (vehicleNode == null) {
            log.warn("Vehicle data not found");
            throw new RuntimeException("Vehicle data not found");
        }

        Vehicle vehicle = new Vehicle();

        // Set license plate and VIN
        JsonNode vehicleData = vehicleNode;
        vehicle.setLicensePlate(vehicleData.path("kjoretoyId").path("kjennemerke").asText());
        vehicle.setVin(vehicleData.path("kjoretoyId").path("understellsnummer").asText());

        // Set first registration date
        vehicle.setFirstRegistrationDateInNorway(vehicleData.path("forstegangsregistrering").path("registrertForstegangNorgeDato").asText());

        // Set make, model, and registration status
        JsonNode generalData = vehicleData.path("godkjenning").path("tekniskGodkjenning").path("tekniskeData").path("generelt");
        if (generalData != null && !generalData.isMissingNode()) {
            JsonNode merkeNode = generalData.path("merke").get(0);
            if (merkeNode != null) {
                vehicle.setMake(merkeNode.path("merke").asText());
            }
            JsonNode handelsbetegnelseNode = generalData.path("handelsbetegnelse").get(0);
            if (handelsbetegnelseNode != null) {
                vehicle.setModel(handelsbetegnelseNode.asText());
            }
        }
        vehicle.setRegistrationStatus(vehicleData.path("registrering").path("registreringsstatus").path("kodeBeskrivelse").asText());
        vehicle.setRegistrationDateOnCurrentOwner(vehicleData.path("registrering").path("fomTidspunkt").asText());

        // Set import data
        JsonNode importData = vehicleData.path("godkjenning").path("forstegangsGodkjenning").path("bruktimport");
        if (importData != null && !importData.isMissingNode()) {
            vehicle.setImportCountry(importData.path("importland").path("landNavn").asText());
            vehicle.setImportMilage(importData.path("kilometerstand").asText());
        }

        // Set periodic control dates
        JsonNode periodicControl = vehicleData.path("periodiskKjoretoyKontroll");
        if (periodicControl != null && !periodicControl.isMissingNode()) {
            vehicle.setNextControlDate(periodicControl.path("kontrollfrist").asText());
            vehicle.setLastControlDate(periodicControl.path("sistGodkjent").asText());
        }

        // Set fuel type and color
        JsonNode environmentalData = vehicleData.path("godkjenning").path("tekniskGodkjenning").path("tekniskeData").path("miljodata").path("miljoOgdrivstoffGruppe");
        if (environmentalData != null && environmentalData.isArray() && environmentalData.size() > 0) {
            JsonNode fuelData = environmentalData.get(0);
            if (fuelData != null && !fuelData.isMissingNode()) {
                vehicle.setFuelType(fuelData.path("drivstoffKodeMiljodata").path("kodeBeskrivelse").asText());
            }
        }
        JsonNode colorData = vehicleData.path("godkjenning").path("tekniskGodkjenning").path("tekniskeData").path("karosseriOgLasteplan").path("rFarge");
        if (colorData != null && colorData.isArray() && colorData.size() > 0) {
            vehicle.setColor(colorData.get(0).path("kodeNavn").asText());
        }

        // Set seating count
        JsonNode personTall = vehicleData.path("godkjenning").path("tekniskGodkjenning").path("tekniskeData").path("persontall");
        if (personTall != null && !personTall.isMissingNode()) {
            vehicle.setSeatCount(personTall.path("sitteplasserTotalt").asText());
        }

        // Set emissions class, CO2 emission, and fuel consumption
        if (environmentalData != null && environmentalData.isArray() && environmentalData.size() > 0) {
            JsonNode emissionDetails = environmentalData.get(0).path("forbrukOgUtslipp").get(0);
            if (emissionDetails != null && !emissionDetails.isMissingNode()) {
                vehicle.setEmissionsClass(vehicleData.path("godkjenning").path("tekniskGodkjenning").path("tekniskeData").path("miljodata").path("euroKlasse").path("kodeBeskrivelse").asText());
                vehicle.setCo2Emission(emissionDetails.path("co2BlandetKjoring").asDouble());
                vehicle.setFuelConsumption(emissionDetails.path("forbrukBlandetKjoring").asDouble());
            }
        }

        // Set engine information
        JsonNode engineData = vehicleData.path("godkjenning").path("tekniskGodkjenning").path("tekniskeData").path("motorOgDrivverk").path("motor");
        if (engineData != null && engineData.isArray() && engineData.size() > 0) {
            JsonNode engineDetails = engineData.get(0);
            if (engineDetails != null && !engineDetails.isMissingNode()) {
                JsonNode drivstoffArray = engineDetails.path("drivstoff");
                if (drivstoffArray.isArray() && drivstoffArray.size() > 0) {
                    vehicle.setMaxNetPower(drivstoffArray.get(0).path("maksNettoEffekt").asDouble());
                }
                vehicle.setEngineVolume(engineDetails.path("slagvolum").asDouble());
            }
        }

        // Set weight information
        JsonNode weightData = vehicleData.path("godkjenning").path("tekniskGodkjenning").path("tekniskeData").path("vekter");
        if (weightData != null && !weightData.isMissingNode()) {
            vehicle.setCurbWeight(weightData.path("egenvekt").asInt());
            vehicle.setMinimumCurbWeight(weightData.path("egenvektMinimum").asInt());
            vehicle.setPayloadCapacity(weightData.path("nyttelast").asInt());
            vehicle.setMaxTrailerWeightWithBrakes(weightData.path("tillattTilhengervektMedBrems").asInt());
            vehicle.setMaxTrailerWeightWithoutBrakes(weightData.path("tillattTilhengervektUtenBrems").asInt());
            vehicle.setMaxTotalWeight(weightData.path("tillattTotalvekt").asInt());
            vehicle.setMaxVerticalCouplingLoad(weightData.path("tillattVertikalKoplingslast").asInt());
            vehicle.setMaxGrossWeight(weightData.path("tillattVogntogvekt").asInt());
        }

        log.info("Vehicle JSON parsed successfully for license plate: " + vehicle.getLicensePlate());
        return vehicle;
    }
}
