package com.nmviajes.app.entidad;
import java.util.Date;

import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "itinerario")
public class Itinerario {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "hora")
    private Time hora;
    
    @Column(name = "fecha")
    private Date fecha;
    
    @ManyToOne
    @JoinColumn(name = "origen_id")
    private Origen origen;
    
    @ManyToOne
    @JoinColumn(name = "destino_id")
    private Destino destino;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Time getHora() {
		return hora;
	}

	public void setHora(Time hora) {
		this.hora = hora;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "Itinerario [id=" + id + ", hora=" + hora + ", fecha=" + fecha + ", getId()=" + getId() + ", getHora()="
				+ getHora() + ", getFecha()=" + getFecha() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
    
    
}
