package com.nmviajes.app.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nmviajes.app.entidad.Usuario;


@Repository
public interface UsuarioRepo extends JpaRepository<Usuario,Long>{
	//public User findByUsernameAndPassword(String username , String password);
	
}
