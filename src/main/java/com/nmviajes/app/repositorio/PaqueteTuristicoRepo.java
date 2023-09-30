package com.nmviajes.app.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nmviajes.app.entidad.PaqueteTuristico;

@Repository
public interface PaqueteTuristicoRepo extends JpaRepository<PaqueteTuristico,Long>{
    

	
}
