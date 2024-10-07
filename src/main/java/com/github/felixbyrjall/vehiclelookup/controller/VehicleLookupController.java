package com.github.felixbyrjall.vehiclelookup.controller;

import com.github.felixbyrjall.vehiclelookup.dto.VehicleDetailedDTO;
import com.github.felixbyrjall.vehiclelookup.dto.VehicleSimpleDTO;
import com.github.felixbyrjall.vehiclelookup.service.VehicleDTOService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/vehicle")
public class VehicleLookupController {
    private final VehicleDTOService vehicleDTOService;

    public VehicleLookupController(VehicleDTOService vehicleDTOService) {
        this.vehicleDTOService = vehicleDTOService;
    }

    @GetMapping("/simple/{licensePlate}")
    public Mono<VehicleSimpleDTO> getVehicleSimple(@PathVariable String licensePlate) {
        return vehicleDTOService.lookupVehicleSimple(licensePlate);
    }

    @GetMapping("/detailed/{licensePlate}")
    public Mono<VehicleDetailedDTO> getVehicleDetailed(@PathVariable String licensePlate) {
        return vehicleDTOService.lookupVehicleDetailed(licensePlate);
    }
}
