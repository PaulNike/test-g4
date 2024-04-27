package com.codigo.unittest.service.impl;

import com.codigo.unittest.aggregates.constants.Constants;
import com.codigo.unittest.aggregates.request.EmpresaRequest;
import com.codigo.unittest.aggregates.response.BaseResponse;
import com.codigo.unittest.dao.EmpresaRepository;
import com.codigo.unittest.entity.Empresa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;


import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmpresaServiceImplTest {

    @Mock
    private EmpresaRepository empresaRepository;
    @InjectMocks
    private EmpresaServiceImpl empresaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //AAA -> ARRANGE -> ACT - ASSERT (ORGANIZAR, ACTUAR, AFIRMAR)
    @Test
    void testCrearEmpresaExistente(){
        //ARRANGE
        EmpresaRequest request = new EmpresaRequest();
        request.setNumeroDocumento("123456789");

        //simular|preparar el compartamiento del mock
        when(empresaRepository.existsByNumeroDocumento(anyString())).thenReturn(true);

        //ACT
        ResponseEntity<BaseResponse> response = empresaService.crear(request);

        //ASSERT
        assertEquals(Constants.CODE_EXIST, response.getBody().getCode());
        assertEquals(Constants.MSJ_EXIST, response.getBody().getMessage());
        assertTrue(response.getBody().getEntidad().isEmpty());
    }

    @Test
    void testCrearEmpresaNueva(){
        //ARRANGE
        EmpresaRequest empresaRequest = new EmpresaRequest();
        empresaRequest.setNumeroDocumento("123456789");
        Empresa empresa = new Empresa();

        //CONFIGURAR EL MOCK
        when(empresaRepository.existsByNumeroDocumento(anyString())).thenReturn(false);
        when(empresaRepository.save(any(Empresa.class))).thenReturn(empresa);

        //llama a mi metodo de la clase real (impl)
        ResponseEntity<BaseResponse> response = empresaService.crear(empresaRequest);

        //ASSERT
        assertEquals(Constants.CODE_OK, response.getBody().getCode());
        assertEquals(Constants.MSJ_OK, response.getBody().getMessage());
        assertTrue(response.getBody().getEntidad().isPresent());
        assertSame(empresa, response.getBody().getEntidad().get());
    }

    @Test
    void testObtenerEmpresaExiste(){
        //ARRANGE
        Long id = 1L;
        Empresa empresa = new Empresa();
        empresa.setDireccion("LIMA");
        when(empresaRepository.findById(id)).thenReturn(Optional.of(empresa));
        //ACT
        ResponseEntity<BaseResponse> response = empresaService.obtenerEmpresa(id);
        //ASSERT
        assertEquals(Constants.CODE_OK, response.getBody().getCode());
        assertEquals(Constants.MSJ_OK, response.getBody().getMessage());
        assertTrue(response.getBody().getEntidad().isPresent());
        assertSame(empresa, response.getBody().getEntidad().get());
    }
    @Test
    void testObtenerEmpresaNullPointer(){
        //ARRANGE
        Long id = 1L;
        Empresa empresa = new Empresa();
        when(empresaRepository.findById(id)).thenReturn(Optional.empty());

        //act & assert
        assertThrows(NoSuchElementException.class, () -> empresaService.obtenerEmpresa(id));
    }
}