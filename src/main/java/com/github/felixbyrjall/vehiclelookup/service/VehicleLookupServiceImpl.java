package com.github.felixbyrjall.vehiclelookup.service;

import com.github.felixbyrjall.vehiclelookup.model.Vehicle;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class VehicleLookupServiceImpl implements VehicleLookupService{

    private final WebClient webClient;
    private String apiKey;

    public VehicleLookupServiceImpl(WebClient.Builder webClientBuilder) {
        Dotenv dotenv = Dotenv.load();  // Load the .env file
        this.apiKey = dotenv.get("API_KEY");  // Get the API key
        this.webClient = webClientBuilder.baseUrl("https://akfell-datautlevering.atlas.vegvesen.no").build();
    }

    @Override
    public Mono<Vehicle> lookupVehicle(String licensePlate) {
        String url = "/enkeltoppslag/kjoretoydata?kjennemerke=" + licensePlate;

        return webClient.get()
                .uri(url)
                .header("SVV-Authorization", "Apikey " + apiKey)
                .retrieve()
                .bodyToMono(Vehicle.class)
                .doOnError(error -> System.out.println("Error" + error.getMessage()));
    }
}
