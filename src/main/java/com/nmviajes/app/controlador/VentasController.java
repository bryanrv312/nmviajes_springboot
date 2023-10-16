package com.nmviajes.app.controlador;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nmviajes.app.entidad.Orden;
import com.nmviajes.app.entidad.Usuario;
import com.nmviajes.app.servicio.DetallePagoServicio;
import com.nmviajes.app.servicio.UsuarioServicio;

@Secured("ROLE_ADMIN")
@Controller
public class VentasController {
	
	@Autowired
	private DetallePagoServicio detallePagoDTOImple;
	
    @GetMapping("/mejores-ventas")
    public String mostrarMejoresVentas(Model model) {
    	List<Orden> o = detallePagoDTOImple.listarCinco();
    	
        List<String> nombresProductos = new ArrayList<>();
        List<Double> cantidadesVentas = new ArrayList<>();

        
        for (Orden venta : o) {
            nombresProductos.add(venta.getUsuario());
            cantidadesVentas.add(venta.getMontoTotal());
        }

        // Agrega los datos al modelo para que Thymeleaf pueda acceder a ellos
        model.addAttribute("nombresProductos", nombresProductos);
        model.addAttribute("cantidadesVentas", cantidadesVentas);

        return "barra_registro_ventas";
    }

}
