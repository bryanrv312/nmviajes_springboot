package com.nmviajes.app.servicio;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nmviajes.app.entidad.Orden;
import com.nmviajes.app.entidad.Usuario;
import com.nmviajes.app.modelo.DetallePagoDTO;
import com.nmviajes.app.repositorio.OrdenRepo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Date;

@Service
public class DetallePagoDTOImple implements DetallePagoServicio{

	@Autowired
	private OrdenRepo ordenRepo;

	DetallePagoDTO u;
	
	@Override
    public void setDetallePago(DetallePagoDTO registroDTO){	
			this.u=registroDTO;	
	}
	
	@Override
	public Orden saveOrden(DetallePagoDTO d, Usuario u){
		Orden o = new Orden(null, u.getNombre(),u.getApellido(), u.getEmail(),d.getNombre(), new Date(), d.getTotal());
		return ordenRepo.save(o);
	}
	
	

	@Override
	public void listar(int c){
		List<Orden> slist = listAll();
        slist.sort(Comparator.comparing(Orden::getMontoTotal).reversed());
       
	   for (int i = 0; i < 3; i++) {
		slist.set(i, slist.get(i));
		System.out.println(slist.set(i, slist.get(i)));
	  }
	 
	}

	public List<Orden>listarCinco(){
		List<Orden> slist = listAll();
        slist.sort(Comparator.comparing(Orden::getMontoTotal).reversed());
       List<Orden> n = new ArrayList<>();
       
	   for (int i = 0; i < 5; i++) {
		slist.set(i, slist.get(i));
		n.add(slist.set(i, slist.get(i)));
		System.out.println(slist.set(i, slist.get(i)));
	  }
	  
		return n;
	}

	/*@Override
	public Usuario save(UsuarioRegistroDTO registroDTO) {
		Usuario usuario = new Usuario(registroDTO.getNombre(), registroDTO.getApellido(),
				registroDTO.getEmail(),
				registroDTO.getPassword());
		return repo.save(usuario);

	}
	
	
	@Override
	public List<Usuario> listarUsuarios() {
		return repo.findAll();

	}

	*/
	@Override
	public DetallePagoDTO getUsuario(){
		return this.u;
	}

	@Override
	public List<Orden> listAll(){
		return ordenRepo.findAll();
	}
    
}
