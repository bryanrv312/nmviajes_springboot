package com.nmviajes.app.controlador;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Example;
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
import org.springframework.web.bind.annotation.SessionAttributes;
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
import com.nmviajes.app.servicio.DetallePagoDTOImple;
import com.nmviajes.app.servicio.HospedajeServicioImpl;
import com.nmviajes.app.servicio.PagoServicioImpl;
import com.nmviajes.app.servicio.PaqueteTuristicoServicioImpl;
import com.nmviajes.app.servicio.UsuarioServicio;
import com.nmviajes.app.servicio.VueloServicioImpl;
import com.nmviajes.app.servicio.utils.GenerarReportes;

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
	private PaypalService servicePaypal;// AGREGE

	@Autowired
	private PaqueteTuristicoServicioImpl servicePaquete;// AGREGE

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
	private GenerarReportes jasperReportService;

	JRBeanCollectionDataSource dataSource ;

	Map<String, Object> parametros;

	List<DetallePagoDTO> detalles = new ArrayList<DetallePagoDTO>();

	private double total = 0;

	private double totalorder = 0;

	@Secured("ROLE_ADMIN")
	@GetMapping("/gestion_vuelos")
	public String listarVuelos(Model model) throws ParseException {
		List<Vuelo> p = servicio.listarVuelo();
		Vuelo vuelo = new Vuelo();

		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");

		for (Vuelo v : p) {
			Date fechaOriginal = v.getFecha();
			String fechaFormateada = formatoFecha.format(fechaOriginal);
			v.setFecha(formatoFecha.parse(fechaFormateada));
		}

		model.addAttribute("user", p);
		model.addAttribute("vuelo", vuelo);
		return "gestion_vuelos";
	}

	@GetMapping("/armar_paquete")
	public String paginaArmarPaquete(Model model) {
		List<Vuelo> p = servicio.listarVuelo();
		List<Hospedaje> a = hospedajeServicioImpl.listarHospedaje();

		// System.out.println("-----------"+ uServicio.getUsuario().getNombre());
		model.addAttribute("vuelo", p);
		model.addAttribute("hospedaje", a);

		return "armar_paquete";
	}

	@ModelAttribute
	public void setGenericos(Model model) {
		Vuelo vueloSearch = new Vuelo();
		model.addAttribute("search", vueloSearch);
	}

	@InitBinder // cambia de vacio a null
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@GetMapping("/search")
	public String buscarVuelo(@ModelAttribute("search") Vuelo vuelo, Model model) {
		System.out.println("Vuelo: " + vuelo);
		Example<Vuelo> example = Example.of(vuelo);
		List<Vuelo> listaVuelos = servicio.buscarByExample(example);
		model.addAttribute("vuelo", listaVuelos);
		return "armar_paquete";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/vuelos/eliminar/{id}")
	public String eliminarUnVuelo(@PathVariable("id") Long id, Model modelo) {
		servicio.eliminarVuelo(id);
		return "redirect:/gestion_vuelos";
	}

	@ModelAttribute("vuelo")
	public VueloDTO retornarNuevoUsuarioRegistroDTO() {
		return new VueloDTO();
	}

	@Secured("ROLE_ADMIN")
	@PostMapping("/vueloRegistro")
	public String registrarVuelo(@ModelAttribute("vuelo") VueloDTO registroDTO) {
		servicio.guardar(registroDTO);
		return "gestion_admin";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/vuelos/editar/{id}")
	public String mostrarFormularioModificarVuelo(@PathVariable("id") Long id, Model modelo) {
		Vuelo p = servicio.buscarUsuarioPorId(id);// encontramos y obtenemos

		System.out.println(p.getFecha() + " --> " + p.getFecha().getClass().getName());

		SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
		String fechaFormateada = dateformat.format(p.getFecha());

		modelo.addAttribute("fechaFormateada", fechaFormateada);

		// p.setFecha(fechaFormateada);

		System.out.println(fechaFormateada.getClass().getName());
		System.out.println("fechaFormateada : " + fechaFormateada);

		/*
		 * try { parseDate = dateformat.parse(p.getFecha().toString());
		 * System.out.println("fecha parseada: " + parseDate);
		 * System.out.println(parseDate.getClass().getName()); p.setFecha(parseDate);
		 * System.out.println("fecha setteada: " + p.getFecha()); } catch (Exception e)
		 * { e.printStackTrace(); }
		 */

		modelo.addAttribute("vuelo", p);

		return "gestion_vuelos_editar";
	}

	@Secured("ROLE_ADMIN")
	@PostMapping("/vueloRegistroEditado")
	public String registrarVueloEditado(@ModelAttribute("vuelo") VueloDTO registroDTO) {
		servicio.guardarEditado(registroDTO);
		return "gestion_admin";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/gestion_vuelos_editar")
	public String registrarVueloEditadar() {

		return "gestion_vuelos_editar";
	}

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

		for (DetallePagoDTO i : detalles) {
			capturar += i.getTotal();
			e.setNombre(i.getNombre() + " "); // e se le agrega el nombre de lo que escogio
		}

		// public Orden(String usuario, String apellido, String correo, String lugar,
		// double montoTotal)
		// Orden or = new Orden(detalles);

		e.setTotal(capturar);// e solo tendria el nombre y el total

		System.out.println("----------------- foreach detalles ----------");
		for (DetallePagoDTO dpago : detalles) {
			System.out.println(dpago);
		}

		detallePagoDTOImple.saveOrden(e, usuario);
		System.out.println("-------------" + e.getTotal());
		model.addAttribute("usuario", usuario);
		model.addAttribute("detalles", detalles);
		model.addAttribute("orden", e);

		return "resumenOrden";
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping("pagarPaypal/{total}")
	public String pagarPaypal(@PathVariable Double total, Model model) {
		System.out.println("Boton Generar a paypal");
		System.out.println("TOTAL: " + total);
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
	public String limpiarCarrito(RedirectAttributes attributes) {
		System.out.println("Entre a /cleanCart");

		total = 0;
		detalles.clear();

		attributes.addFlashAttribute("msg", "Pago Exitoso !!!");
		return "redirect:/";
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
		Hospedaje vuelo = new Hospedaje();
		log.info("Producto añadido: {}", hservicio.buscarUsuarioPorId(id));
		log.info("Cantidad: {}", cantidad);
		double sumaTotal = 0;

		sumaTotal = hservicio.buscarUsuarioPorId(id).getPrecio() * cantidad;

		detallePagoDTO.setCantidad(cantidad);
		detallePagoDTO.setPrecio(hservicio.buscarUsuarioPorId(id).getPrecio());
		detallePagoDTO.setNombre(hservicio.buscarUsuarioPorId(id).getNombre());
		detallePagoDTO.setTotal(sumaTotal);
		// detallePagoDTOImple.setDetallePago(detallePagoDTO);

		detalles.add(detallePagoDTO);
		model.addAttribute("detalle", detallePagoDTO);

		return "paquete_horden";
	}

	

	/////////////////////////////////////// GESTION PAQUETES TURISTICOS
	/////////////////////////////////////// ///////////////////////////////////////////////////////////////////////////////////////////////
	@Secured({ "ROLE_ADMIN" })
	@GetMapping("/gestion_paqueteTuristico")
	public String listarPaquetes(Model model) {

		List<PaqueteTuristico> listaPaquete = servicePaquete.listarPaqueteTuristicos();
		PaqueteTuristico paquete = new PaqueteTuristico();

		model.addAttribute("listaPaquete", listaPaquete);
		model.addAttribute("paquete", paquete);

		return "gestionPaqueteTuristico";
	}

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

	/*******************/

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
		enviarCorreo(nombreUsuario, apellidos, email, pago.getOrderId(), fechaPago2, pago.getPurchaseAmount());
		
		jasperReportService.generarYenviarReporte(obtenerPdf(), "comprobante-pago.pdf");
		
		enviarCorreoPdf(email, jasperReportService.generarYenviarReporte(obtenerPdf(), "comprobante-pago.pdf"));

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

	@InitBinder
	public void initBinder1(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
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

}
