package com.nmviajes.app.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nmviajes.app.entidad.Pago;

@Repository
public interface PagRepo extends JpaRepository<Pago,Long> {
	
	
	@Query("select p from Pago p where p.orderId = ?1")
	Pago findByOrderId(String orderId);
    
}
