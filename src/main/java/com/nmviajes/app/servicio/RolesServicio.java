package com.nmviajes.app.servicio;

import java.util.List;

import com.nmviajes.app.entidad.Role;

public interface RolesServicio {
	public Role save(Role registroDTO );
	public List<Role> listarRoles();
	public Role buscarRole(Long id);
}
