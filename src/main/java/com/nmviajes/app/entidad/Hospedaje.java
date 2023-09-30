package com.nmviajes.app.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "hospedajes") 
public class Hospedaje {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(name = "nombre")
	private String nombre;

    @Column(name = "ubicacion")
    private String ubicacion;
    

    @Column(name = "precio")
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

    public Hospedaje(Long id, String nombre, String ubicacion, Double precio) {
        this.id = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.precio = precio;
    }

    
    public Hospedaje(String nombre, String ubicacion, Double precio) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.precio = precio;
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
        Hospedaje other = (Hospedaje) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public Hospedaje() {
    }

    @Override
    public String toString() {
        return "Hospedaje [nombre=" + nombre + ", ubicacion=" + ubicacion + ", precio=" + precio + "]";
    }

    

}
