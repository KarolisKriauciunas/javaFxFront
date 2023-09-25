package com.example.kursinis.model;

import com.example.kursinis.model.Account.AccountType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Session {
    @JsonProperty("userName")
    public String userName;
    @JsonProperty("type")
    public AccountType type;
    @JsonProperty("employeeID")
    public Long employeeID;

}
