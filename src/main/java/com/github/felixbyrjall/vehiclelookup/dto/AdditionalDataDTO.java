package com.github.felixbyrjall.vehiclelookup.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdditionalDataDTO {
    private String county; // Fylke
    private String geographicalArea;
    private String vehicleType; // For special combinations like "Elektriskdrevet kjøretøy"
}
