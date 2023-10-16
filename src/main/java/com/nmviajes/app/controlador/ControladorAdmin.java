package com.nmviajes.app.controlador;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nmviajes.app.entidad.Orden;
import com.nmviajes.app.entidad.Role;
import com.nmviajes.app.entidad.Usuario;
import com.nmviajes.app.modelo.DetallePagoDTO;
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

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Secured("ROLE_ADMIN")
@Controller
public class ControladorAdmin {
	
	
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
		List<Usuario> p = servicio.listAll();
		
		/*for (Usuario usuario : p) {
	        System.err.println("Roles del usuario " + usuario.getUsername() + ":");
	        for (Role role : usuario.getRoles()) {
	            System.err.println("Rol: " + role.getAuthority());
	        }
	    }*/
		
		model.addAttribute("user",p);
		return "gestion_usuarios";
	}

	@GetMapping("/usuario/eliminar/{id}")
	public String eliminarFormularioProducto(@PathVariable("id") Long id , Model modelo) {
		servicio.eliminarUsuarioPorId(id);
		return "redirect:/gestion_usuarios";
	}

	//editar usuario
	@GetMapping("/usuarios/editar/{id}")
	public String mostrarFormularioModificarUsuario(@PathVariable("id") Long id , Model modelo) {
		Usuario p = servicio.buscarUsuarioPorId(id);//encontramos y obtenemos
		modelo.addAttribute("user",p);
		List<Usuario> listaUsuario = servicio.listarUsuarios();
		modelo.addAttribute("listaUsuario",listaUsuario);//para pasarle una lista y recorrer todas las lista de la categoria
		
		return "gestion_usuarios";
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
		
		List<DetallePagoDTO> detalles = serviceDetalles.getDetalles();
		
		System.err.println("detalles obtenido : " + detalles);
		
		List<Orden> o = detallePagoDTOImple.listarCinco();
		model.addAttribute("user",o);
		return "/registro_mejor";
	}
	
	

}



