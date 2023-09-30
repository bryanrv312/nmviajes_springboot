package com.nmviajes.app.modelo;

import com.nmviajes.app.entidad.Usuario;

public class PaqueteTuristicoDTO {

    private Long id;
    
	private String lugarTuristico;
	
	private Integer estado;
   
	private Integer detalles;

    private Usuario usuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLugarTuristico() {
        return lugarTuristico;
    }

    public void setLugarTuristico(String lugarTuristico) {
        this.lugarTuristico = lugarTuristico;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Integer getDetalles() {
        return detalles;
    }

    public void setDetalles(Integer detalles) {
        this.detalles = detalles;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public PaqueteTuristicoDTO(Long id, String lugarTuristico, Integer estado, Integer detalles, Usuario usuario) {
        this.id = id;
        this.lugarTuristico = lugarTuristico;
        this.estado = estado;
        this.detalles = detalles;
        this.usuario = usuario;
    }

    public PaqueteTuristicoDTO() {
    }

    
}
