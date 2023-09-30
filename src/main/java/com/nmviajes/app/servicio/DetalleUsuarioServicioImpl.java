package com.nmviajes.app.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nmviajes.app.entidad.DetalleUsuario;
import com.nmviajes.app.modelo.DetalleUsuarioDTO;
import com.nmviajes.app.repositorio.DetalleUsuarioRepo;

@Service
public class DetalleUsuarioServicioImpl {
    @Autowired
    private DetalleUsuarioRepo detalleUsuarioRepo;

    public DetalleUsuario guardar(DetalleUsuarioDTO registroDTO) {
			DetalleUsuario usuario = new DetalleUsuario(registroDTO.getCorreo(), registroDTO.getDni(),
				registroDTO.getNumeroCompra(),registroDTO.getAsunto(),registroDTO.getTipoatencion(),registroDTO.getEstadia(),registroDTO.getReclamo());
		return detalleUsuarioRepo.save(usuario);
	}



}
