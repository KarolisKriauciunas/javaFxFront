package com.example.kursinis.model;

import com.example.kursinis.model.Account.DtoUser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
public class Vehicle {
    private String carName;
    private Long vehicleID;
    private String plateNumbers;
    private Long assignedId;
}

