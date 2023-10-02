package com.nmviajes.app.entidad;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vuelos") 
public class Vuelo {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	@Column(name = "origen")
	private String origen;
    
    
	@Column(name = "destino")
	private String destino;

    @Column(name = "aerolinea")
    private String aerolinea;

    @Column(name = "pasajeros")
    private Integer pasajeros;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "precio")
    private Double precio;
    
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Integer getPasajeros() {
        return pasajeros;
    }

    public void setPasajeros(Integer pasajeros) {
        this.pasajeros = pasajeros;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    
    public Vuelo(String origen, String destino, String aerolinea, Date fecha, Double precio) {
        this.origen = origen;
        this.destino = destino;
        this.aerolinea = aerolinea;
        this.fecha = fecha;
        this.precio = precio;
    }

   
    public Vuelo(Long id, String origen, String destino, String aerolinea, Integer pasajeros, Date fecha,
            Double precio) {
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.aerolinea = aerolinea;
        this.pasajeros = pasajeros;
        this.fecha = fecha;
        this.precio = precio;
    }

    public Vuelo(String origen, String destino, String aerolinea, Integer pasajeros, Date fecha, Double precio) {
        this.origen = origen;
        this.destino = destino;
        this.aerolinea = aerolinea;
        this.pasajeros = pasajeros;
        this.fecha = fecha;
        this.precio = precio;
    }

    public Vuelo(Long id, String origen, String destino, Integer pasajeros, Date fecha, Double precio) {
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.pasajeros = pasajeros;
        this.fecha = fecha;
        this.precio = precio;
    }

    public Vuelo(String origen, String destino, Integer pasajeros, Date fecha, Double precio) {
        this.origen = origen;
        this.destino = destino;
        this.pasajeros = pasajeros;
        this.fecha = fecha;
        this.precio = precio;
    }

    public Vuelo() {
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
        Vuelo other = (Vuelo) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Vuelo [origen=" + origen + ", destino=" + destino + ", pasajeros=" + pasajeros + ", fecha=" + fecha
                + ", precio=" + precio + "]";
    }

    public String getAerolinea() {
        return aerolinea;
    }

    public void setAerolinea(String aerolinea) {
        this.aerolinea = aerolinea;
    }

    
    
}
