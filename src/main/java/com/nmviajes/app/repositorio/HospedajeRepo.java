package com.nmviajes.app.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nmviajes.app.entidad.Hospedaje;

@Repository
public interface HospedajeRepo extends JpaRepository<Hospedaje,Long> {
    
}
