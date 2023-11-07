package com.nmviajes.app.controlador;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nmviajes.app.entidad.Role;
import com.nmviajes.app.entidad.Usuario;
import com.nmviajes.app.modelo.UsuarioRegistroDTO;
import com.nmviajes.app.servicio.RolesServicio;
import com.nmviajes.app.servicio.UsuarioServicio;

@Controller
public class ControladorRegistroUsuarios {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private UsuarioServicio usuarioServicio;
	
	@Autowired
	private RolesServicio rolServicio;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("/registro")
	public String register(Model model) {
		Usuario user = new Usuario();
		model.addAttribute("user",user);
		
		return "registro";
	}
	
	
	@PostMapping("/registrarUsuario")
	public String registrarUsuario(@ModelAttribute("user") Usuario usu, RedirectAttributes attributes) {
		
		String token = generateToken();
		
        usu.setToken(token);
        //usu.setConfirmado(false); // El estado inicial es "no confirmado"
	
		usu.setEnabled(false); //utilizamos el campo enable de ss
		//usu.setNombre("patrick");
		usu.setPassword(passwordEncoder.encode(usu.getPassword()));
		
		Role role = new Role();
		role.setAuthority("ROLE_USER");
		role.setUser(usu);
		
		rolServicio.save(role);
		usuarioServicio.guardar(usu);
		
		String subject = "Confirmación de registro";
        String confirmationLink = "http://localhost:9590/confirmar_email?token=" + token;
        String message = "Haz clic en el siguiente enlace para confirmar tu registro: " + confirmationLink;
        
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(usu.getEmail());
        email.setSubject(subject);
        email.setText(message);
        
        javaMailSender.send(email);
        
        System.err.println("/registrarUsuario - USUARIO POR CONFIRMAR - SE ENVIO EMAIL!!!");
        attributes.addFlashAttribute("msg", "Se envio un mensaje de confirmación a su correo");
		
		return "redirect:/registro";
	}
	
	//generate token
	public static String generateToken() {
        return UUID.randomUUID().toString();
    }
	
	
	
	//Confirm email
	@GetMapping("/confirmar_email")
    public String confirmarRegistro(@RequestParam("token") String token, Model model, RedirectAttributes attributes) {
		System.out.println("- token recibido: " + token);
		
        //Verifica si el token es válido
        Usuario usuario = usuarioServicio.buscarPorToken(token);
        System.err.println("-usuario del token: " + usuario.getNombre());

        if (usuario != null && !usuario.getEnabled()) {
        	usuario.setEnabled(true);
            //usuario.setConfirmado(true); // actualizara a 1 la confirmacion en la bd
            usuarioServicio.guardar(usuario);
            System.err.println("/confirmar_email - confirmacion_exitosa");
            
            model.addAttribute("msg", "Correo confirmado, Ingrese sus credenciales");
            return "iniciar_sesion";
        }	
        
        System.err.println("/confirmar_email - confirmacion_errone");
        attributes.addFlashAttribute("msg", "Error en la confirmación de su correo");
        return "redirect:/";
    }
	
	
	
	@GetMapping("/settings")
	public String ver_configuraciones(Authentication auth, Model model) {
		Usuario usuario_1 = usuarioServicio.buscaUsurio(auth.getName());
		Usuario usuario_2 = usuarioServicio.buscaUsurio(auth.getName());
		Usuario usuario_3 = usuarioServicio.buscaUsurio(auth.getName());
		model.addAttribute("user_1", usuario_1);//th:object en el form
		model.addAttribute("user_2", usuario_2);
		model.addAttribute("user_3", usuario_3);
		return "usuario/usuario_settings";
	}
	
	@PostMapping("/editar_usuario_3")
	public String editar_usaurio_3(@ModelAttribute("user_1") Usuario usu, Model model) {
		System.err.println(usu.getId());
		System.err.println(usu.getUsername());
		System.err.println(usu.getEmail());	
		
		if(usu.getEmail() != null) {
			usuarioServicio.guardarEditado(usu);
			SecurityContextHolder.clearContext();
		}
		
		model.addAttribute("msg","Se edito sus credenciales, ingrese nuevamente !!!");
		return "iniciar_sesion";
	}
	
	
	@PostMapping("/editar_usuario_email")
	public String editar_solo_correo(@ModelAttribute("user_2") Usuario usu, Model model, RedirectAttributes flash) {
		System.err.println(usu.getId());
		System.err.println(usu.getUsername());
		System.err.println(usu.getEmail());	
		
		if(usu.getEmail() != null) {
			
			String token = generateToken();
			
	        usu.setToken(token);
	        //usu.setConfirmado(false);
	        usu.setEnabled(false);
	        
	        usuarioServicio.guardarEditado_correo(usu);
			//SecurityContextHolder.clearContext();
	        
	        /*verifiacion correo*/
	        String subject = "Cambio de Correo";
	        String confirmationLink = "http://localhost:9590/confirmar_email?token=" + token;
	        String message = "Haz clic en el siguiente enlace para confirmar su Correo: " + confirmationLink;
	        
	        SimpleMailMessage email = new SimpleMailMessage();
	        email.setTo(usu.getEmail());
	        email.setSubject(subject);
	        email.setText(message);
	        
	        javaMailSender.send(email);  
	        System.err.println("/registrarUsuario - USUARIO POR CONFIRMAR - SE ENVIO EMAIL!!!");
	                
	        SecurityContextHolder.clearContext();//cerrar sesion
		}
		//flash.addFlashAttribute("msg", "Se envio un token de confirmación a su correo");
		model.addAttribute("msg","Se edito sus credenciales, ingrese nuevamente !!!");
		return "iniciar_sesion";
	}
	
	
	@PostMapping("/editar_usuario_password")
	public String editar_password(@ModelAttribute("user_3") Usuario usu, 
								  @RequestParam("actual_pass") String actual,
								  @RequestParam("nuevo_pass") String nuevo,
								  Model model, RedirectAttributes flash) {
		System.err.println("id:" + usu.getId());
		System.err.println("actual:" + actual);
		System.err.println("nueva:" + nuevo);
		
		Usuario usua = usuarioServicio.buscarUsuarioPorId(usu.getId());
		System.err.println("pass:" + usua.getPassword());
		
		if(passwordEncoder.matches(actual, usua.getPassword())) {//en caso de q SI coincidan las pass
			System.err.println("correcto coincide las contraseñas");
			
			if(nuevo != actual) {
				usua.setPassword(passwordEncoder.encode(nuevo));
				usuarioServicio.guardar(usua);
				model.addAttribute("msg","Se edito sus credenciales, ingrese nuevamente !!!");
				SecurityContextHolder.clearContext();
				return "iniciar_sesion";
			}else {
				System.err.println("Actual == Nuevo");
				flash.addFlashAttribute("msg_pass","Debe ingresar una contraseña diferente a la Actual !!!");
				return "redirect:/settings";
			}
			
		}else {
			System.err.println("No coincide las contraseñas");
			flash.addFlashAttribute("msg_pass","Contraseña actual no coincide !!!");
			return "redirect:/settings";
		}
		
	}
	
	

}
