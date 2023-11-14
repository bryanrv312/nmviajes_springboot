package com.nmviajes.app.entidad;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
 
//hola bryan desde mi rama

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
    
    @Column(name = "imagen")
    private String imagen = "no-image.png";
    
    //@Column(name = "fechaPartida", columnDefinition = "DATE")
    @Column(name = "fechaPartida")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaPartida;
    
    
    @Column(name = "fechaRegreso")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaRegreso;
    
     

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
    
    public LocalDate getFechaPartida() {
		return fechaPartida;
	}

	public void setFechaPartida(LocalDate fechaPartida) {
		this.fechaPartida = fechaPartida;
	}

	public LocalDate getFechaRegreso() {
		return fechaRegreso;
	}

	public void setFechaRegreso(LocalDate fechaRegreso) {
		this.fechaRegreso = fechaRegreso;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public Vuelo(Long id, String origen, String destino, String aerolinea, Integer pasajeros, Date fecha, Double precio,
			LocalDate fechaPartida, LocalDate fechaRegreso, String imagen) {
		super();
		this.id = id;
		this.origen = origen;
		this.destino = destino;
		this.aerolinea = aerolinea;
		this.pasajeros = pasajeros;
		this.fecha = fecha;
		this.precio = precio;
		this.fechaPartida = fechaPartida;
		this.fechaRegreso = fechaRegreso;
		this.imagen = imagen;
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

  

    public String getAerolinea() {
        return aerolinea;
    }

    public void setAerolinea(String aerolinea) {
        this.aerolinea = aerolinea;
    }
    
    
    /*cambiar imagen a null para q el example haga el match correspondiente en search_2*/
    public void reset() {
    	this.imagen = null;
    }
    
    
	@Override
	public String toString() {
		return "Vuelo [id=" + id + ", origen=" + origen + ", destino=" + destino + ", aerolinea=" + aerolinea
				+ ", pasajeros=" + pasajeros + ", fecha=" + fecha + ", precio=" + precio + ", imagen=" + imagen
				+ ", fechaPartida=" + fechaPartida + ", fechaRegreso=" + fechaRegreso + "]";
	}
    
}
