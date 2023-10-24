package com.nmviajes.app.controlador;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nmviajes.app.entidad.Orden;
import com.nmviajes.app.entidad.Role;
import com.nmviajes.app.entidad.Usuario;
import com.nmviajes.app.modelo.DetallePagoDTO;
import com.nmviajes.app.modelo.HospedajeDTO;
import com.nmviajes.app.modelo.UsuarioRegistroDTO;
import com.nmviajes.app.servicio.DetallePagoDTOImple;
import com.nmviajes.app.servicio.DetallePagoServicio;
import com.nmviajes.app.servicio.UsuarioServicio;
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

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Secured("ROLE_ADMIN")
@Controller
public class ControladorAdmin {
	
	private Long idEnvioCorreo;
	private String email;
	private Usuario usuarioPublico;
	
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	
	@Autowired
	private UsuarioServicio servicio;

	@Autowired
	private DetallePagoServicio detallePagoDTOImple;
	
	@Autowired
	private DetallePagoDTOImple serviceDetalles;

	
	
	@GetMapping("/listaUsuario")
	public String verLista(Model model) {
		List<Usuario> p = servicio.listAll();
		
		model.addAttribute("user",p);
		return "listaUsuario";
	}

	
	@GetMapping("/sesion_admin")
	public String loginAdmin(Model model) {
		Usuario p = new Usuario();
		
		model.addAttribute("user",p);
		return "sesion_admin";
	}

	@PostMapping("/validarAdmin")
	public String validarAdmin(@ModelAttribute("user") UsuarioRegistroDTO user) {
		
		if(servicio.siUsuario(user)) {
			
			return "gestion_admin";
		}
		return "sesion_admin";
	}


	@GetMapping("/gestion_usuarios")
	public String paginaGestionUsuarios(Model model) {
		List<Usuario> usuario = servicio.listAll();
		/*for (Usuario usuario : p) {
	        System.err.println("Roles del usuario " + usuario.getUsername() + ":");
	        for (Role role : usuario.getRoles()) {
	            System.err.println("Rol: " + role.getAuthority());
	        }
	    }*/
		model.addAttribute("user",usuario);
		return "gestion_usuarios";
	}

	@GetMapping("/usuario/eliminar/{id}")
	public String eliminarFormularioProducto(@PathVariable("id") Long id , Model modelo) {
		servicio.eliminarUsuarioPorId(id);
		return "redirect:/gestion_usuarios";
	}
	
	@GetMapping("/usuario/desbloquear/{id}")
	public String desbloquearUsuario(@PathVariable("id") Long id , RedirectAttributes attributes) {
		System.out.println("Desbloqueando usuario con id: " + id);
		Usuario usuario = servicio.buscarUsuarioPorId(id);
		usuario.setEnabled(true);
		servicio.guardar(usuario);
		attributes.addFlashAttribute("msg", "El Usuario fue Habilitado !!!");
		return "redirect:/gestion_usuarios";
	}
	
	@GetMapping("/usuario/bloquear/{id}")
	public String bloquearUsuario(@PathVariable("id") Long id , RedirectAttributes attributes) {
		System.out.println("BLOQUEANDO usuario con id: " + id);
		Usuario usuario = servicio.buscarUsuarioPorId(id);
		usuario.setEnabled(false);
		servicio.guardar(usuario);
		attributes.addFlashAttribute("msg", "El Usuario fue Bloqueado !!!");
		return "redirect:/gestion_usuarios";
	}
	
	@PostMapping("/usuario/enviar_correo/{id}")
	public String enviarCorreoUsuario(@PathVariable("id") String id , Model modelo, RedirectAttributes flash) {
		System.out.println("id enviar correo : " + id);
		Long id_usuario = Long.parseLong(id);
		Usuario usuario = servicio.buscarUsuarioPorId(id_usuario);
		System.out.println(usuario.getEmail());
		
		this.idEnvioCorreo = id_usuario;
		this.email = usuario.getEmail();
		this.usuarioPublico = usuario; //hace toda la Magia al obtener el ID
		
		return "redirect:/form_correo";
	}
	
	@GetMapping("/form_correo")
	public String formularioCorreo(Model model) {
		//model.addAttribute("msg",idEnvioCorreo);
		model.addAttribute("usuario",usuarioPublico);//contiene el id,email, etc del usuario
		
		//Usuario user = new Usuario();
		//model.addAttribute("user", user);//th:object en el form

		return "z_formEnviarCorreo";
	}
	
	@PostMapping("/enviar_correo_2")
	public String enviarDatosFormulario(
			@RequestParam("destinatario") String destinatario, 
			@RequestParam("asunto") String asunto, 
			@RequestParam("mensaje") String mensaje,
			@RequestParam("archivo") MultipartFile multipart,
			RedirectAttributes flash) {
		
		System.err.println(destinatario);
		System.err.println(asunto);
		System.err.println(mensaje);
		
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	        
	     // Configura el destinatario, asunto y cuerpo del mensaje
	        helper.setTo(destinatario);
	        helper.setSubject(asunto);
	        helper.setText(mensaje, true);  // El segundo parámetro indica que es HTML
	        
	     // Agrega el archivo adjunto si se proporciona
	        if (multipart != null && !multipart.isEmpty()) {
	            helper.addAttachment(multipart.getOriginalFilename(), multipart);
	        }
	        
	        javaMailSender.send(message);// Envía el mensaje
	        flash.addFlashAttribute("msg", "Correo enviado correctamente");
			
		} catch (Exception e) {
			flash.addFlashAttribute("msg","ERROR al enviar correo!!");
		}

		flash.addFlashAttribute("msg","Correo enviado correctamente!!");
		
		return "redirect:/form_correo";
		//return "z_formEnviarCorreo";
	}
	

	//editar usuario
	@GetMapping("/usuario/editar/{id}")
	public String mostrarFormularioModificarUsuario(@PathVariable("id") Long id , Model modelo) {
		System.out.println("Editar id: " + id);
		Usuario usuario = servicio.buscarUsuarioPorId(id);
		modelo.addAttribute("user",usuario);
		return "gestion_usuarios_editar";
	}
	
	@PostMapping("/usuarioRegistroEditado")
	public String registrarUsuarioEditado(@ModelAttribute("user") Usuario usu, Model modelo){
		System.err.println(usu.getNombre() + " " + usu.getId());
		servicio.guardarEditado(usu);
		modelo.addAttribute("msg","Usuario Editado Correctamente !!!");
		return "gestion_usuarios_editar";
	}


	/* 
	@GetMapping("/registro_ventas")
	public String paginaGestionVentas(Model model) {
		List<Orden> o = detallePagoDTOImple.listAll();
		//List<Orden> o = detallePagoDTOImple.listarCinco();
		model.addAttribute("user",o);
		return "/registro_ventas";
	}*/
	@GetMapping("/registro_mejor")
	public String paginaMejoresVentas(Model model) {
		
		/*Probar List detalles*/
		List<DetallePagoDTO> detalles = serviceDetalles.getDetalles();
		System.err.println("detalles obtenido : " + detalles);//test detalle
		
		List<Orden> o = detallePagoDTOImple.listarCinco();
		model.addAttribute("user",o);
		return "/registro_mejor";
	}
	
	

}



