package com.example.kursinis.model.Trip;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
public class Trip {
    private Long driverId;
    private Timestamp tripStartDate;
    private Timestamp tripEndDate;
    private String destination;
    private Long tripId;
    private Long cargoId;
    private TripStatus status;
}
