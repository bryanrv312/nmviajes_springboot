package com.nmviajes.app.modelo;

public class HospedajeDTO {
    private Long id;

   
	private String nombre;

    
    private String ubicacion;
   
    private Double precio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public HospedajeDTO(Long id, String nombre, String ubicacion, Double precio) {
        this.id = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.precio = precio;
    }
    

    public HospedajeDTO(String nombre, String ubicacion, Double precio) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.precio = precio;
    }

    public HospedajeDTO() {
    }

    
}
