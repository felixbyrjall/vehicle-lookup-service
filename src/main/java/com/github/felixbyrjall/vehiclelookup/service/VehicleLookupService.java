package com.github.felixbyrjall.vehiclelookup.service;

import com.github.felixbyrjall.vehiclelookup.model.Vehicle;
import reactor.core.publisher.Mono;

public interface VehicleLookupService {
    Mono<Vehicle> lookupVehicle(String licensePlate, String userId);

    void publishVehicleSearchedEvent(String licensePlate, String userId);
}
