package com.nmviajes.app.repositorio;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.nmviajes.app.entidad.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long>{

	public Usuario findByUsername(String username);
	
	
	@Query("select p from Usuario p where p.username = ?1")
	public Usuario findByName(String name);
		

}
	