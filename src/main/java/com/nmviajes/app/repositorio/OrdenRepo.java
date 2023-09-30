package com.nmviajes.app.repositorio;

import org.springframework.stereotype.Repository;

import com.nmviajes.app.entidad.Orden;

import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface OrdenRepo  extends JpaRepository<Orden,Integer>{
    
}
