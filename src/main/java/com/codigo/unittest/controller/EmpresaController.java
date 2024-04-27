package com.codigo.unittest.controller;

import com.codigo.unittest.aggregates.request.EmpresaRequest;
import com.codigo.unittest.aggregates.response.BaseResponse;
import com.codigo.unittest.service.EmpresaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/empresa/v1/")
@AllArgsConstructor
public class EmpresaController {


    private final EmpresaService service;

    @PostMapping
    public ResponseEntity<BaseResponse> registrar(@RequestBody EmpresaRequest empresaRequest){
        return service.crear(empresaRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse>obtenerUno(@PathVariable Long id){
        return service.obtenerEmpresa(id);
    }

    @GetMapping()
    public ResponseEntity<BaseResponse>obtenerTodos(){
        return service.obtenerTodos();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse>actualizar(@PathVariable Long id, @RequestBody EmpresaRequest request){
        return service.actualizar(id,request);

    }
}
