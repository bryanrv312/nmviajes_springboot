package com.nmviajes.app.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nmviajes.app.entidad.PaqueteTuristico;

@Repository
public interface PaqueteTuristicoRepo extends JpaRepository<PaqueteTuristico,Long>{
    
	 List<PaqueteTuristico> findByVueloOrigen(String origen);
	 List<PaqueteTuristico> findByVueloDestino(String destino);
	 List<PaqueteTuristico> findByVueloOrigenAndVueloDestino(String origen, String destino);
	
}
