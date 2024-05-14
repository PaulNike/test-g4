package com.codigo.unittest.service;

import com.codigo.unittest.aggregates.response.Persona;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "MS-PRUEBA-DATOS")
public interface ClientPrueba {

    @GetMapping("/api/v1/persona/infoPersona")
    Persona getInfoPersona();

}
