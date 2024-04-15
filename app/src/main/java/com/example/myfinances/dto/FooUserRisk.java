package com.example.myfinances.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FooUserRisk {
    private String email;
    private double currentRisk;
    private double previousRisk;
}
