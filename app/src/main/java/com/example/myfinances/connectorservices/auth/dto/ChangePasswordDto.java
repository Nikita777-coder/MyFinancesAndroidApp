package com.example.myfinances.connectorservices.auth.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ChangePasswordDto {
    private String email;
    private String password;
}
