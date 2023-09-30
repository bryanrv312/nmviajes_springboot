package com.nmviajes.app.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nmviajes.app.entidad.DetalleUsuario;

@Repository
public interface DetalleUsuarioRepo extends JpaRepository<DetalleUsuario,Long>{
    
}
