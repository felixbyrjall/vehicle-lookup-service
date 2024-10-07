package com.github.felixbyrjall.vehiclelookup.service;

import com.github.felixbyrjall.vehiclelookup.model.Vehicle;
import com.github.felixbyrjall.vehiclelookup.repository.VehicleRepository;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Service
public class VehicleLookupServiceImpl implements VehicleLookupService {
    private final String apiKey;
    private final WebClient webClient;
    private final VehicleJsonParser vehicleJsonParser;
    private final VehicleRepository vehicleRepository;

    public VehicleLookupServiceImpl(WebClient.Builder webClientBuilder, VehicleJsonParser vehicleJsonParser, VehicleRepository vehicleRepository) {
        Dotenv dotenv = Dotenv.load();  // Load the .env file
        this.apiKey = dotenv.get("API_KEY");  // Get the API key
        this.webClient = webClientBuilder.baseUrl("https://akfell-datautlevering.atlas.vegvesen.no").build();
        this.vehicleJsonParser = vehicleJsonParser;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Mono<Vehicle> lookupVehicle(String licensePlate) {
        String url = "/enkeltoppslag/kjoretoydata?kjennemerke=" + licensePlate;

        log.info("Looking up vehicle with license plate: {}", licensePlate);

        return webClient.get()
                .uri(url)
                .header("SVV-Authorization", "Apikey " + apiKey)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(json -> {
                    log.info("Successfully retrieved data for license plate: {}", licensePlate);
                    try {
                        Vehicle vehicle = vehicleJsonParser.parseVehicleFromJson(json);
                        return saveVehicle(vehicle);
                    } catch (Exception e) {
                        log.error("Error parsing vehicle JSON for license plate {}: {}", licensePlate, e.getMessage());
                        return Mono.error(new RuntimeException("Error parsing vehicle JSON", e));
                    }
                })
                .onErrorResume(error -> {
                    log.error("Error during vehicle lookup for {}: {}. Checking cache...", licensePlate, error.getMessage());
                    return findVehicleInCache(licensePlate);
                });
    }

    private Mono<Vehicle> saveVehicle(Vehicle vehicle) {
        return Mono.fromCallable(() -> vehicleRepository.save(vehicle))
                .subscribeOn(Schedulers.boundedElastic())
                .doOnSuccess(savedVehicle -> log.info("Saved vehicle data for license plate: {}", savedVehicle.getLicensePlate()));
    }

    private Mono<Vehicle> findVehicleInCache(String licensePlate) {
        return Mono.fromCallable(() -> vehicleRepository.findById(licensePlate)
                        .orElseThrow(() -> new EntityNotFoundException("No cached data found for license plate: " + licensePlate)))
                .subscribeOn(Schedulers.boundedElastic())
                .doOnSuccess(vehicle -> log.info("Returning cached data for license plate: {}", licensePlate))
                .doOnError(e -> log.warn("No cached data available for license plate: {}", licensePlate));
    }
}
