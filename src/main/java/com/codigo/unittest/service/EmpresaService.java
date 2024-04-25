package com.codigo.unittest.service;

import com.codigo.unittest.aggregates.request.EmpresaRequest;
import com.codigo.unittest.aggregates.response.BaseResponse;
import org.springframework.http.ResponseEntity;


public interface EmpresaService {

    ResponseEntity<BaseResponse> crear(EmpresaRequest request);
    ResponseEntity<BaseResponse> obtenerEmpresa(Long id);
    ResponseEntity<BaseResponse> obtenerTodos();
    ResponseEntity<BaseResponse> actualizar(Long id, EmpresaRequest request);
    ResponseEntity<BaseResponse> delete(Long id);
    ResponseEntity<BaseResponse> obtenerEmpresaXNumDoc(String numDocu);

}
