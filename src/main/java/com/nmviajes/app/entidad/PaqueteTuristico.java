package com.nmviajes.app.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "paqueteturisticos")
public class PaqueteTuristico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "lugarTuristico")
    private String lugarTuristico;
    
    @Column(name = "estado")
    private Integer estado;
    
    @Column(name = "detalles")
    private String detalles;
    
    @Column(name = "precio")
    private Double precio;
    
    @Column(name = "pasajeros")
    private int pasajeros;
    
    @Column(name = "habitaciones")
    private int habitaciones;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "hospedaje_id")
    private Hospedaje hospedaje;

    @ManyToOne
    @JoinColumn(name = "vuelo_id")
    private Vuelo vuelo;

    @ManyToOne
    @JoinColumn(name = "pago_id")
    private Pago pago;

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

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Hospedaje getHospedaje() {
        return hospedaje;
    }

    public void setHospedaje(Hospedaje hospedaje) {
        this.hospedaje = hospedaje;
    }

    public Vuelo getVuelo() {
        return vuelo;
    }

    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }
    

    public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public int getPasajeros() {
		return pasajeros;
	}

	public void setPasajeros(int pasajeros) {
		this.pasajeros = pasajeros;
	}

	public int getHabitaciones() {
		return habitaciones;
	}

	public void setHabitaciones(int habitaciones) {
		this.habitaciones = habitaciones;
	}

	public PaqueteTuristico(String lugarTuristico, Integer estado, String detalles, Usuario usuario,
            Hospedaje hospedaje, Vuelo vuelo, Pago pago) {
        this.lugarTuristico = lugarTuristico;
        this.estado = estado;
        this.detalles = detalles;
        this.usuario = usuario;
        this.hospedaje = hospedaje;
        this.vuelo = vuelo;
        this.pago = pago;
    }

    public PaqueteTuristico(Long id, String lugarTuristico, Integer estado, String detalles, Usuario usuario,
            Hospedaje hospedaje, Vuelo vuelo, Pago pago) {
        this.id = id;
        this.lugarTuristico = lugarTuristico;
        this.estado = estado;
        this.detalles = detalles;
        this.usuario = usuario;
        this.hospedaje = hospedaje;
        this.vuelo = vuelo;
        this.pago = pago;
    }

    public PaqueteTuristico(Long id, String lugarTuristico, Integer estado, String detalles, Usuario usuario,
            Hospedaje hospedaje, Vuelo vuelo) {
        this.id = id;
        this.lugarTuristico = lugarTuristico;
        this.estado = estado;
        this.detalles = detalles;
        this.usuario = usuario;
        this.hospedaje = hospedaje;
        this.vuelo = vuelo;
    }

    public PaqueteTuristico(String lugarTuristico, Integer estado, String detalles, Usuario usuario,
            Hospedaje hospedaje, Vuelo vuelo) {
        this.lugarTuristico = lugarTuristico;
        this.estado = estado;
        this.detalles = detalles;
        this.usuario = usuario;
        this.hospedaje = hospedaje;
        this.vuelo = vuelo;
    }

    public PaqueteTuristico(Long id, String lugarTuristico, Integer estado, String detalles, Usuario usuario,
            Hospedaje hospedaje) {
        this.id = id;
        this.lugarTuristico = lugarTuristico;
        this.estado = estado;
        this.detalles = detalles;
        this.usuario = usuario;
        this.hospedaje = hospedaje;
    }

    public PaqueteTuristico(Long id, String lugarTuristico, Integer estado, String detalles, Usuario usuario) {
        this.id = id;
        this.lugarTuristico = lugarTuristico;
        this.estado = estado;
        this.detalles = detalles;
        this.usuario = usuario;
    }

    public PaqueteTuristico(String lugarTuristico, Integer estado, String detalles) {

        this.lugarTuristico = lugarTuristico;
        this.estado = estado;
        this.detalles = detalles;
    }

    public PaqueteTuristico(Long id) {
        this.id = id;
    }

    public PaqueteTuristico(Long id, String lugarTuristico, Integer estado) {
        this.id = id;
        this.lugarTuristico = lugarTuristico;
        this.estado = estado;
    }

    public PaqueteTuristico(Long id, Usuario usuario) {
        this.id = id;
        this.usuario = usuario;
    }

    public PaqueteTuristico() {
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
        PaqueteTuristico other = (PaqueteTuristico) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

	@Override
	public String toString() {
		return "PaqueteTuristico [id=" + id + ", lugarTuristico=" + lugarTuristico + ", estado=" + estado
				+ ", detalles=" + detalles + ", precio=" + precio + ", pasajeros=" + pasajeros + ", habitaciones="
				+ habitaciones + ", usuario=" + usuario + ", hospedaje=" + hospedaje + ", vuelo=" + vuelo + ", pago="
				+ pago + "]";
	}

	
    
    

}
