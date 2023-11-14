package com.nmviajes.app.controlador;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nmviajes.app.entidad.Hospedaje;
import com.nmviajes.app.entidad.PaqueteTuristico;
import com.nmviajes.app.entidad.Vuelo;
import com.nmviajes.app.modelo.HospedajeDTO;
import com.nmviajes.app.servicio.HospedajeServicioImpl;
import com.nmviajes.app.servicio.PaqueteTuristicoServicioImpl;
import com.nmviajes.app.servicio.VueloServicioImpl;
import com.nmviajes.app.servicio.utils.Utileria;

@Secured({"ROLE_ADMIN","ROLE_USER"})
@Controller
public class ControladorHospedajes {

    @Autowired
	private HospedajeServicioImpl servicio;
    
    @Autowired
    private PaqueteTuristicoServicioImpl servicePaquete;
    
    @Autowired
    private VueloServicioImpl serviceVuelo;
    
    
    @GetMapping("/armar_hoteles")
    public String armar_hoteles(Model model) {
    	
    	List<Hospedaje> lista_hoteles = servicio.listarHospedaje();
    	List<Vuelo> lista_vuelos = serviceVuelo.listarVuelo();
    	Vuelo vue = new Vuelo();
    	
    	model.addAttribute("hospedaje",lista_hoteles);
    	model.addAttribute("search_hospedaje_vuelo",vue);
    	model.addAttribute("vuelo_hoteles", lista_vuelos);
    	
    	return "armar_hoteles";
    }

    @GetMapping("/gestion_hoteles")
	public String listarHoteles(Model model) {
		Hospedaje hospedaje = new Hospedaje();
        List<Hospedaje> p = servicio.listarHospedaje();
		model.addAttribute("user",p);
		model.addAttribute("hospedajeObject",hospedaje);//th:object de nuevo hospedaje
		return "gestion_hoteles";
	}

	@ModelAttribute("hospedaje")
	public HospedajeDTO retornarNuevoHospedajeDTO() {
		return new HospedajeDTO();
	}

	@PostMapping("/hospedajeRegistro")
	public String registrarHospedaje(@ModelAttribute("hospedajeObject") Hospedaje hos, RedirectAttributes flash,
									 @RequestParam("archivoImagen") MultipartFile multiPart	){
		
		System.err.println(hos);
		
		if (!multiPart.isEmpty()) {
			String ruta = "c:/nmviajes/img-hoteles/";
			String nombreImagen = Utileria.guardarArchivo(multiPart, ruta);
			if (nombreImagen != null) { // La imagen si se subio
				hos.setImagen(nombreImagen);
			}
		}
		
		servicio.guardarHospedaje(hos);
		flash.addFlashAttribute("msg","Hotel registrado correctamente !!");
		return "redirect:/gestion_hoteles";
	}

	@GetMapping("/hospedaje/eliminar/{id}")
	public String eliminarFormularioHospedaje(@PathVariable("id") Long id, RedirectAttributes flash) {
		servicio.eliminarHospedaje(id);
		flash.addFlashAttribute("msg","Hotel Eliminado correctamente !!");
		return "redirect:/gestion_hoteles";
	}
	

	@GetMapping("/hospedaje/editar/{id}")
	public String mostrarFormularioModificarUsuario(@PathVariable("id") Long id , Model modelo) {
		Hospedaje hos = servicio.buscarUsuarioPorId(id);//encontramos y obtenemos
		
		modelo.addAttribute("hospedaje",hos);
		return "gestion_hoteles_editar";
	}

	@PostMapping("/hospedajeRegistroEditado")
	public String registrarHospedajeEditado(@ModelAttribute("hospedaje") HospedajeDTO registroDTO, RedirectAttributes flash){
		servicio.guardarEditado(registroDTO);
		
		flash.addFlashAttribute("msg","Hotel editado correctamente !!");
		return "redirect:/gestion_hoteles";
	}
	
	//Excel
	@GetMapping("/export/allHospedajes")
	public ResponseEntity<InputStreamResource> descargarHospedajes() throws Exception{
		ByteArrayInputStream stream = servicio.exportAllHospedajes();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=hoteles.xls");
		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
	}
	
	
	/*@ModelAttribute
	public void setGenericos2(Model model) {
		Vuelo hospedajeSearch = new Vuelo();
		model.addAttribute("search_hospedaje", hospedajeSearch);//ese es el th:object en el form
	}*/
	
	/*@PostMapping("/search_3")
	public String buscarHospedaje(@ModelAttribute("search_hospedaje") Vuelo vuelo, Model model) {
		System.out.println("busqueda ciudad hospedaje: " + vuelo.getDestino());
		//Example<Hospedaje> example = Example.of(hospedaje);
		//List<Hospedaje> listaHospedajes = servicio.buscarHotelesPorUbicacion();
		
		return "armar_paquete";
	}*/

	
	
	/********************************* GESTION PAQUETE TURISTICO *******************************************************/
	
	@Secured({ "ROLE_ADMIN" })
	@GetMapping("/gestion_paqueteTuristico")
	public String listarPaquetes(Model model) {

		List<PaqueteTuristico> listaPaquete = servicePaquete.listarPaqueteTuristicos();
		List<Vuelo> listaVuelo = serviceVuelo.listarVuelo();
		List<Hospedaje> listaHospedaje = servicio.listarHospedaje();
		PaqueteTuristico paquete = new PaqueteTuristico();

		model.addAttribute("listaPaquete", listaPaquete);
		model.addAttribute("listaVuelo", listaVuelo);
		model.addAttribute("listaHospedaje", listaHospedaje);
		model.addAttribute("paqueteObject", paquete);//th:objct-nuevoPaquete

		return "gestionPaqueteTuristico";
	}
	
	
	@PostMapping("/paqueteRegistro")
	public String registrarPaquete(@ModelAttribute("paqueteObject") PaqueteTuristico paquete, RedirectAttributes flash){
		System.err.println(paquete.getVuelo());
		System.err.println(paquete.getHospedaje());
		System.err.println(paquete);
		servicePaquete.guardar(paquete);
		flash.addFlashAttribute("msg","Paquete registrado correctamente !!");
		return "redirect:/gestion_paqueteTuristico";
	}
	
	
	@GetMapping("/paquete/editar/{id}")
	public String mostrarFormularioEditarPaquete(@PathVariable("id") Long id , Model modelo) {
		PaqueteTuristico pq = servicePaquete.buscarPaqueteTuristicoPorId(id);
		
		List<Vuelo> listaVuelo = serviceVuelo.listarVuelo();
		List<Hospedaje> listaHospedaje = servicio.listarHospedaje();
		
		modelo.addAttribute("listaVuelo", listaVuelo);
		modelo.addAttribute("listaHospedaje", listaHospedaje);
		modelo.addAttribute("paqueteObject", pq);//object for form edit
		return "gestion_paqueteTuristico_editar";
	}
	
	
	@PostMapping("/paqueteRegistroEditado")
	public String registrarPaqueteEditado(@ModelAttribute("paqueteObject") PaqueteTuristico paquete, RedirectAttributes flash){
		servicePaquete.guardarEditado(paquete);
		flash.addFlashAttribute("msg","Paquete editado correctamente !!");
		return "redirect:/gestion_paqueteTuristico";
	}
	
	
	@GetMapping("/paquete/eliminar/{id}")
	public String eliminarPaqueteTuristico(@PathVariable("id") Long id, RedirectAttributes flash) {
		servicePaquete.eliminarPaquete(id);
		flash.addFlashAttribute("msg","Paquete Eliminado correctamente !!");
		return "redirect:/gestion_paqueteTuristico";
	}
	
	
	//Excel
	@GetMapping("/export/allPaquetes")
	public ResponseEntity<InputStreamResource> descargarPaquetes() throws Exception{
		ByteArrayInputStream stream = servicePaquete.exportAllPaquetes();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=paquetesTuristicos.xls");
		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
	}
	
}
