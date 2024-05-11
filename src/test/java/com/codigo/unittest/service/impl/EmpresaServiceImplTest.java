package com.codigo.unittest.service.impl;

import com.codigo.unittest.aggregates.constants.Constants;
import com.codigo.unittest.aggregates.request.EmpresaRequest;
import com.codigo.unittest.aggregates.response.BaseResponse;
import com.codigo.unittest.dao.EmpresaRepository;
import com.codigo.unittest.entity.Empresa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;


import java.util.ArrayList;
import java.util.List;
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
        //assertTrue(response.getBody().getEntidad()));
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
        //assertTrue(response.getBody().getEntidad());
        //assertSame(empresa, response.getBody().getEntidad());
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
        //assertTrue(response.getBody().getEntidad().isPresent());
        //assertSame(empresa, response.getBody().getEntidad());
    }
/*    @Test
    void testObtenerEmpresaNullPointer(){
        //ARRANGE
        Long id = 1L;
        Empresa empresa = new Empresa();
        when(empresaRepository.findById(id)).thenReturn(Optional.empty());

        //act & assert
        assertThrows(NoSuchElementException.class, () -> empresaService.obtenerEmpresa(id));
    }*/

    @Test
    void testObtenerEmpresaNoExistente() {
        Long id = 1L;
        when(empresaRepository.findById(id)).thenReturn(Optional.empty());
        ResponseEntity<BaseResponse> respose = empresaService.obtenerEmpresa(id);
        assertEquals(Constants.CODE_EMPRESA_NO_EXIST, respose.getBody().getCode());
        assertEquals(Constants.MSJ_EMPRESA_NO_EXIST, respose.getBody().getMessage());
        //assertTrue(respose.getBody().getEntidad().isEmpty());
        assertSame(Optional.empty(), respose.getBody().getEntidad());
    }

    @Test
    void  testObtenerListaEmpresasExistentes(){
        List<Empresa> empresas = new ArrayList<>();
        empresas.add(new Empresa());
        when(empresaRepository.findAll()).thenReturn(empresas);
        ResponseEntity<BaseResponse> respose = empresaService.obtenerTodos();
        assertEquals(Constants.CODE_OK, respose.getBody().getCode());
        assertEquals(Constants.MSJ_OK, respose.getBody().getMessage());
        //assertTrue(respose.getBody().getEntidad().isPresent());
        //assertSame(empresas, respose.getBody().getEntidad());
    }

    @Test
    void  testObtenerListaEmpresasVacia(){
        List<Empresa> empresas = new ArrayList<>();
        when(empresaRepository.findAll()).thenReturn(empresas);
        ResponseEntity<BaseResponse> respose = empresaService.obtenerTodos();
        assertEquals(Constants.CODE_EMPRESA_NO_EXIST, respose.getBody().getCode());
        assertEquals(Constants.MSJ_EMPRESA_NO_EXIST, respose.getBody().getMessage());
        //assertTrue(respose.getBody().getEntidad().isEmpty());
        assertSame(Optional.empty(), respose.getBody().getEntidad());
    }

    @Test
    void testEmpresaActualizado(){
        EmpresaRequest request = new EmpresaRequest();
        Empresa empresa = new Empresa();
        when(empresaRepository.existsById(anyLong())).thenReturn(true);
        when(empresaRepository.findById(anyLong())).thenReturn(Optional.of(empresa));
        when(empresaRepository.save(empresa)).thenReturn(empresa);

        ResponseEntity<BaseResponse> respose = empresaService.actualizar(anyLong(), request);

        assertEquals(Constants.CODE_OK, respose.getBody().getCode());
        assertEquals(Constants.MSJ_OK, respose.getBody().getMessage());
        //assertTrue(respose.getBody().getEntidad().isPresent());
        //assertSame(empresa, respose.getBody().getEntidad());
    }

    @Test
    void testEmpresaNoActualizado(){
        EmpresaRequest request = new EmpresaRequest();
        when(empresaRepository.existsById(anyLong())).thenReturn(false);

        ResponseEntity<BaseResponse> respose = empresaService.actualizar(anyLong(), request);
        assertEquals(Constants.CODE_EMPRESA_NO_EXIST, respose.getBody().getCode());
        assertEquals(Constants.MSJ_EMPRESA_NO_EXIST, respose.getBody().getMessage());
        //assertTrue(respose.getBody().getEntidad().isEmpty());
        assertSame(Optional.empty(), respose.getBody().getEntidad());
    }

    @Test
    void testEmpresaBorrada(){
        Empresa empresa = new Empresa();
        when(empresaRepository.existsById(anyLong())).thenReturn(true);
        when(empresaRepository.findById(anyLong())).thenReturn(Optional.of(empresa));
        when(empresaRepository.save(empresa)).thenReturn(empresa);
        ResponseEntity<BaseResponse> respose = empresaService.delete(anyLong());
        assertEquals(Constants.CODE_OK, respose.getBody().getCode());
        assertEquals(Constants.MSJ_OK, respose.getBody().getMessage());
        //assertTrue(respose.getBody().getEntidad().isPresent());
        //assertSame(empresa, respose.getBody().getEntidad());
    }

    @Test
    void testEmpresaNoBorrada(){
        when(empresaRepository.existsById(anyLong())).thenReturn(false);
        ResponseEntity<BaseResponse> respose = empresaService.delete(anyLong());
        assertEquals(Constants.CODE_EMPRESA_NO_EXIST, respose.getBody().getCode());
        assertEquals(Constants.MSJ_EMPRESA_NO_EXIST, respose.getBody().getMessage());
        //assertTrue(respose.getBody().getEntidad().isEmpty());
        assertSame(Optional.empty(), respose.getBody().getEntidad());
    }
}