package com.github.felixbyrjall.vehiclelookup.service;

import com.github.felixbyrjall.vehiclelookup.model.Vehicle;
import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class VehicleLookupServiceImpl implements VehicleLookupService {

    private static final Logger log = LoggerFactory.getLogger(VehicleLookupServiceImpl.class);

    private final WebClient webClient;
    private final VehicleJsonParser vehicleJsonParser;
    private final String apiKey;

    public VehicleLookupServiceImpl(WebClient.Builder webClientBuilder, VehicleJsonParser vehicleJsonParser) {
        Dotenv dotenv = Dotenv.load();  // Load the .env file
        this.apiKey = dotenv.get("API_KEY");  // Get the API key
        this.webClient = webClientBuilder.baseUrl("https://akfell-datautlevering.atlas.vegvesen.no").build();
        this.vehicleJsonParser = vehicleJsonParser;
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
                        return Mono.just(vehicleJsonParser.parseVehicleFromJson(json));
                    } catch (Exception e) {
                        log.error("Error parsing vehicle JSON for license plate {}: {}", licensePlate, e.getMessage());
                        return Mono.error(new RuntimeException("Error parsing vehicle JSON", e));
                    }
                })
                .doOnError(error -> log.error("Error during vehicle lookup: {}", error.getMessage()));
    }
}
