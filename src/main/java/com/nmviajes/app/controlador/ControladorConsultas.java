package com.nmviajes.app.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.nmviajes.app.entidad.DetalleUsuario;
import com.nmviajes.app.modelo.DetalleUsuarioDTO;
import com.nmviajes.app.servicio.DetalleUsuarioServicioImpl;

@Controller
public class ControladorConsultas {
    @Autowired
    private DetalleUsuarioServicioImpl detalleUsuarioServicioImpl;

    @GetMapping("/ayuda")
	public String paginaAyuda(Model model ) {
        DetalleUsuario detalleusuario = new DetalleUsuario();

        model.addAttribute("detalleusuario", detalleusuario);
		return "ayuda";
	}

    @ModelAttribute("detalleusuario")
	public DetalleUsuarioDTO retornarNuevoDetallleUsariojeDTO() {
		return new DetalleUsuarioDTO();
	}
/*
 * @PostMapping("/registrarUsuario")
	public String registrarUsuario(@ModelAttribute("user") UsuarioRegistroDTO registroDTO) {
			service.save(registroDTO);
		return "iniciar_sesion";
	}
 * 
 */
    @PostMapping("/registrarConsulta")
    public String obtenerConsultas(@ModelAttribute("detalleusuario") DetalleUsuarioDTO registroDTO) {
        detalleUsuarioServicioImpl.guardar(registroDTO);

		return "ayuda";
	}


}
