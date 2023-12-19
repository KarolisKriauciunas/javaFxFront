package com.example.kursinis.model.Reservation;

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
public class Reservation {
    private Long userId;
    private Timestamp reservationStartDate;
    private Timestamp reservationEndDate;
    private Long parkingSpaceId;
    private ReservationStatus reservationStatus;
}
