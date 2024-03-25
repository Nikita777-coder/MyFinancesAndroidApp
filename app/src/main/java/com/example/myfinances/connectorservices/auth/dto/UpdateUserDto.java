package com.example.myfinances.connectorservices.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class UpdateUserDto {
    private String requestEmail;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private Boolean isActive;
}
