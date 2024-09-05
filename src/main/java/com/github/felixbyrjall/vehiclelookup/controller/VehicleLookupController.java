package com.github.felixbyrjall.vehiclelookup.controller;

import com.github.felixbyrjall.vehiclelookup.model.Vehicle;
import com.github.felixbyrjall.vehiclelookup.service.VehicleLookupService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class VehicleLookupController {
    private final VehicleLookupService vehicleLookupService;

    public VehicleLookupController(VehicleLookupService vehicleLookupService) {
        this.vehicleLookupService = vehicleLookupService;
    }

    @GetMapping("/vehicle/{licensePlate}")
    public Mono<Vehicle> getVehicleData(@PathVariable String licensePlate) {
        return vehicleLookupService.lookupVehicle(licensePlate);
    }
}
