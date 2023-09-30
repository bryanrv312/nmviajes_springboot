package com.nmviajes.app.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.nmviajes.app.entidad.Role;
import com.nmviajes.app.entidad.Usuario;
import com.nmviajes.app.modelo.UsuarioRegistroDTO;
import com.nmviajes.app.servicio.RolesServicio;
import com.nmviajes.app.servicio.UsuarioServicio;

@Controller
public class ControladorRegistroUsuarios {
	
	@Autowired
	private UsuarioServicio service;
	
	@Autowired
	private UsuarioServicio uServicio;
	
	@Autowired
	private RolesServicio rolServicio;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("/registro")
	public String register(Model model) {
		Usuario user = new Usuario();
		model.addAttribute("user",user);
		
		return "registro";
	}
	
	
	@PostMapping("/registrarUsuario")
	public String registrarUsuario(@ModelAttribute("user") Usuario registroDTO) {
	
		registroDTO.setEnabled(true);
		//registroDTO.setNombre("patrick");
		registroDTO.setPassword(
				passwordEncoder.encode(registroDTO.getPassword())
				);
		
		Role role = new Role();
		role.setAuthority("ROLE_USER");
		
		role.setUser(registroDTO);
		
		rolServicio.save(role);
		service.guardar(registroDTO);
		
		
//		UsuarioRegistroDTO usuarioSecundary =
//				new UsuarioRegistroDTO(registroDTO.getNombre(),
//						registroDTO.getApellido(),
//						registroDTO.getEmail(),
//						registroDTO.getPassword());
		
		
		//uServicio.save(usuarioSecundary);
		//service.guardar(registroDTO);
		
		
		
		return "redirect:/";
	}
	

}
