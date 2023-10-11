package com.nmviajes.app.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nmviajes.app.entidad.Usuario;


@Repository
public interface UsuarioRepo extends JpaRepository<Usuario,Long>{
	//public User findByUsernameAndPassword(String username , String password);
	
	Usuario findByToken(String token);
	
	Usuario findByUsername(String username);
	
	
	// Define la consulta personalizada utilizando JPQL
    /*@Query("SELECT u FROM Usuario u WHERE u.token = :token")
    Usuario findByToken(String token);*/
}
