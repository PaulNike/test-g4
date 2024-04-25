package com.codigo.unittest.dao;

import com.codigo.unittest.entity.Empresa;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa,Long> {
    boolean existsByNumeroDocumento(String numeroDocumento);

    boolean existsByRazonSocial(String razonSocial);
}
