package com.github.felixbyrjall.vehiclelookup.service;

import com.github.felixbyrjall.vehiclelookup.dto.VehicleDetailedDTO;
import com.github.felixbyrjall.vehiclelookup.dto.VehicleSimpleDTO;
import com.github.felixbyrjall.vehiclelookup.model.Vehicle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class VehicleDTOServiceImpl implements VehicleDTOService {
    private final VehicleLookupService vehicleLookupService;
    private final AdditionalDataService additionalDataService;

    public VehicleDTOServiceImpl(VehicleLookupService vehicleLookupService, AdditionalDataService additionalDataService) {
        this.vehicleLookupService = vehicleLookupService;
        this.additionalDataService = additionalDataService;
    }

    @Override
    public Mono<VehicleSimpleDTO> lookupVehicleSimple(String licensePlate) {
        return vehicleLookupService.lookupVehicle(licensePlate)
                .map(this::convertToSimpleDTO);
    }

    @Override
    public Mono<VehicleDetailedDTO> lookupVehicleDetailed(String licensePlate) {
        return vehicleLookupService.lookupVehicle(licensePlate)
                .flatMap(vehicle -> {
                    VehicleDetailedDTO dto = convertToDetailedDTO(vehicle);
                    return additionalDataService.addAdditionalVehicleData(dto);
                });
    }

    private VehicleSimpleDTO convertToSimpleDTO(Vehicle vehicle) {
        VehicleSimpleDTO dto = new VehicleSimpleDTO();
        dto.setVehicleId(vehicle.getVehicleId());
        dto.setLicensePlate(vehicle.getLicensePlate());
        dto.setMake(vehicle.getMake());
        dto.setModel(vehicle.getModel());
        dto.setFuelType(vehicle.getFuelType());
        dto.setColor(vehicle.getColor());
        return dto;
    }

    private VehicleDetailedDTO convertToDetailedDTO(Vehicle vehicle) {
        VehicleDetailedDTO dto = new VehicleDetailedDTO();
        dto.setVehicleId(vehicle.getVehicleId());
        dto.setLicensePlate(vehicle.getLicensePlate());
        dto.setVin(vehicle.getVin());
        dto.setFirstRegistrationDateInNorway(vehicle.getFirstRegistrationDateInNorway());
        dto.setMake(vehicle.getMake());
        dto.setModel(vehicle.getModel());
        dto.setRegistrationStatus(vehicle.getRegistrationStatus());
        dto.setRegistrationDateOnCurrentOwner(vehicle.getRegistrationDateOnCurrentOwner());
        dto.setImportCountry(vehicle.getImportCountry());
        dto.setNextControlDate(vehicle.getNextControlDate());
        dto.setLastControlDate(vehicle.getLastControlDate());
        dto.setFuelType(vehicle.getFuelType());
        dto.setColor(vehicle.getColor());
        dto.setEmissionsClass(vehicle.getEmissionsClass());

        dto.setCo2Emission(vehicle.getCo2Emission());
        dto.setFuelConsumption(vehicle.getFuelConsumption());
        dto.setMaxNetPower(vehicle.getMaxNetPower());
        dto.setEngineVolume(vehicle.getEngineVolume());

        dto.setImportMilage(vehicle.getImportMilage());
        dto.setSeatCount(vehicle.getSeatCount());
        dto.setCurbWeight(vehicle.getCurbWeight());
        dto.setMinimumCurbWeight(vehicle.getMinimumCurbWeight());
        dto.setPayloadCapacity(vehicle.getPayloadCapacity());
        dto.setMaxTrailerWeightWithBrakes(vehicle.getMaxTrailerWeightWithBrakes());
        dto.setMaxTrailerWeightWithoutBrakes(vehicle.getMaxTrailerWeightWithoutBrakes());
        dto.setMaxTotalWeight(vehicle.getMaxTotalWeight());
        dto.setMaxVerticalCouplingLoad(vehicle.getMaxVerticalCouplingLoad());
        dto.setMaxGrossWeight(vehicle.getMaxGrossWeight());

        dto.setCounty(vehicle.getCounty());
        dto.setGeographicalArea(vehicle.getGeographicalArea());
        dto.setVehicleType(vehicle.getVehicleType());
        return dto;
    }
}
