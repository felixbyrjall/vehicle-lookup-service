package com.github.felixbyrjall.vehiclelookup.service;

import com.github.felixbyrjall.vehiclelookup.dto.AdditionalDataDTO;
import com.github.felixbyrjall.vehiclelookup.dto.VehicleDetailedDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class AdditionalDataService {
    private final WebClient webClient;

    public AdditionalDataService(WebClient.Builder webClientBuilder, @Value("${additional.data.service.url:http://localhost:8081}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public Mono<VehicleDetailedDTO> addAdditionalVehicleData(VehicleDetailedDTO dto) {
        String vehicleId = dto.getVehicleId();
        return webClient.get()
                .uri("/vehicle/plate-info/{vehicleId}", vehicleId)
                .retrieve()
                .bodyToMono(AdditionalDataDTO.class)
                .map(additionalData -> {
                    dto.setCounty(additionalData.getCounty());
                    dto.setGeographicalArea(additionalData.getGeographicalArea());
                    dto.setVehicleType(additionalData.getVehicleType());

                    log.info("Enriched vehicle data: {}", additionalData);
                    return dto;
                })
                .onErrorResume(error -> {
                    log.error("Error fetching additional data for vehicleId {}: {}", vehicleId, error.getMessage());
                    return Mono.just(dto);
                });
    }
}
