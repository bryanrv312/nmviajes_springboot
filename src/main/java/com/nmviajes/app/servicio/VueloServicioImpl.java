package com.nmviajes.app.servicio;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nmviajes.app.entidad.Vuelo;
import com.nmviajes.app.modelo.VueloDTO;
import com.nmviajes.app.repositorio.VueloRepo;


@Service
public class VueloServicioImpl {
    @Autowired
    private VueloRepo vueloRepo;

    public Vuelo guardar(VueloDTO registroDTO) {
		// public Vuelo(Long id, String origen, String destino, String aerolinea, Integer pasajeros, Date fecha,
         //   Double precio) {
			Vuelo usuario = new Vuelo(registroDTO.getOrigen(), registroDTO.getDestino(),
			registroDTO.getAerolinea(),registroDTO.getPasajeros(),registroDTO.getFecha(),registroDTO.getPrecio());
		return vueloRepo.save(usuario);
	}

	public Vuelo guardarEditado(VueloDTO registroDTO) {
		// public Vuelo(Long id, String origen, String destino, String aerolinea, Integer pasajeros, Date fecha,
		//Double precio) {
			Vuelo usuario = new Vuelo(registroDTO.getId(),registroDTO.getOrigen(), registroDTO.getDestino(),
			registroDTO.getAerolinea(),registroDTO.getPasajeros(),registroDTO.getFecha(),registroDTO.getPrecio());
		return vueloRepo.save(usuario);
	}

	public List<Vuelo> listarVuelo() {
		return vueloRepo.findAll();
	}

	public Vuelo buscarUsuarioPorId(Long id) {
		Vuelo u = vueloRepo.findById(id).get();
		return u;
	}
	public void eliminarVuelo(Long id){
		vueloRepo.deleteById(id);
	}
	
	public List<Vuelo> buscarByExample(Example<Vuelo> example){
		return vueloRepo.findAll(example);
	}
	
}
