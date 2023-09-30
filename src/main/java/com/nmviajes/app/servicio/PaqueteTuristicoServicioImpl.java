package com.nmviajes.app.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nmviajes.app.entidad.PaqueteTuristico;
import com.nmviajes.app.entidad.Vuelo;
import com.nmviajes.app.modelo.PaqueteTuristicoDTO;
import com.nmviajes.app.repositorio.PaqueteTuristicoRepo;

@Service
public class PaqueteTuristicoServicioImpl {
	
    @Autowired
    private PaqueteTuristicoRepo paqueteTuristicoRepo;
    

    public PaqueteTuristico save(PaqueteTuristicoDTO registroDTO) {
		//Long id, String lugarTuristico, Integer estado, Integer detalles, Usuario usuario
		PaqueteTuristico usuario = new PaqueteTuristico(registroDTO.getLugarTuristico(), registroDTO.getEstado(),
				registroDTO.getDetalles());
		return paqueteTuristicoRepo.save(usuario);
	}
    

	public List<PaqueteTuristico> listarPaqueteTuristicos() {
		return paqueteTuristicoRepo.findAll();
	}
	
	
	public PaqueteTuristico buscarPaqueteTuristicoPorId(Long id) {
		PaqueteTuristico pt = paqueteTuristicoRepo.findById(id).get();
		return pt;
	}

}
