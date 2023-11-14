
package com.nmviajes.app.modelo;

import com.nmviajes.app.entidad.Hospedaje;
import com.nmviajes.app.entidad.Vuelo;

public class DetallePagoDTO{
    private Integer id;
    private String nombre;
    private double cantidad;
    private double precio ;
    private double total; 

    private Vuelo vuelo;
 
    private Hospedaje hospedaje;
    
    private Integer personas;
    
    public DetallePagoDTO(Integer id, String nombre, double cantidad, double precio, double total, Vuelo vuelo,
            Hospedaje hospedaje) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.total = total;
        this.vuelo = vuelo;
        this.hospedaje = hospedaje;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public double getCantidad() {
        return cantidad;
    }
    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }
    public double getPrecio() {
        return precio;
    }
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }

    public Integer getPersonas() {
		return personas;
	}
	public void setPersonas(Integer personas) {
		this.personas = personas;
	}
	public DetallePagoDTO() {

    }

    public DetallePagoDTO(Integer id, String nombre, double cantidad, double precio, double total) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.total = total;
    }
    
    public DetallePagoDTO(String nombre, double cantidad, double precio, double total) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.total = total;
    }
    public DetallePagoDTO(Integer id, double cantidad, double precio, double total) {
        this.id = id;
        this.cantidad = cantidad;
        this.precio = precio;
        this.total = total;
    }

    
    public Vuelo getVuelo() {
        return vuelo;
    }
    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }
    public Hospedaje getHospedaje() {
        return hospedaje;
    }
    public void setHospedaje(Hospedaje hospedaje) {
        this.hospedaje = hospedaje;
    }
	@Override
	public String toString() {
		return "DetallePagoDTO [id=" + id + ", nombre=" + nombre + ", cantidad=" + cantidad + ", precio=" + precio
				+ ", total=" + total + ", vuelo=" + vuelo + ", hospedaje=" + hospedaje + ", personas=" + personas + "]";
	}

    
    
    
    


}