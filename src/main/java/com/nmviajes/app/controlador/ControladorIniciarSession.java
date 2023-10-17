package com.nmviajes.app.controlador;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	
	@GetMapping("/iniciar_sesion")
	public String iniciarSesion(
			@RequestParam(value="error", required=false) String error, //obtenemos el error que nos envia SS cuando ingresa mal el usario
			@RequestParam(value="logout", required = false) String logout,
			Model model,Principal principal,RedirectAttributes flash) {
		
		if(principal!=null){
			System.out.println("ya inicio sesion anteiormente");
			return "redirect:/";
		}
		
		if(error!=null) {
			System.out.println("Error en el login ingresa los datos coreectos");
			flash.addFlashAttribute("msg", "Ingrese correctamente sus credenciales");
			return "redirect:/iniciar_sesion";
		}
		if(logout != null) {
			model.addAttribute("success", "Ha cerrado sesión con éxito!");
		}
		return "iniciar_sesion";
	}
	
}
