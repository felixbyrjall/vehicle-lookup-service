package com.github.felixbyrjall.vehiclelookup.service;

import com.github.felixbyrjall.vehiclelookup.dto.VehicleDetailedDTO;
import com.github.felixbyrjall.vehiclelookup.dto.VehicleSimpleDTO;
import reactor.core.publisher.Mono;

public interface VehicleDTOService {
    Mono<VehicleSimpleDTO> lookupVehicleSimple(String licensePlate);
    Mono<VehicleDetailedDTO> lookupVehicleDetailed(String licensePlate);
}
