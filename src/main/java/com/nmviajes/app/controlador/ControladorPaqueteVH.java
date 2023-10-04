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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.nmviajes.app.entidad.PaqueteTuristico;
import com.nmviajes.app.entidad.Vuelo;
import com.nmviajes.app.modelo.DetallePagoDTO;
import com.nmviajes.app.servicio.PaqueteTuristicoServicioImpl;
import com.nmviajes.app.servicio.VueloServicioImpl;

//@Secured({"ROLE_ADMIN","ROLE_USER"})
@Controller
public class ControladorPaqueteVH {
	
	
	@Autowired
    private PaqueteTuristicoServicioImpl servicePaquete;
	
	@Autowired
	private VueloServicioImpl serviceVuelo;
	
	
	/*@GetMapping("/paqueteHV")
	public String listarPaqueteHV(Model model) {
		List<PaqueteTuristico> listaPaquete = servicePaquete.listarPaqueteTuristicos();
		PaqueteTuristico search_paquete = new PaqueteTuristico();
		
		model.addAttribute("listaPaquete", listaPaquete);
		model.addAttribute("search_paquete", search_paquete);//th:object
		
		return "paquete";
	}
	*/
	
	
	
	@GetMapping("/verPaqueteHV/{id}")
	public String verPaqueteHV(@PathVariable Long id, Model model) {
		PaqueteTuristico pt =  servicePaquete.buscarPaqueteTuristicoPorId(id);
		System.out.println("ID Paquete: " + pt.getId() + " " + pt.getVuelo().getAerolinea());
		model.addAttribute("paquete", pt);
		return "paqueteHVcantidad";
	}
	
	
	/*@PostMapping("/cartPaquete")
	public String verCartPaquete(@RequestParam Long id, Model model) {
		System.out.println("(cart) ID paquete: " + id);
		
		DetallePagoDTO detallePagoDTO = new DetallePagoDTO();
		PaqueteTuristico paquete = servicePaquete.buscarPaqueteTuristicoPorId(id);
		
		double sumaTotal=0;
		sumaTotal = paquete.getPrecio()*1;
		
		detallePagoDTO.setCantidad(1);
		detallePagoDTO.setPrecio(paquete.getPrecio());
		detallePagoDTO.setNombre("Paquete Turistico " + paquete.getId());
		detallePagoDTO.setTotal(sumaTotal);
		
		detalles.add(detallePagoDTO);
		
		model.addAttribute("detalle", detallePagoDTO);
		
		return "redirect:/";
	}*/

}
