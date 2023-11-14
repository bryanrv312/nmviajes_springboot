package com.nmviajes.app.servicio;
 
import java.io.ByteArrayInputStream;
import java.util.List;

import com.nmviajes.app.entidad.Hospedaje;
import com.nmviajes.app.entidad.Usuario;
import com.nmviajes.app.modelo.HospedajeDTO;
import com.nmviajes.app.modelo.UsuarioRegistroDTO;

public interface UsuarioServicio {
	/*public void regiserUser(User user);*/
	public Usuario save(UsuarioRegistroDTO registroDTO );
	public List<Usuario> listarUsuarios();
	public boolean siRegistradoExiste(UsuarioRegistroDTO u);
	public boolean siUsuario(UsuarioRegistroDTO u);
	public List<Usuario> listAll();
	public Usuario buscarUsuariosNombreApellido(UsuarioRegistroDTO u);
	public Usuario buscarUsuariosNombrePassword(UsuarioRegistroDTO u);
	public void eliminarUsuarioPorId(Long id); 
	public Usuario buscarUsuarioPorId(Long id); 
	public Usuario IdUsuario(UsuarioRegistroDTO u);
	public void setUsuario(Usuario registroDTO);
	
	public Usuario getUsuario();
	//public User login(String name,String password);
	
	//
	public Usuario guardar(Usuario registroDTO );
	
	public Usuario buscaUsurio(String registroDTO );
	
	public Usuario buscarPorToken(String token);
	
	public Usuario buscarPorUsername(String username);
	
	public Usuario guardarEditado(Usuario usuario);
	
	public Usuario guardarEditado_correo(Usuario usuario);
	
	public void guardarUsuarioBloqueado(Usuario usuario);
	
	public Usuario getUsuarioBloqueado();
	
	ByteArrayInputStream exportAllData() throws Exception;
}
