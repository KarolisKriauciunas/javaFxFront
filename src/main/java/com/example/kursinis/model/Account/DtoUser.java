package com.example.kursinis.model.Account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
public class DtoUser {
    private String password;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private Long employeeID;
    private AccountType type;

    @Override
    public String toString() {
        return " employeeID=" + employeeID +" userName='" + userName;
    }
}