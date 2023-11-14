package com.nmviajes.app.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nmviajes.app.entidad.Hospedaje;

@Repository
public interface HospedajeRepo extends JpaRepository<Hospedaje,Long> {
	
    /*@Query("SELECT ubicacion FROM Hospedajes WHERE ubicacion LIKE '%cuzco%'")
	List<Hospedaje> findByUbicacion(String ubicacion);*/
	
	
	List<Hospedaje> findByUbicacionContaining(String ciudad);
    
}
 