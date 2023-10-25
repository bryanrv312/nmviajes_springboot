package com.nmviajes.app.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nmviajes.app.entidad.Hospedaje;
import com.nmviajes.app.modelo.HospedajeDTO;
import com.nmviajes.app.repositorio.HospedajeRepo;

@Service
public class HospedajeServicioImpl {
    @Autowired
    private HospedajeRepo hospedajeRepo;

    public Hospedaje guardar(HospedajeDTO registroDTO) {
		//public HospedajeDTO(Long id, String nombre, String ubicacion, Double precio) {
			Hospedaje hospedaje = new Hospedaje(registroDTO.getNombre(), registroDTO.getUbicacion(),
				registroDTO.getPrecio(), registroDTO.getHabitaciones(), registroDTO.getPersonas());
		return hospedajeRepo.save(hospedaje);
	}

	public Hospedaje guardarEditado(HospedajeDTO registroDTO) {
		//public HospedajeDTO(Long id, String nombre, String ubicacion, Double precio) {
			Hospedaje usuario = new Hospedaje(registroDTO.getId(),registroDTO.getNombre(), registroDTO.getUbicacion(),
				registroDTO.getPrecio(), registroDTO.getHabitaciones(), registroDTO.getPersonas());
		return hospedajeRepo.save(usuario);
	}
	
	public List<Hospedaje> listarHospedaje() {
		return hospedajeRepo.findAll();
	}

	public void eliminarHospedaje(Long id){
		hospedajeRepo.deleteById(id);
	}

	
	public Hospedaje buscarUsuarioPorId(Long id) {
		Hospedaje u = hospedajeRepo.findById(id).get();
		return u;
	}
	
	public List<Hospedaje> buscarHotelesPorUbicacion(String ciudad) {
	    return hospedajeRepo.findByUbicacionContaining(ciudad);
	}
    
}
