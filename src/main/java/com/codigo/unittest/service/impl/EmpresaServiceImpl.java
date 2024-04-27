package com.codigo.unittest.service.impl;

import com.codigo.unittest.aggregates.constants.Constants;
import com.codigo.unittest.aggregates.request.EmpresaRequest;
import com.codigo.unittest.aggregates.response.BaseResponse;
import com.codigo.unittest.dao.EmpresaRepository;
import com.codigo.unittest.entity.Empresa;
import com.codigo.unittest.service.EmpresaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class EmpresaServiceImpl implements EmpresaService {

    private final EmpresaRepository empresaRepository;

    @Override
    public ResponseEntity<BaseResponse> crear(EmpresaRequest request) {
        boolean exist = empresaRepository.existsByNumeroDocumento(request.getNumeroDocumento());
        if(exist){
            BaseResponse response = new BaseResponse(Constants.CODE_EXIST,Constants.MSJ_EXIST, Optional.empty());
            return ResponseEntity.ok(response);
        }else{
            Empresa empresaGuardar = empresaRepository.save(getEntity(request));
            BaseResponse response = new BaseResponse(Constants.CODE_OK,Constants.MSJ_OK, Optional.of(empresaGuardar));
            return ResponseEntity.ok(response);
        }
    }

    private Empresa getEntity(EmpresaRequest request){
        Empresa entity = new Empresa();
        entity.setRazonSocial(request.getRazonSocial());
        entity.setTipoDocumento(request.getTipoDocumento());
        entity.setNumeroDocumento(request.getNumeroDocumento());
        entity.setCondicion(Constants.CONDICION);
        entity.setDireccion(request.getDireccion());
        entity.setDistrito(request.getDistrito());
        entity.setProvincia(request.getProvincia());
        entity.setDepartamento(request.getDepartamento());
        entity.setEstado(Constants.STATUS_ACTIVE);
        entity.setEsAgenteRetencion(Constants.AGENTE_RETENCION_TRUE);
        entity.setUsuaCrea(Constants.AUDIT_ADMIN);
        entity.setDateCreate(getTimestamp());
        return entity;
    }
    private Timestamp getTimestamp(){
        long currentTime = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(currentTime);
        return timestamp;
    }
    private Empresa getEntityUpdate(EmpresaRequest request, Empresa empresa){
        if(empresa != null){
            empresa.setRazonSocial(request.getRazonSocial());
            empresa.setTipoDocumento(request.getTipoDocumento());
            empresa.setNumeroDocumento(request.getNumeroDocumento());
            empresa.setDireccion(request.getDireccion());
            empresa.setDistrito(request.getDistrito());
            empresa.setProvincia(request.getProvincia());
            empresa.setDepartamento(request.getDepartamento());
            empresa.setUsuaModif(Constants.AUDIT_ADMIN);
            empresa.setDateModif(getTimestamp());
        }else {
            return null;
        }
        return empresa;
    }
    @Override
    public ResponseEntity<BaseResponse> obtenerEmpresa(Long id) {
        BaseResponse baseResponse = new BaseResponse();
        Optional<Empresa> empresaBuscar = empresaRepository.findById(id);
        if(empresaBuscar.isPresent()){
            baseResponse.setCode(Constants.CODE_OK);
            baseResponse.setMessage(Constants.MSJ_OK);
            baseResponse.setEntidad(empresaBuscar);
        }else{
            empresaBuscar.get().getRazonSocial();
            baseResponse.setCode(Constants.CODE_EMPRESA_NO_EXIST);
            baseResponse.setMessage(Constants.MSJ_EMPRESA_NO_EXIST);
            baseResponse.setEntidad(Optional.empty());
        }
        return ResponseEntity.ok(baseResponse);
    }

    @Override
    public ResponseEntity<BaseResponse> obtenerTodos() {
        BaseResponse baseResponse = new BaseResponse();
        List<Empresa> listEmpresa = empresaRepository.findAll();

        if(!listEmpresa.isEmpty()){
            baseResponse.setCode(Constants.CODE_OK);
            baseResponse.setMessage(Constants.MSJ_OK);
            baseResponse.setEntidad(Optional.of(listEmpresa));
        }else {
            baseResponse.setCode(Constants.CODE_EMPRESA_NO_EXIST);
            baseResponse.setMessage(Constants.MSJ_EMPRESA_NO_EXIST);
            baseResponse.setEntidad(Optional.empty());
        }
        return ResponseEntity.ok(baseResponse);
    }

    @Override
    public ResponseEntity<BaseResponse> actualizar(Long id, EmpresaRequest requestPersona) {
        BaseResponse baseResponse = new BaseResponse();
        if (empresaRepository.existsById(id)){
            Empresa empresaRecuperada = empresaRepository.findById(id).orElse( null);
            Empresa actualizar = getEntityUpdate(requestPersona,empresaRecuperada);
            baseResponse.setCode(Constants.CODE_OK);
            baseResponse.setMessage(Constants.MSJ_OK);
            baseResponse.setEntidad(Optional.of(empresaRepository.save(actualizar)));
        }else{
            baseResponse.setCode(Constants.CODE_EMPRESA_NO_EXIST);
            baseResponse.setMessage(Constants.MSJ_EMPRESA_NO_EXIST);
            baseResponse.setEntidad(Optional.empty());
        }
        return ResponseEntity.ok(baseResponse);
    }

    @Override
    public ResponseEntity<BaseResponse> delete(Long id) {
        BaseResponse baseResponse = new BaseResponse();
        if (empresaRepository.existsById(id)){
            Empresa empresaRecuperada = empresaRepository.findById(id).orElse( null);
            empresaRecuperada.setEstado(0);
            empresaRecuperada.setUsuaDelet(Constants.AUDIT_ADMIN);
            empresaRecuperada.setDateDelet(getTimestamp());

            //Response
            baseResponse.setCode(Constants.CODE_OK);
            baseResponse.setMessage(Constants.MSJ_OK);
            baseResponse.setEntidad(Optional.of(empresaRepository.save(empresaRecuperada)));
        }else{
            baseResponse.setCode(Constants.CODE_EMPRESA_NO_EXIST);
            baseResponse.setMessage(Constants.MSJ_EMPRESA_NO_EXIST);
            baseResponse.setEntidad(Optional.empty());
        }
        return ResponseEntity.ok(baseResponse);
    }
    @Override
    public ResponseEntity<BaseResponse> obtenerEmpresaXNumDoc(String numDocu) {
        return null;
    }

}
