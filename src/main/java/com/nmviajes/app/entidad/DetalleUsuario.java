package com.nmviajes.app.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "detalleusuario") 
public class DetalleUsuario {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(name = "correo")
    private String correo;
    
    
    @Column(name = "dni")
    private String dni;

    @Column(name = "numerocompra")
	private String numeroCompra;

    @Column(name = "asunto")
	private String asunto;

    @Column(name = "tipoatencion")
	private String tipoatencion;

    @Column(name = "estadia")
	private String estadia;
    
    
    @Column(name = "reclamo")
	private String reclamo;


    public DetalleUsuario() {
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getCorreo() {
        return correo;
    }


    public void setCorreo(String correo) {
        this.correo = correo;
    }


    public String getDni() {
        return dni;
    }


    public void setDni(String dni) {
        this.dni = dni;
    }


    public String getNumeroCompra() {
        return numeroCompra;
    }


    public void setNumeroCompra(String numeroCompra) {
        this.numeroCompra = numeroCompra;
    }


    public String getAsunto() {
        return asunto;
    }


    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }


    public String getTipoatencion() {
        return tipoatencion;
    }


    public void setTipoatencion(String tipoatencion) {
        this.tipoatencion = tipoatencion;
    }


    public String getEstadia() {
        return estadia;
    }


    public void setEstadia(String estadia) {
        this.estadia = estadia;
    }


    public String getReclamo() {
        return reclamo;
    }


    public void setReclamo(String reclamo) {
        this.reclamo = reclamo;
    }


    public DetalleUsuario(String correo, String dni, String numeroCompra, String asunto, String tipoatencion,
            String estadia, String reclamo) {
        this.correo = correo;
        this.dni = dni;
        this.numeroCompra = numeroCompra;
        this.asunto = asunto;
        this.tipoatencion = tipoatencion;
        this.estadia = estadia;
        this.reclamo = reclamo;
    }


    public DetalleUsuario(Long id, String correo, String dni, String numeroCompra, String asunto,
            String tipoatencion, String estadia, String reclamo) {
        this.id = id;
        this.correo = correo;
        this.dni = dni;
        this.numeroCompra = numeroCompra;
        this.asunto = asunto;
        this.tipoatencion = tipoatencion;
        this.estadia = estadia;
        this.reclamo = reclamo;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DetalleUsuario other = (DetalleUsuario) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }


    @Override
    public String toString() {
        return "UsuarioProgromociones [id=" + id + ", correo=" + correo + ", dni=" + dni + ", numeroCompra="
                + numeroCompra + ", asunto=" + asunto + ", tipoatencion=" + tipoatencion + ", estadia=" + estadia
                + ", reclamo=" + reclamo + "]";
    }

   
    


}
