package com.nmviajes.app.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nmviajes.app.entidad.Vuelo;

@Repository
public interface VueloRepo extends JpaRepository<Vuelo,Long>{
    
}
