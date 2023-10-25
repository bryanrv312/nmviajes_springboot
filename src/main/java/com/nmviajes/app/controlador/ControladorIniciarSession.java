package com.nmviajes.app.controlador;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nmviajes.app.entidad.Usuario;
import com.nmviajes.app.modelo.UsuarioRegistroDTO;
import com.nmviajes.app.servicio.UsuarioServicio;


@Controller
public class ControladorIniciarSession {

	
	@Autowired
	private UsuarioServicio usuarioServicio;
	
	
	
	
	/*@PostMapping("/get_username")
	public String capturar_username(@ModelAttribute("username") String user){
		System.err.println("user capturado: " + user);
		return "";
	}*/
	
	

	
	@GetMapping("/iniciar_sesion")
	public String iniciarSesion(
			@RequestParam(value="error", required=false) String error, //obtenemos el error que nos envia SS cuando ingresa mal el usario
			@RequestParam(value="logout", required = false) String logout,
			Model model,Principal principal,RedirectAttributes flash, HttpServletRequest request) {	

		Usuario usuario_bloqueado = usuarioServicio.getUsuarioBloqueado() == null ? new Usuario("nombre_b", "apellido_b") : usuarioServicio.getUsuarioBloqueado();
		
		if(principal!=null){
			System.out.println("ya inicio sesion anteiormente");
			return "redirect:/";
		}
		
		if(error!=null) {
		
			if("nombre_b".equals(usuario_bloqueado.getNombre()) && "apellido_b".equals(usuario_bloqueado.getApellido())) {
				System.out.println("Error en el login ingresa los datos coreectos");	
				flash.addFlashAttribute("msg", "Ingrese correctamente sus credenciales");
			}else if(!usuario_bloqueado.getEnabled()) {
				System.err.println("******************************************************* ESTA BLOQUEADO" );
				flash.addFlashAttribute("msg_bloqueado", "Usuario bloqueado");
			}
			
			return "redirect:/iniciar_sesion";
		}
		
		if(logout != null) {
			model.addAttribute("success", "Ha cerrado sesión con éxito!");
		}
		return "iniciar_sesion";
	}
	
	
	
}
