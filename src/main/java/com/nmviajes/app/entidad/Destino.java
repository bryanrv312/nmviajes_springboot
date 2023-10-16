package com.nmviajes.app.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "destino")
public class Destino {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nombreAP")
    private String nombreAP;
    
    @Column(name = "ciudad")
    private String ciudad;
    
    @Column(name = "codCiudad")
    private String codCiudad;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreAP() {
		return nombreAP;
	}

	public void setNombreAP(String nombreAP) {
		this.nombreAP = nombreAP;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getCodCiudad() {
		return codCiudad;
	}

	public void setCodCiudad(String codCiudad) {
		this.codCiudad = codCiudad;
	}

	@Override
	public String toString() {
		return "Destino [id=" + id + ", nombreAP=" + nombreAP + ", ciudad=" + ciudad + ", codCiudad=" + codCiudad
				+ ", getId()=" + getId() + ", getNombreAP()=" + getNombreAP() + ", getCiudad()=" + getCiudad()
				+ ", getCodCiudad()=" + getCodCiudad() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
    
    
	
}
