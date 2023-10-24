package com.nmviajes.app.servicio;

import java.util.ArrayList;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nmviajes.app.entidad.Usuario;
import com.nmviajes.app.modelo.UsuarioRegistroDTO;
import com.nmviajes.app.repositorio.IUsuarioDao;
import com.nmviajes.app.repositorio.UsuarioRepo;

@Service
public class UsuarioServicioImpl implements UsuarioServicio {
	
	@Autowired
	private  IUsuarioDao usuarioRepo;

	@Autowired
	private UsuarioRepo repo;
	private Usuario u;

	@Override
	public Usuario save(UsuarioRegistroDTO registroDTO) {
		Usuario usuario = new Usuario(registroDTO.getNombre(), registroDTO.getApellido(),
				registroDTO.getEmail(),
				registroDTO.getPassword());
		return repo.save(usuario);

	}

	@Override
	public List<Usuario> listarUsuarios() {
		return repo.findAll();

	}


	@Override
	public boolean siRegistradoExiste(UsuarioRegistroDTO u) {

		List<Usuario> us = repo.findAll();
		for (int i = 0; i < us.size(); i++) {
			
			if (buscarUsuariosNombrePassword(u) != null)
				{if (buscarUsuariosNombrePassword(u).getRol().equals("ROL_USER"))
					return true;
				}
		}
		return false;
	}

	@Override
	public List<Usuario> listAll() {
		return repo.findAll();
	}

	@Override
	public boolean siUsuario(UsuarioRegistroDTO u) {

		List<Usuario> us = repo.findAll();
		for (int i = 0; i < us.size(); i++) {
			if (buscarUsuariosNombrePassword(u) != null)
				{if (buscarUsuariosNombrePassword(u).getRol().equals("ROL_ADMIN"))
					return true;
				}
		}
		return false;
	}
	@Override
	public void setUsuario(Usuario registroDTO){

		//if(registroDTO!=null){
		Usuario usuario = new Usuario(registroDTO.getId(),registroDTO.getNombre(), registroDTO.getApellido(),
		registroDTO.getEmail(),
		registroDTO.getPassword());
			this.u=usuario;
		//}
		//this.u=null;
	}
	@Override
	public Usuario getUsuario(){
		return this.u;
	}
	
	@Override
	public Usuario IdUsuario(UsuarioRegistroDTO u){
		
			List<Usuario> us = repo.findAll();
			for (int i = 0; i < us.size(); i++) {
				
				if (buscarUsuariosNombrePassword(u) != null)
					{if (buscarUsuariosNombrePassword(u).getRol().equals("ROL_USER"))
						return buscarUsuariosNombrePassword(u);
					}
			}
		
		return null;
	}

	@Override
	public Usuario buscarUsuariosNombreApellido(UsuarioRegistroDTO u) {
		List<Usuario> us = repo.findAll();
		Usuario p = new Usuario();
		// Long id, String nombre, String apellido, String email, String password
		for (int i = 0; i < us.size(); i++) {
			if ((us.get(i).getNombre()).equals(u.getNombre()) && (us.get(i).getApellido()).equals(u.getApellido())) {
				return new Usuario(us.get(i).getId(), us.get(i).getNombre(), us.get(i).getApellido(),
						us.get(i).getEmail(), us.get(i).getPassword(), us.get(i).getRol(), us.get(i).getToken(), us.get(i).isConfirmado());
			}
		}
		return null;
	}

	@Override
	public Usuario buscarUsuariosNombrePassword(UsuarioRegistroDTO u) {
		List<Usuario> us = repo.findAll();
		Usuario p = new Usuario();
		// Long id, String nombre, String apellido, String email, String password
		for (int i = 0; i < us.size(); i++) {
			if ((us.get(i).getNombre()).equals(u.getNombre()) && (us.get(i).getPassword()).equals(u.getPassword())) {
				return new Usuario(us.get(i).getId(), us.get(i).getNombre(), us.get(i).getApellido(),
						us.get(i).getEmail(), us.get(i).getPassword(), us.get(i).getRol(), us.get(i).getToken(), us.get(i).isConfirmado());
			}
		}
		return null;
	}

	@Override
	public void eliminarUsuarioPorId(Long id) {
		repo.deleteById(id);
	}

	@Override
	public Usuario buscarUsuarioPorId(Long id) {
		Usuario u = repo.findById(id).get();
		return u;
	}

	@Override
	public Usuario guardar(Usuario registroDTO) {
		return usuarioRepo.save(registroDTO);
	}

	@Override
	public Usuario buscaUsurio(String registroDTO) {
		return usuarioRepo.findByName(registroDTO);
	}

	@Override
	public Usuario buscarPorToken(String token) {
		return repo.findByToken(token);
	}

	@Override
	public Usuario buscarPorUsername(String username) {
		return repo.findByUsername(username);
	}

	@Override
	public Usuario guardarEditado(Usuario usuario) {
		Optional<Usuario> usuarioExistente = repo.findById(usuario.getId());
		
		if(usuarioExistente.isPresent()) {
			Usuario usuarioActualizado = usuarioExistente.get();
	        usuarioActualizado.setNombre(usuario.getNombre());
	        usuarioActualizado.setApellido(usuario.getApellido());
	        usuarioActualizado.setUsername(usuario.getUsername());
	        usuarioActualizado.setEmail(usuario.getEmail());
	        
	        return repo.save(usuarioActualizado);
	        
		} else {
	        return null;
	    }
	}

	@Override
	public Usuario guardarEditado_correo(Usuario usuario) {
		Optional<Usuario> usuarioExistente = repo.findById(usuario.getId());
				
		if(usuarioExistente.isPresent()) {
			Usuario usuarioActualizado = usuarioExistente.get();
	        usuarioActualizado.setNombre(usuario.getNombre());
	        usuarioActualizado.setApellido(usuario.getApellido());
	        usuarioActualizado.setUsername(usuario.getUsername());
	        usuarioActualizado.setEmail(usuario.getEmail());
	        usuarioActualizado.setEnabled(usuario.getEnabled());
	        usuarioActualizado.setToken(usuario.getToken());
	        
	        return repo.save(usuarioActualizado);
	        
		} else {
	        return null;
	    }
	}

}
