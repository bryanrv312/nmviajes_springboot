package com.nmviajes.app.servicio;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.Optional;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
//import org.apache.poi.ss.util.CellAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.nmviajes.app.entidad.Role;
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
	
	private Usuario usuarioBloqueado;

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

	@Override
	public void guardarUsuarioBloqueado(Usuario usuario) {
		this.usuarioBloqueado = usuario;
		
	}

	@Override
	public Usuario getUsuarioBloqueado() {
		return this.usuarioBloqueado;
	}

	@Override
	public ByteArrayInputStream exportAllData() throws Exception {
		
		String[] columns = {"id", "Usuario", "Apellido", "Correo", "Rol", "Status"};
		
		//creando ueva libro
		Workbook workbook = new HSSFWorkbook();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		
		Sheet sheet = workbook.createSheet("Clientes");//creamos una nueva hoja llamada Clientes
		
		Row row_titulo = sheet.createRow(0);
		Cell cell_titulo = row_titulo.createCell(0);
		cell_titulo.setCellValue("REPORTE DE USUARIOS");
		
		Row row_user = sheet.createRow(1);
		Cell cell_user = row_user.createCell(0);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		cell_user.setCellValue("Descargado por: " + username);
		
		Row row_fecha_hora = sheet.createRow(2);
		Cell cell_fecha_hora = row_fecha_hora.createCell(0);
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	    String formattedDateTime = now.format(formatter);
	    cell_fecha_hora.setCellValue("Fecha y hora: " + formattedDateTime);
	    
	 // Crear un estilo de celda y establecer negrita
	    CellStyle style = workbook.createCellStyle();
	    Font font = workbook.createFont();
	    font.setBold(true);
	    font.setFontHeightInPoints((short) 14); // Tamaño de la fuente
	    style.setFont(font);
	    style.setAlignment(HorizontalAlignment.CENTER);
	    style.setVerticalAlignment(VerticalAlignment.CENTER);

	    // Aplicar el estilo
	    cell_fecha_hora.setCellStyle(style);
	    cell_user.setCellStyle(style);
	    cell_titulo.setCellStyle(style);
	    
	    // Combinar las celdas
	    sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columns.length - 1));
	    sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, columns.length - 1));
	    sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, columns.length - 1));
	    sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, columns.length - 1));
		
		
		Row row = sheet.createRow(4);//creamos registro en posicion 4
		//creando celdas para encabezado
		for(int i=0; i<columns.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(columns[i]);
			// Ajustar el ancho de la columna automáticamente
		    sheet.autoSizeColumn(i);
		}
		
		List<Usuario> lista_usuarios = repo.findAll();
		int initRow = 5;//fila inicial
		
		for (Usuario usuario : lista_usuarios) {
			row = sheet.createRow(initRow);
			row.createCell(0).setCellValue(usuario.getId());
			row.createCell(1).setCellValue(usuario.getUsername());
			row.createCell(2).setCellValue(usuario.getApellido());
			row.createCell(3).setCellValue(usuario.getEmail());
			for(Role role:usuario.getRoles()) {
				row.createCell(4).setCellValue(role.getAuthority());
			}
			row.createCell(5).setCellValue(usuario.getEnabled() ? "HABILITADO" : "DESHABILITADO");
			
			// Ajustar el ancho de la columna automáticamente para todas las celdas de esta fila
		    for (int i = 0; i < columns.length; i++) {
		        sheet.autoSizeColumn(i);
		    }
			
			
			initRow++;
		}
		
		workbook.write(stream);
		workbook.close();
		//obtener el outputstream y transformarlo en inputstream
		return new ByteArrayInputStream(stream.toByteArray());
	}

}
