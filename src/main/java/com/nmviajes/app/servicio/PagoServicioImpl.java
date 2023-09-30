package com.nmviajes.app.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nmviajes.app.entidad.Pago;
import com.nmviajes.app.modelo.PagoDTO;
import com.nmviajes.app.repositorio.PagRepo;

@Service
public class PagoServicioImpl {
    @Autowired
    private PagRepo pagRepo;
    
    private Pago pago;

    /*public Pago guardar(PagoDTO registroDTO) {
			Pago usuario = new Pago(registroDTO.getCorreo(), registroDTO.getMetodo(),
				registroDTO.getPrecioFinal());
		return pagRepo.save(usuario);
	}*/
    
    public void guardarPagoEnMemoria(Pago p) {
    	this.pago = p;
    }
    
    public Pago retornarPagoEnMemoria() {
    	
    	return pago;
    }
    
    
    public Pago buscarPorOrderId(String orderId) {
    	return pagRepo.findByOrderId(orderId);
    }
    
    
    public Pago save(Pago pago) {
    	return pagRepo.save(pago);
    }
    
    

	public List<Pago> listarHospedaje() {
		return pagRepo.findAll();
	}
}
