package com.nmviajes.app.controlador;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nmviajes.app.entidad.Pago;
import com.nmviajes.app.servicio.DetallePagoServicio;
import com.nmviajes.app.servicio.PagoServicioImpl;

//HOLA
//BUENAS

@Controller
public class Controlador {
	@Autowired
	private DetallePagoServicio detallePagoDTOImple;
	
	@Autowired 
	private PagoServicioImpl servicePago;
	
	
	
	@GetMapping("/")
	public String paginaPrincipal(Model model) {
		Pago p = servicePago.retornarPagoEnMemoria();
		
		if(p != null) {
			p.setMensajePago("Pago Completado !!!");
		}
		
		Pago pago = servicePago.buscarPorOrderId(p == null ? "" : p.getOrderId());	
		System.out.println(pago);
		model.addAttribute("pago", pago == null ? new Pago() : p); // si es null manda nulo, si no manda el valor de p
		
		return "index";
	}
	
	@GetMapping("/nosotros")
	public String paginaNostros() {
		return "nosotros";
	}
	
	@GetMapping("/oficinas")
	public String paginaOficinas() {
		return "oficinas";
	}
	
	
	
	
	@GetMapping("/ofertas")
	public String paginaOfertas() {
		return "ofertas";
	}
	
	
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/gestion_admin")
	public String paginaGestionAdministrador() {

		return "gestion_admin";
	}

	/*@GetMapping("/gestion_admin")
	public String paginaGestionVuelo() {
		
		return "gestion_vuelos";
	}*/

	
}
