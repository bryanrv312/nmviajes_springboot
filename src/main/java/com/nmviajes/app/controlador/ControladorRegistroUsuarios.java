package com.nmviajes.app.controlador;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
	private UsuarioServicio uServicio;
	
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
        usu.setConfirmado(false); // El estado inicial es "no confirmado"
	
		usu.setEnabled(false);
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

        if (usuario != null && !usuario.isConfirmado()) {
        	usuario.setEnabled(true);
            usuario.setConfirmado(true); // actualizara a 1 la confirmacion en la bd
            usuarioServicio.guardar(usuario);
            System.err.println("/confirmar_email - confirmacion_exitosa");
            
            model.addAttribute("msg", "Correo confirmado, Ingrese sus credenciales");
            return "iniciar_sesion";
        }	
        
        System.err.println("/confirmar_email - confirmacion_errone");
        attributes.addFlashAttribute("msg", "Error en la confirmación de su correo");
        return "redirect:/";
    }
	
	
	

}
