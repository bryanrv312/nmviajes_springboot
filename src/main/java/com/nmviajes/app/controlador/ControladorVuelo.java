package com.nmviajes.app.controlador;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nmviajes.app.PaypalService;
import com.nmviajes.app.entidad.Hospedaje;
import com.nmviajes.app.entidad.Orden;
import com.nmviajes.app.entidad.Pago;
import com.nmviajes.app.entidad.PaqueteTuristico;
import com.nmviajes.app.entidad.Usuario;
import com.nmviajes.app.entidad.Vuelo;
import com.nmviajes.app.modelo.DetallePagoDTO;
import com.nmviajes.app.modelo.UsuarioRegistroDTO;
import com.nmviajes.app.modelo.VueloDTO;
import com.nmviajes.app.repositorio.HospedajeRepo;
import com.nmviajes.app.servicio.DetallePagoDTOImple;
import com.nmviajes.app.servicio.HospedajeServicioImpl;
import com.nmviajes.app.servicio.PagoServicioImpl;
import com.nmviajes.app.servicio.PaqueteTuristicoServicioImpl;
import com.nmviajes.app.servicio.UsuarioServicio;
import com.nmviajes.app.servicio.VueloServicioImpl;
import com.nmviajes.app.servicio.utils.GenerarReportes;
import com.nmviajes.app.servicio.utils.Utileria;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;

import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

 
@Controller
public class ControladorVuelo {
	private final Logger log = LoggerFactory.getLogger(ControladorVuelo.class);
	
	
	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private PagoServicioImpl servicePago;

	@Autowired
	private PaypalService servicePaypal;// 

	@Autowired
	private PaqueteTuristicoServicioImpl servicePaquete;// 

	@Autowired
	private UsuarioServicio uServicio;

	@Autowired
	private VueloServicioImpl servicio;

	@Autowired
	private HospedajeServicioImpl hospedajeServicioImpl;

	@Autowired
	private DetallePagoDTOImple detallePagoDTOImple;

	@Autowired
	private HospedajeServicioImpl hservicio;
	
	@Autowired
	private HospedajeRepo hospedaje_repo;

	@Autowired
	private GenerarReportes jasperReportService;
	
	

	JRBeanCollectionDataSource dataSource ;

	Map<String, Object> parametros;

	List<DetallePagoDTO> detalles = new ArrayList<DetallePagoDTO>();

	private double total = 0;

	private double totalorder = 0;
	
	String destinoFinal = "";//filtrar hoteles por destino(automatico)
	
	public String getDestinoFinal() {
		String destino_final = this.destinoFinal;
		detallePagoDTOImple.addDf(destino_final);
		return destino_final;
	}
	
	//funcion que persiste el List detalles
	public List<DetallePagoDTO> getDetalles() {
		List<DetallePagoDTO> detalles = this.detalles;
		detallePagoDTOImple.agregarDetalle(detalles);
		return detalles;
	}

	@GetMapping("/armar_paquete")
	public String paginaArmarPaquete(Model model) {
		List<Vuelo> p = servicio.listarVuelo();
		List<Hospedaje> a = hospedajeServicioImpl.listarHospedaje();

		// System.out.println("-----------"+ uServicio.getUsuario().getNombre());
		model.addAttribute("vuelo", p);
		model.addAttribute("hospedaje", a);
		
		model.addAttribute("vuelo_hoteles", p);
		
		//para el th:object de form busqueda vuelos
		Vuelo vueloSearch = new Vuelo();
		model.addAttribute("search_vuelo", vueloSearch);
		
		//para el th:object de form busqueda hospedaje
		Vuelo hospedajeSearch = new Vuelo();
		model.addAttribute("search_hospedaje", hospedajeSearch);

		return "armar_paquete";
	}

//	@ModelAttribute
//	public void setGenericos(Model model) {
//		Vuelo vueloSearch = new Vuelo();
//		model.addAttribute("search", vueloSearch);//ese es el th:object en el form
//	}

	@InitBinder // cambia de vacio a null
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	@PostMapping("/search_2")
	public String buscarPorFecha(@ModelAttribute("search_vuelo") Vuelo vuelo, Model model) {
		System.out.println("vuelo fecha partida: " + vuelo.getFechaPartida());
		System.out.println("vuelo fecha regreso: " + vuelo.getFechaRegreso());
		vuelo.setImagen(null);//controlara el valor por defecto de imagen a null para q el example o tome como null
		Example<Vuelo> example = Example.of(vuelo);
		List<Vuelo> listaVuelos = servicio.buscarByExample(example);
		model.addAttribute("vuelo", listaVuelos);
		return "armar_paquete";
	}
	
	
	@PostMapping("/search_3")
	public String buscarHospedaje(@ModelAttribute("search_hospedaje_vuelo") Vuelo vuelo, Model model) {
		System.out.println("busqueda ciudad hospedaje: " + vuelo.getDestino());
		
		//Example<Vuelo> example = Example.of(vuelo);
		List<Hospedaje> listaHospedajes = hservicio.buscarHotelesPorUbicacion(vuelo.getDestino());
		System.out.println("busqueda de hospedaje por ubicacion: " + vuelo.getDestino());
		for(Hospedaje h: listaHospedajes) {
			System.out.println("Nombre del Hospedaje: " + h.getNombre());
	        System.out.println("Ubicación: " + h.getUbicacion());
		}
		
		model.addAttribute("hospedaje", listaHospedajes);
		
		return "armar_hoteles";
	}
	
	
	/*SELECT DEPENDIENTE DE VUELO EN PAQUETES*/
	@GetMapping("/hoteles_select_filtro/{id_vuelo_destino}")
	@ResponseBody
	public List<Hospedaje> getHotelesByOrigen(@PathVariable("id_vuelo_destino") Long id_vuelo) {
		//System.err.println("ORIGEN:" + id_vuelo);
		Vuelo vuelo = servicio.buscarUsuarioPorId(id_vuelo);
		//System.err.println(vuelo);
		
	    return hservicio.buscarHotelesPorUbicacion(vuelo.getDestino()); //eso sera el data, en el success del jquery
	}
	
	@GetMapping("/vuelo_detalle/{id_vuelo}")
	@ResponseBody
	public Vuelo getVueloDetalle(@PathVariable("id_vuelo") Long id_vuelo) {
		Vuelo vuelo = servicio.buscarUsuarioPorId(id_vuelo);
	    return vuelo; //eso sera el data, en el success del jquery
	}
	
	@GetMapping("/hotels_detalle/{id_hotel}")
	@ResponseBody
	public Hospedaje getHotelsDetalle(@PathVariable("id_hotel") Long id_hotel) {
		Hospedaje hospedaje = hservicio.buscarUsuarioPorId(id_hotel);
		System.err.println(hospedaje);
	    return hospedaje; //eso sera el data, en el success del jquery
	}
	
	
	
	@PostMapping("/search_4")
	public String busquedaPaquete(@ModelAttribute("search_paquete") PaqueteTuristico paquete, Model model) {
		System.out.println("paquete fecha partida: " + paquete.getVuelo().getOrigen());
		System.out.println("paquete fecha partida: " + paquete.getVuelo().getDestino());
		
		
		String origen = paquete.getVuelo().getOrigen();
	    String destino = paquete.getVuelo().getDestino();

	    if (origen != null && destino != null) {
	        // Búsqueda por origen y destino
	        List<PaqueteTuristico> paquetesPorOrigenYDestino = servicePaquete.buscarPorOrigenYDestino(origen, destino);
	        model.addAttribute("listaPaquete", paquetesPorOrigenYDestino);
	    } else if (origen != null) {
	        // Búsqueda por origen
	        List<PaqueteTuristico> paquetesPorOrigen = servicePaquete.buscarPorOrigen(origen);
	        model.addAttribute("listaPaquete", paquetesPorOrigen);
	    } else if (destino != null) {
	        // Búsqueda por destino
	        List<PaqueteTuristico> paquetesPorDestino = servicePaquete.buscarPorDestino(destino);
	        model.addAttribute("listaPaquete", paquetesPorDestino);
	    } else {
	        // No se proporcionaron criterios de búsqueda válidos
	        model.addAttribute("listaPaquete", Collections.emptyList());
	    }

		return "paquete";
	}
	
	
	@GetMapping("/paqueteHV")
	public String listarPaqueteHV(Model model) {
		List<PaqueteTuristico> listaPaquete = servicePaquete.listarPaqueteTuristicos();
		PaqueteTuristico search_paquete = new PaqueteTuristico();
		
		model.addAttribute("listaPaquete", listaPaquete);
		model.addAttribute("search_paquete", search_paquete);//th:object
		
		return "paquete";
	}
	/************************  GESTION VUELOS ****************************************/
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/gestion_vuelos")
	public String listarVuelos(Model model) throws ParseException {
		List<Vuelo> p = servicio.listarVuelo();
		Vuelo vuelo = new Vuelo();

		model.addAttribute("user", p);
		model.addAttribute("vueloObject", vuelo);
		return "gestion_vuelos";
	}
	

	@Secured("ROLE_ADMIN")
	@GetMapping("/vuelos/eliminar/{id}")
	public String eliminarUnVuelo(@PathVariable("id") Long id, Model modelo, RedirectAttributes flash) {
		servicio.eliminarVuelo(id);
		flash.addFlashAttribute("msg", "Vuelo eliminado correctamente !!");
		return "redirect:/gestion_vuelos";
	}

	@ModelAttribute("vuelo")
	public VueloDTO retornarNuevoUsuarioRegistroDTO() {
		return new VueloDTO();
	}

	/*@Secured("ROLE_ADMIN")
	@PostMapping("/vueloRegistro")
	public String registrarVuelo(@ModelAttribute("vuelo") VueloDTO registroDTO, RedirectAttributes flash) {
		System.err.println("eNTRE NEW VUELO");
		servicio.guardar(registroDTO);
		
		flash.addFlashAttribute("msg", "Vuelo registrado correctamente !!");
		return "redirect:/gestion_vuelos";
	}*/	
	
	public static boolean validarFechas(LocalDate fechaPartida, LocalDate fechaPartida2, LocalDate fechaRegreso,
			LocalDate fechaRegreso2, LocalTime horaPartida, LocalTime horaPartida2, LocalTime horaRegreso, LocalTime horaRegreso2) {
		// Validaciones
		if (fechaPartida.isAfter(fechaPartida2)) {
			System.out.println("La fecha de partida debe ser anterior a la fecha de llegada del vuelo de ida.");
			return false;
		}

		if (fechaRegreso.isAfter(fechaRegreso2)) {
			System.out.println("La fecha de partida debe ser anterior a la fecha de llegada del vuelo de regreso.");
			return false;
		}

		if (fechaPartida2.isAfter(fechaRegreso)) {
			System.out.println(
					"La fecha de llegada del vuelo de ida debe ser anterior a la fecha de partida del vuelo de regreso.");
			return false;
		}

		if (fechaRegreso.isAfter(fechaRegreso2)) {
			System.out.println(
					"La fecha de llegada del vuelo de regreso debe ser anterior a la fecha de llegada del vuelo de regreso.");
			return false;
		}
		
		if (fechaPartida.equals(fechaPartida2) && horaPartida.isAfter(horaPartida2)) {
            System.out.println("La hora de partida debe ser anterior a la hora de llegada del vuelo de ida.");
            return false;
        }
		
		if (fechaRegreso.equals(fechaRegreso2) && horaRegreso.isAfter(horaRegreso2)) {
            System.out.println("La hora de partida debe ser anterior a la hora de llegada del vuelo de regreso.");
            
            return false;
        }

		// Si todas las validaciones pasan, devuelve true
		return true;
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/vueloRegistro")
	public String registrarVuelo(@ModelAttribute("vueloObject") Vuelo vu, RedirectAttributes flash, 
								 @RequestParam("archivoImagen") MultipartFile multiPart, Model modelo) {
		
		System.err.println("entre");
		System.err.println(vu);
		
		LocalDate fecha_partida = vu.getFechaPartida();
		LocalDate fecha_partida2 = vu.getFechaPartida2();
		LocalDate fecha_regreso = vu.getFechaRegreso();
		LocalDate fecha_regreso2 = vu.getFechaRegreso2();
		
		LocalTime hora_partida = vu.getHoraPartida();
		LocalTime hora_partida2 = vu.getHoraPartida2();
		LocalTime hora_regreso= vu.getHoraRegreso();
		LocalTime hora_regreso2 = vu.getHoraRegreso2();
		
		/*boolean validacionExitosa = validarFechas(fecha_partida, fecha_partida2, fecha_regreso, fecha_regreso2,
				hora_partida, hora_partida2, hora_regreso, hora_regreso2);*/
		
		
		// Puedes usar el resultado para decidir qué hacer en tu aplicación
        /*if (validacionExitosa) {
            System.out.println("Las fechas son válidas.");
        } else {
            System.out.println("Las fechas no son válidas. Realiza alguna acción necesaria.");
            System.err.println("Pipipipipi !!!");
			flash.addFlashAttribute("msgg","Las fechas no son validas !!! ");
		 	return "redirect:/gestion_vuelos?modal=true";
        }*/
		
		if (fecha_partida.isAfter(fecha_partida2)) {
			System.out.println("La fecha de partida debe ser anterior a la fecha de llegada del vuelo de ida.");
			flash.addFlashAttribute("msgg","La fecha de partida debe ser anterior a la fecha de llegada del vuelo de ida.");
		 	return "redirect:/gestion_vuelos?modal=true";
		}

		if (fecha_regreso.isAfter(fecha_regreso2)) {
			System.out.println("La fecha de partida debe ser anterior a la fecha de llegada del vuelo de regreso.");
			flash.addFlashAttribute("msgg","La fecha de partida debe ser anterior a la fecha de llegada del vuelo de regreso. ");
		 	return "redirect:/gestion_vuelos?modal=true";
		}

		if (fecha_partida2.isAfter(fecha_regreso)) {
			System.out.println("La fecha de llegada del vuelo de ida debe ser anterior a la fecha de partida del vuelo de regreso.");
			flash.addFlashAttribute("msgg","La fecha de llegada del vuelo de ida debe ser anterior a la fecha de partida del vuelo de regreso.");
		 	return "redirect:/gestion_vuelos?modal=true";
		}

		if (fecha_regreso.isAfter(fecha_regreso2)) {
			System.out.println("La fecha de llegada del vuelo de regreso debe ser anterior a la fecha de llegada del vuelo de regreso.");
			flash.addFlashAttribute("msgg","La fecha de llegada del vuelo de regreso debe ser anterior a la fecha de llegada del vuelo de regreso.");
		 	return "redirect:/gestion_vuelos?modal=true";
		}
		
		if (fecha_partida.equals(fecha_partida2) && hora_partida.isAfter(hora_partida2)) {
            System.out.println("La hora de partida debe ser anterior a la hora de llegada del vuelo de ida.");
            flash.addFlashAttribute("msgg","La hora de partida debe ser anterior a la hora de llegada del vuelo de ida.");
		 	return "redirect:/gestion_vuelos?modal=true";
        }
		
		if (fecha_regreso.equals(fecha_regreso2) && hora_regreso.isAfter(hora_regreso2)) {
            System.out.println("La hora de partida debe ser anterior a la hora de llegada del vuelo de regreso.");
            flash.addFlashAttribute("msgg","La hora de partida debe ser anterior a la hora de llegada del vuelo de regreso.");
		 	return "redirect:/gestion_vuelos?modal=true";
        }
		
        
        System.err.println("sali del if-else");
		
		
		/*if(fecha_partida.compareTo(fecha_regreso) > 0) {
			System.err.println("Pipipipipi !!!");
			flash.addFlashAttribute("msgg","la fecha partida debe ser menor a la de regreso");
		 	return "redirect:/gestion_vuelos?modal=true";	
		}*/
				
		
		if (!multiPart.isEmpty()) {
			String ruta = "c:/nmviajes/img-vuelos/";
			String nombreImagen = Utileria.guardarArchivo(multiPart, ruta);
			if (nombreImagen != null) { // La imagen si se subio
				vu.setImagen(nombreImagen);
			}
		}
		
		servicio.guardarVuelo(vu);	
		flash.addFlashAttribute("msg", "Vuelo registrado correctamente !!");
		return "redirect:/gestion_vuelos";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/vuelos/editar/{id}")
	public String mostrarFormularioModificarVuelo(@PathVariable("id") Long id, Model modelo) {
		Vuelo vuelo = servicio.buscarUsuarioPorId(id);// encontramos y obtenemos
		modelo.addAttribute("vueloEdit", vuelo);
		return "gestion_vuelos_editar";
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/vueloRegistroEditado")
	public String registrarVueloEditado(@ModelAttribute("vueloEdit") Vuelo vu, RedirectAttributes flash, Model modelo, 
			 							@RequestParam("archivoImagen") MultipartFile multiPart) {
		
		LocalDate fecha_partida = vu.getFechaPartida();
		LocalDate fecha_partida2 = vu.getFechaPartida2();
		LocalDate fecha_regreso = vu.getFechaRegreso();
		LocalDate fecha_regreso2 = vu.getFechaRegreso2();
		
		LocalTime hora_partida = vu.getHoraPartida();
		LocalTime hora_partida2 = vu.getHoraPartida2();
		LocalTime hora_regreso= vu.getHoraRegreso();
		LocalTime hora_regreso2 = vu.getHoraRegreso2();
		
		if (fecha_partida.isAfter(fecha_partida2)) {
			System.out.println("La fecha de partida debe ser anterior a la fecha de llegada del vuelo de ida.");
			//flash.addFlashAttribute("msgg","La fecha de partida debe ser anterior a la fecha de llegada del vuelo de ida.");
			modelo.addAttribute("msgg","La fecha de partida debe ser anterior a la fecha de llegada del vuelo de ida.");
			return "gestion_vuelos_editar";
		 	//return "redirect:/gestion_vuelos?modal=true";
		}

		if (fecha_regreso.isAfter(fecha_regreso2)) {
			System.out.println("La fecha de partida debe ser anterior a la fecha de llegada del vuelo de regreso.");
			modelo.addAttribute("msgg","La fecha de partida debe ser anterior a la fecha de llegada del vuelo de regreso. ");
			return "gestion_vuelos_editar";
		}

		if (fecha_partida2.isAfter(fecha_regreso)) {
			System.out.println("La fecha de llegada del vuelo de ida debe ser anterior a la fecha de partida del vuelo de regreso.");
			modelo.addAttribute("msgg","La fecha de llegada del vuelo de ida debe ser anterior a la fecha de partida del vuelo de regreso.");
			return "gestion_vuelos_editar";
		}

		if (fecha_regreso.isAfter(fecha_regreso2)) {
			System.out.println("La fecha de llegada del vuelo de regreso debe ser anterior a la fecha de llegada del vuelo de regreso.");
			modelo.addAttribute("msgg","La fecha de llegada del vuelo de regreso debe ser anterior a la fecha de llegada del vuelo de regreso.");
			return "gestion_vuelos_editar";
		}
		
		if (fecha_partida.equals(fecha_partida2) && hora_partida.isAfter(hora_partida2)) {
            System.out.println("La hora de partida debe ser anterior a la hora de llegada del vuelo de ida.");
            modelo.addAttribute("msgg","La hora de partida debe ser anterior a la hora de llegada del vuelo de ida.");
            return "gestion_vuelos_editar";
        }
		
		if (fecha_regreso.equals(fecha_regreso2) && hora_regreso.isAfter(hora_regreso2)) {
            System.out.println("La hora de partida debe ser anterior a la hora de llegada del vuelo de regreso.");
            modelo.addAttribute("msgg","La hora de partida debe ser anterior a la hora de llegada del vuelo de regreso.");
            return "gestion_vuelos_editar";
        }
		
		
		if (!multiPart.isEmpty()) {
			String ruta = "c:/nmviajes/img-vuelos/";
			String nombreImagen = Utileria.guardarArchivo(multiPart, ruta);
			if (nombreImagen != null) { // La imagen si se subio
				vu.setImagen(nombreImagen);
			}
		}
		
		System.err.println(vu.getFechaPartida());
		servicio.guardarEditado_2(vu);
		flash.addFlashAttribute("msg", "Vuelo editado correctamente !!");
		return "redirect:/gestion_vuelos";
	}

	/*@Secured("ROLE_ADMIN")
	@PostMapping("/vueloRegistroEditado")
	public String registrarVueloEditado(@ModelAttribute("vuelo") VueloDTO registroDTO) {
		servicio.guardarEditado(registroDTO);
		return "gestion_admin";
	}*/

	@Secured("ROLE_ADMIN")
	@GetMapping("/gestion_vuelos_editar")
	public String registrarVueloEditadar() {

		return "gestion_vuelos_editar";
	}
	
	//Excel
	@GetMapping("/export/allVuelos")
	public ResponseEntity<InputStreamResource> descargarVuelos() throws Exception{
		ByteArrayInputStream stream = servicio.exportAllVuelos();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=vuelos.xls");
		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
	}
	
	
	/**********************************  FIN GESTION VUELOS  **************************************************/

	// carrtio de compras
	@GetMapping("/ejemplo_paquete")
	public String mostrarEjemplo(Model model) {
		List<Vuelo> p = servicio.listarVuelo();

		model.addAttribute("vuelo", p);
		return "ejemplo_paquete";
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping("verVuelo/{id}")
	public String verVuelo(@PathVariable Long id, Model model) {
		log.info("Id vuelo enviado como parámetro {}", id);

		Vuelo v = new Vuelo();
		v = servicio.buscarUsuarioPorId(id);
		model.addAttribute("vuelo", v);

		return "ejemmplo_paqueteCantidad";
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@PostMapping("/cart")
	public String verCart(@RequestParam Long id, @RequestParam Integer cantidad, Model model) {
		
		System.err.println("DESTINO FINAL:" + getDestinoFinal());

		DetallePagoDTO detallePagoDTO = new DetallePagoDTO();
		Vuelo vuelo = new Vuelo();
		log.info("Producto añadido: {}", servicio.buscarUsuarioPorId(id));
		log.info("Cantidad: {}", cantidad);
		double sumaTotal = 0;

		sumaTotal = servicio.buscarUsuarioPorId(id).getPrecio() * cantidad;
		total = sumaTotal;

		detallePagoDTO.setCantidad(cantidad);// pasajeros vuelo
		detallePagoDTO.setPrecio(servicio.buscarUsuarioPorId(id).getPrecio());// precio vuelo
		detallePagoDTO.setNombre(
				servicio.buscarUsuarioPorId(id).getOrigen() + " " + servicio.buscarUsuarioPorId(id).getDestino());// origen
																													// +
																													// destino
		detallePagoDTO.setTotal(sumaTotal);
		// detallePagoDTOImple.setDetallePago(detallePagoDTO);
		detalles.add(detallePagoDTO);
		model.addAttribute("detalle", detallePagoDTO);
		return "ejemplo_paquete_orden";
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping("/order")
	public String order(Model model, Authentication authentication) {

		Usuario usuario = uServicio.buscaUsurio(authentication.getName());
		System.out.println("----- Nombre del usuario en /order: " + usuario.getNombre());

		double capturar = 0;
		String nombre = " ";
		DetallePagoDTO e = new DetallePagoDTO();
		
		if(getDetalles().isEmpty()) {
			model.addAttribute("mensaje", "Orden vacía");
		}else {
			for (DetallePagoDTO item : getDetalles()) {
				capturar += item.getTotal();
				e.setNombre(item.getNombre() + " "); // e se le agrega el nombre de lo que escogio
			}

			e.setTotal(capturar);// e solo tendria el nombre y el total
			
			

			System.out.println("----------------- foreach detalles ----------");
			for (DetallePagoDTO dpago : getDetalles()) {
				System.out.println(dpago);
				String[] partes = dpago.getNombre().split(" ");
				String ciudadDestino = partes[1];
				System.err.println(ciudadDestino);
				destinoFinal = ciudadDestino;
			}
			
			
			
			//detallePagoDTOImple.saveOrden(e, usuario);
			//System.out.println("-------------" + e.getTotal());
			
		}
		
		model.addAttribute("usuario", usuario);
		model.addAttribute("detalles", detalles);
		model.addAttribute("orden", e);

		return "resumenOrden";
	}

	/*@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping("pagarPaypal/{total}")
	public String pagarPaypal(@PathVariable Double total, Model model) {
		System.out.println("Boton Generar a paypal");
		System.out.println("TOTAL: " + total);
		return "zpago";
	}*/
	
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@PostMapping("pagarPaypal")
	public String pagarPaypal(@RequestParam("total") Double total, Model model) {
			System.out.println("Boton Generar a paypal");
			System.out.println("TOTAL: " + total);
			model.addAttribute("total", total);
			return "zpago";
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping("guardarOV/{total}")
	public String guardarOrden(@PathVariable Double total, Model model) {
		System.out.println("Boton Generar");
		System.out.println("TOTAL: " + total);

		Pago pagoObject = new Pago();

		// Usuario u = uServicio.getUsuario();
		// System.out.println(u);
		// uServicio.setUsuario(u);

		model.addAttribute("total", total);
		model.addAttribute("pagoObject", pagoObject);

		// total=0;
		// detalles.clear();
		// return "redirect:/";
		return "pago";
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping("/cleanCart")
	public String limpiarCarrito(RedirectAttributes attributes, Authentication authentication) {
		System.out.println("Entre a /cleanCart");
		
		Usuario usuario = uServicio.buscaUsurio(authentication.getName());
		
		double capturar_total = 0;
		DetallePagoDTO detalle_pago_dto = new DetallePagoDTO();
		
		for (DetallePagoDTO dpdto : getDetalles()) {
			capturar_total += dpdto.getTotal();
			detalle_pago_dto.setNombre(dpdto.getNombre() + " "); // detalle_pago_dto se le agrega el nombre de lo que escogio
		}
		detalle_pago_dto.setTotal(capturar_total);// detalle_pago_dto solo tendria el nombre y el total
		
		if(detalle_pago_dto.getTotal() != 0.0) {
			System.err.println("grabando orden en /cleanncart con un total de: " + detalle_pago_dto.getTotal());
			detallePagoDTOImple.saveOrden(detalle_pago_dto, usuario);	
		}
		
		System.out.println("Limpiando orden");
		total = 0;
		detalles.clear();
		destinoFinal = "";

		attributes.addFlashAttribute("msg", "Su Pago se realizo Exitosamente, se envio los detalles a su correo");
		return "redirect:/";
	}
	
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping("/cleanCart2")
	public String limpiarCarrito2(RedirectAttributes attributes) {
		System.out.println("Entre a /cleanCart2");

		total = 0;
		detalles.clear();
		destinoFinal = "";

		attributes.addFlashAttribute("mensaje", "items eliminados");
		return "redirect:/order";
	}

	/*
	 * @Autowired private PagoServicioImpl servicePago;
	 */

	/* YA NO LO USO */
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@PostMapping("/registrarPago")
	public String registrarPago(@RequestParam("total") double total, Pago pago, RedirectAttributes attributes) {

//		pago.setPrecioFinal(total);
//	    pago.setFechaPago(LocalDate.now());

		// System.out.println(pago);
		// servicePago.save(pago);

		attributes.addFlashAttribute("msg", "Pago Exitoso !!!");

		total = 0;
		detalles.clear();

		return "redirect:/";
	}

	@Secured({ "ROLE_ADMIN" })
	@GetMapping("/registro_ventas")
	public String paginaGestionVentas(Model model) {
		List<Orden> o = detallePagoDTOImple.listAll();
		// List<Orden> o = detallePagoDTOImple.listarCinco();

		model.addAttribute("user", o);
		return "/registro_ventas";
	}

	@GetMapping("/reporte")
	public String paginaGestion() {
		return "redirect:/registro_ventas";
	}

	// caritto de compras de hospedajes

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping("/nuevo_paquete_hospedaje/{id}")
	public String mostrarEjemploHospedjae(@PathVariable Long id, Model model) {
		log.info("Id vuelo enviado como parámetro {}", id);

		Hospedaje v = new Hospedaje();
		v = hservicio.buscarUsuarioPorId(id);
		model.addAttribute("hospedaje", v);
		return "nuevo_paquete_hospedaje";
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@PostMapping("/cartHospedaje")
	public String verCartHospedaje(@RequestParam Long id, @RequestParam Integer cantidad, Model model) {

		DetallePagoDTO detallePagoDTO = new DetallePagoDTO();
		//Hospedaje vuelo = new Hospedaje();
		log.info("Producto añadido: {}", hservicio.buscarUsuarioPorId(id));
		log.info("Cantidad: {}", cantidad);
		double sumaTotal = 0;

		sumaTotal = hservicio.buscarUsuarioPorId(id).getPrecio() * cantidad;

		detallePagoDTO.setCantidad(cantidad);
		detallePagoDTO.setPrecio(hservicio.buscarUsuarioPorId(id).getPrecio());
		detallePagoDTO.setNombre(hservicio.buscarUsuarioPorId(id).getNombre());
		detallePagoDTO.setPersonas(hservicio.buscarUsuarioPorId(id).getPersonas());
		detallePagoDTO.setTotal(sumaTotal);
		// detallePagoDTOImple.setDetallePago(detallePagoDTO);

		detalles.add(detallePagoDTO);
		model.addAttribute("detalle", detallePagoDTO);

		return "paquete_horden";
	}

	

	/////////////////////////////////////// GESTION PAQUETES TURISTICOS
	/////////////////////////////////////// ///////////////////////////////////////////////////////////////////////////////////////////////
	

	/* CARRITO PAQUETE */
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@PostMapping("/cartPaquete")
	public String verCartPaquete(@RequestParam Long id, Model model) {
		System.out.println("(cart) ID paquete: " + id);

		DetallePagoDTO detallePagoDTO = new DetallePagoDTO();
		PaqueteTuristico paquete = servicePaquete.buscarPaqueteTuristicoPorId(id);

		double sumaTotal = 0;
		sumaTotal = paquete.getPrecio() * 1;

		detallePagoDTO.setCantidad(1);
		detallePagoDTO.setPrecio(paquete.getPrecio());
		detallePagoDTO.setNombre("Paquete Turistico " + paquete.getId());
		detallePagoDTO.setTotal(sumaTotal);

		detalles.add(detallePagoDTO);

		model.addAttribute("detalle", detallePagoDTO);

		return "redirect:/order";
	}

	/********************************************************************************************/

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@PostMapping("/success_11")
	public String obtenerDetallesTransaccion_1(@RequestBody Map<String, Object> transactionData, Model model,
			Authentication authentication) {

		String orderId = (String) transactionData.get("orderId");
		String fechaPago = (String) transactionData.get("paymentDate");
		String fechaPago2 = new Date().toString();
		String payerName = (String) transactionData.get("payerName");
		String payerEmail = (String) transactionData.get("payerEmail");
		String purchaseAmount = (String) transactionData.get("purchaseAmount");
		String currencyCode = (String) transactionData.get("currencyCode");

		System.out.println("--------------Mostrando Datos del Pago Paypal ------------");
		System.out.println("ID de la orden: " + orderId);
		System.out.println("Fecha del pago: " + fechaPago);
		System.out.println("Fecha del pago2: " + fechaPago2);
		System.out.println("Nombre del pagador: " + payerName);
		System.out.println("Email del pagador: " + payerEmail);
		System.out.println("Monto de compra: " + purchaseAmount + " " + currencyCode);

		System.out.println("--------------Mostrando Datos de list detalles------------");

		String nombreServicio;
		String cantidad;
		String precio;
		String total;

		String nombreUsuario;
		String apellidos;
		String email;

		for (DetallePagoDTO dpago : detalles) {

			nombreServicio = dpago.getNombre();
			cantidad = String.valueOf(dpago.getCantidad());
			precio = String.valueOf(dpago.getPrecio());
			total = String.valueOf(dpago.getPrecio() * dpago.getCantidad());

			System.out.println("Nombre: " + nombreServicio);
			System.out.println("Cantidad: " + cantidad);
			System.out.println("Precio: " + precio);
			System.out.println("Total: " + total);
			System.out.println("----------");
		}

		System.out.println("----------------- Mostrando Datos del usuario -------------");
		Usuario usuario = uServicio.buscaUsurio(authentication.getName());

		nombreUsuario = usuario.getNombre();
		apellidos = usuario.getApellido();
		email = usuario.getEmail();

		System.out.println(nombreUsuario);
		System.out.println(apellidos);
		System.out.println(email);

		System.out.println("----------------------------------------------------------");

		Pago pago = new Pago();
		pago.setOrderId(orderId);
		pago.setPayerName(payerName);
		pago.setPayerEmail(payerEmail);
		pago.setPurchaseAmount(purchaseAmount);
		pago.setCurrencyCode(currencyCode);


		servicePago.save(pago);
		servicePago.guardarPagoEnMemoria(pago);// guardar pago en memoria
		
		generarPdf(nombreUsuario, apellidos, email, pago.getOrderId(), fechaPago2, pago.getPurchaseAmount());
		//enviarCorreo(nombreUsuario, apellidos, email, pago.getOrderId(), fechaPago2, pago.getPurchaseAmount());
		
		//jasperReportService.generarYenviarReporte(obtenerPdf(), dataSource, "comprobante-pago.pdf");
		enviarCorreoPdf(email, jasperReportService.generarYenviarReporte(obtenerPdf(), dataSource, "comprobante-pago.pdf"));
		System.out.println(dataSource);

		return "redirect:/cleanCart";
	}

	private void generarPdf(String nombreUsuario, String apellidos, String email, String orderId, String fechaPago2,
			String purchaseAmount) {
		String nombreServicio = "";
		BigDecimal igv = new BigDecimal("0.16");
		BigDecimal ventaBruto = new BigDecimal(purchaseAmount);
		BigDecimal ventaTotal = ventaBruto.add(ventaBruto.multiply(igv)).setScale(3);

		Map<String, Object> params = new HashMap<>(); // Cambiamos el tipo de mapa a <String, String>
		
		params.put("nombre", nombreUsuario);
		params.put("apellido", apellidos);
		params.put("correo", email);
		params.put("orden_compra", String.valueOf(orderId)); // Convertimos orderId a String
		params.put("fecha_pago", fechaPago2);
		
		List<Map<String, Object>> listaParametros = new ArrayList<>();
		 
		for (DetallePagoDTO dpago : detalles) {
			 Map<String, Object> parametros = new HashMap<>();
		    nombreServicio = dpago.getNombre();
		    BigDecimal cantidad = new BigDecimal(dpago.getCantidad());
		    BigDecimal precio = new BigDecimal(dpago.getPrecio());
		    BigDecimal total = precio.multiply(cantidad);

		    parametros.put("descripcion_producto", nombreServicio);
		    parametros.put("unidad", cantidad.toString()); // Convertimos cantidad a String
		    parametros.put("precio_unitario", precio.toString()); // Convertimos precio a String
		    parametros.put("total_producto", total.toString()); // Convertimos total a String
		    listaParametros.add(parametros);
		    
	}
		
		dataSource = new JRBeanCollectionDataSource(listaParametros);

		params.put("sub_total", purchaseAmount.toString()); // Convertimos purchaseAmount a String
		params.put("igv", igv.multiply(new BigDecimal(100)).setScale(2) + "%");
		params.put("total", ventaTotal.toString()); // Convertimos ventaTotal a String

		

		generarPdf(params);
	}

	private void generarPdf(Map<String, Object> params) {
		this.parametros = params;
	}

	private Map<String, Object> obtenerPdf() {
		return this.parametros;
	}

	

	@GetMapping("/generar-informe")
	public void generarYDescargarInforme(HttpServletResponse response) {
		jasperReportService.generarYDescargarInforme(response, obtenerPdf(),dataSource, "comprobante-pago.pdf");
	}
	
	
	/*ENVIAR CORREO*/
	public void enviarCorreo(String nombreUsuario, String apellidos, String email, String orderId, String fechaPago2, String purchaseAmount) {
		
		List<String> detallesComoCadenas = new ArrayList<>();
		
		for (DetallePagoDTO atributo : detalles) {
		    String atributoComoCadena = atributo.toString(); 
		    detallesComoCadenas.add(atributoComoCadena);
		}
		
		SimpleMailMessage message = new SimpleMailMessage();
		System.out.println("Enviando correo a: " + email);
		message.setTo(email);
		message.setSubject("Pago Realizado - NMVIAJES");
		message.setText("Se realizo el pago correctamente\n");
		message.setText("Detalles:");
		
		StringBuilder textoCorreo = new StringBuilder();
        textoCorreo.append("Se realizó el pago correctamente.\n");
        textoCorreo.append("Detalles:\n");
        textoCorreo.append("Monto total: " + purchaseAmount + "\n");
        
        for (String elemento : detallesComoCadenas) {
            textoCorreo.append("- ").append(elemento).append("\n");
        }
        
        message.setText(textoCorreo.toString());
		
		try {
			javaMailSender.send(message);
			System.out.println("Correo enviado exitosamente.");
		} catch (Exception e) {
			System.out.println("Error al enviar el correo.");
		}	
	}
	
	public void enviarCorreoPdf(String destinatario, byte[] pdfReport) {
		MimeMessage message = javaMailSender.createMimeMessage();
		 try {
		        MimeMessageHelper helper = new MimeMessageHelper(message, true);
		        helper.setTo(destinatario);
		        helper.setSubject("NMVIAJES - Informe de Pago");
		        helper.setText("Adjunto encontrarás el informe de tu pago.");

		        // Adjunta el informe como archivo PDF
		        helper.addAttachment("InformePago.pdf", new ByteArrayResource(pdfReport));

		        javaMailSender.send(message);
		    } catch (MessagingException e) {
		        e.printStackTrace();
		    }
		
	}
	
	
	/*****************************************************************************/
	
	/*****************************************************************************/


}
