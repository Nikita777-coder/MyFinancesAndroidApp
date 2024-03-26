package com.example.myfinances.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UpdateUserDto {
    private String requestEmail;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private Boolean isActive;
}
