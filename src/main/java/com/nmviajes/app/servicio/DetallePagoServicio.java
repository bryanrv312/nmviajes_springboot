package com.nmviajes.app.servicio;

import java.io.ByteArrayInputStream;
import java.util.List;


import com.nmviajes.app.entidad.Orden;
import com.nmviajes.app.entidad.Usuario;
import com.nmviajes.app.modelo.DetallePagoDTO;

public interface DetallePagoServicio {
    public void setDetallePago(DetallePagoDTO registroDTO);
 
    public Orden saveOrden(DetallePagoDTO d, Usuario u);

    /*
     * @Override
     * public Usuario save(UsuarioRegistroDTO registroDTO) {
     * Usuario usuario = new Usuario(registroDTO.getNombre(),
     * registroDTO.getApellido(),
     * registroDTO.getEmail(),
     * registroDTO.getPassword());
     * return repo.save(usuario);
     * 
     * }
     */
    public void listar(int c);
    public List<Orden> listAll();
    public DetallePagoDTO getUsuario();
    
    public List<Orden>listarCinco();
    
    public ByteArrayInputStream exportMejores() throws Exception;
    
    public ByteArrayInputStream exportAllVentas() throws Exception;
}
