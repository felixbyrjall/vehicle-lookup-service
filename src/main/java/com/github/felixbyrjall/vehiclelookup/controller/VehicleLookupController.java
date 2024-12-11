package com.github.felixbyrjall.vehiclelookup.controller;

import com.github.felixbyrjall.vehiclelookup.dto.VehicleDetailedDTO;
import com.github.felixbyrjall.vehiclelookup.dto.VehicleSimpleDTO;
import com.github.felixbyrjall.vehiclelookup.service.VehicleDTOService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleLookupController {
    private final VehicleDTOService vehicleDTOService;

    public VehicleLookupController(VehicleDTOService vehicleDTOService) {
        this.vehicleDTOService = vehicleDTOService;
    }

    @GetMapping("/simple/{licensePlate}")
    public Mono<VehicleSimpleDTO> getVehicleSimple(
            @PathVariable String licensePlate,
            @RequestHeader("X-User-Id") String userId
    ) {
        return vehicleDTOService.lookupVehicleSimple(licensePlate, userId);
    }

    @GetMapping("/detailed/{licensePlate}")
    public Mono<VehicleDetailedDTO> getVehicleDetailed(
            @PathVariable String licensePlate,
            @RequestHeader("X-User-Id") String userId
    ) {
        return vehicleDTOService.lookupVehicleDetailed(licensePlate, userId);
    }
}
