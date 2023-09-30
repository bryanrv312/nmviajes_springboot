package com.nmviajes.app.entidad;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orden") 
public class Orden {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

    @Column(name = "usuario")
    private String usuario;

    private String apellido;

    private String correo;

    private String lugar;

    private Date fecha;

    private double montoTotal;

    public Integer getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getCorreo() {
        return correo;
    }

    public Date getFecha() {
        return fecha;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Orden(String usuario, String apellido, String correo, String lugar, Date fecha, double montoTotal) {
        this.usuario = usuario;
        this.apellido = apellido;
        this.correo = correo;
        this.lugar = lugar;
        this.fecha = fecha;
        this.montoTotal = montoTotal;
    }

    public Orden(Integer id, String usuario, String apellido, String correo, String lugar, Date fecha,
            double montoTotal) {
        this.id = id;
        this.usuario = usuario;
        this.apellido = apellido;
        this.correo = correo;
        this.lugar = lugar;
        this.fecha = fecha;
        this.montoTotal = montoTotal;
    }

    public Orden(String usuario, String apellido, String correo, Date fecha, double montoTotal) {
        this.usuario = usuario;
        this.apellido = apellido;
        this.correo = correo;
        this.fecha = fecha;
        this.montoTotal = montoTotal;
    }

    public Orden(Integer id, String usuario, String apellido, String correo, Date fecha, double montoTotal) {
        this.id = id;
        this.usuario = usuario;
        this.apellido = apellido;
        this.correo = correo;
        this.fecha = fecha;
        this.montoTotal = montoTotal;
    }

    public Orden(Integer id, String usuario, String correo, double montoTotal) {
        this.id = id;
        this.usuario = usuario;
        this.correo = correo;
        this.montoTotal = montoTotal;
    }

    public Orden(String usuario, String apellido, String correo, String lugar, double montoTotal) {
        this.usuario = usuario;
        this.apellido = apellido;
        this.correo = correo;
        this.lugar = lugar;
        this.montoTotal = montoTotal;
    }

    public Orden(Integer id, String usuario, String correo, Date fecha, double montoTotal) {
        this.id = id;
        this.usuario = usuario;
        this.correo = correo;
        this.fecha = fecha;
        this.montoTotal = montoTotal;
    }

    public Orden() {
    }

    public Orden(String usuario) {
        this.usuario = usuario;
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
        Orden other = (Orden) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Orden [id=" + id + ", usuario=" + usuario + ", apellido=" + apellido + ", correo=" + correo + ", fecha="
                + fecha + ", montoTotal=" + montoTotal + "]";
    }

    

}
