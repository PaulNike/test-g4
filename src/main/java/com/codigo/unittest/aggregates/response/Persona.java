package com.codigo.unittest.aggregates.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Persona {
    private String nombre;
    private int edad;
    private String email;
}
