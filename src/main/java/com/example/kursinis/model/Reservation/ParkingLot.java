package com.example.kursinis.model.Reservation;

import com.example.kursinis.model.City;
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
public class ParkingLot {
    @JsonProperty(namespace = "parkingLotId")
    private Long parkingLotId;
    @JsonProperty(namespace = "address")
    private String address;
    @JsonProperty(namespace = "city")
    private City city;
}
