package com.example.myfinances.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDto {
    private String requestEmail;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
}
