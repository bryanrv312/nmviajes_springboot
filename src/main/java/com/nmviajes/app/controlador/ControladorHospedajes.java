package com.nmviajes.app.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.nmviajes.app.entidad.Hospedaje;
import com.nmviajes.app.entidad.Vuelo;
import com.nmviajes.app.modelo.HospedajeDTO;
import com.nmviajes.app.servicio.HospedajeServicioImpl;

@Secured({"ROLE_ADMIN","ROLE_USER"})
@Controller
public class ControladorHospedajes {

    @Autowired
	private HospedajeServicioImpl servicio;

    @GetMapping("/gestion_hoteles")
	public String listarHoteles(Model model) {
		Hospedaje hospedaje = new Hospedaje();
        List<Hospedaje> p = servicio.listarHospedaje();
		model.addAttribute("user",p);
		model.addAttribute("hospedaje",hospedaje);
		return "gestion_hoteles";
	}

	@ModelAttribute("hospedaje")
	public HospedajeDTO retornarNuevoHospedajeDTO() {
		return new HospedajeDTO();
	}

	@PostMapping("/hospedajeRegistro")
	public String registrarHospedaje(@ModelAttribute("hospedaje") HospedajeDTO registroDTO){
		servicio.guardar(registroDTO);
		return "gestion_admin";
	}

	@GetMapping("/hospedaje/eliminar/{id}")
	public String eliminarFormularioHospedaje(@PathVariable("id") Long id ) {
		servicio.eliminarHospedaje(id);
		return "redirect:/gestion_hoteles";
	}
	

	@GetMapping("/hospedaje/editar/{id}")
	public String mostrarFormularioModificarUsuario(@PathVariable("id") Long id , Model modelo) {
		Hospedaje p = servicio.buscarUsuarioPorId(id);//encontramos y obtenemos
		
		modelo.addAttribute("hospedaje",p);
		return "gestion_hoteles_editar";
	}

	@PostMapping("/hospedajeRegistroEditado")
	public String registrarHospedajeEditado(@ModelAttribute("hospedaje") HospedajeDTO registroDTO){
		servicio.guardarEditado(registroDTO);
		return "gestion_admin";
	}
	
	
	/*@ModelAttribute
	public void setGenericos2(Model model) {
		Vuelo hospedajeSearch = new Vuelo();
		model.addAttribute("search_hospedaje", hospedajeSearch);//ese es el th:object en el form
	}*/
	
	@GetMapping("/search_hospedaje")
	public String buscarHospedaje(@ModelAttribute("search_hospedaje") Vuelo vuelo, Model model) {
		System.out.println("busqueda ciudad hospedaje: " + vuelo.getDestino());
		//Example<Hospedaje> example = Example.of(hospedaje);
		//List<Hospedaje> listaHospedajes = servicio.buscarHotelesPorUbicacion();
		
		return "armar_paquete";
	}

	
	
}
