package com.nmviajes.app.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nmviajes.app.entidad.Role;
import com.nmviajes.app.repositorio.RolesRepo;

@Service
public class RoleServicioImpl implements RolesServicio{

	@Autowired
	private RolesRepo repo;
	
	@Override
	public Role save(Role registroDTO) {
		return repo.save(registroDTO);
	}

	@Override
	public List<Role> listarRoles() {
		return repo.findAll();
	}

	@Override
	public Role buscarRole(Long id) {
		return repo.findById(id).get();
	}

}
