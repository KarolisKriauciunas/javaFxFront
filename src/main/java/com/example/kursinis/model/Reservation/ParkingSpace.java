package com.example.kursinis.model.Reservation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
public class ParkingSpace {
    @JsonProperty(namespace = "parkingLotId")
    private long parkingLotId;
    @JsonProperty(namespace = "status")
    private ReservationStatus status;
    @JsonProperty(namespace = "location")
    private String location;
    @JsonProperty(namespace = "price")
    private float price;
}
