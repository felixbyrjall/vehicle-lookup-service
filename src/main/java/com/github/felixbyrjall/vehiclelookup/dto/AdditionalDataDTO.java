package com.github.felixbyrjall.vehiclelookup.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdditionalDataDTO {
    private String licensePlatePrefix;
    private String county; // Fylke
    private String geographicalArea;
    private String vehicleType; // For special combinations like "Elektriskdrevet kjøretøy"
}
