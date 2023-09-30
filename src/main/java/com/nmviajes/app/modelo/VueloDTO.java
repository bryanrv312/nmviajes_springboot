package com.nmviajes.app.modelo;

import java.sql.Date;

public class VueloDTO {
    private Long id;

    private String origen;

    private String destino;

    private String aerolinea;

    private Integer pasajeros;

    private Date fecha;

    private Double precio;

    public VueloDTO(String origen, String destino, String aerolinea, Integer pasajeros, Date fecha, Double precio) {
        this.origen = origen;
        this.destino = destino;
        this.aerolinea = aerolinea;
        this.pasajeros = pasajeros;
        this.fecha = fecha;
        this.precio = precio;
    }

    

    public VueloDTO(String origen, String destino, String aerolinea, Date fecha, Double precio) {
        this.origen = origen;
        this.destino = destino;
        this.aerolinea = aerolinea;
        this.fecha = fecha;
        this.precio = precio;
    }



    public VueloDTO(Long id, String origen, String destino, Integer pasajeros, Date fecha, Double precio) {
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.pasajeros = pasajeros;
        this.fecha = fecha;
        this.precio = precio;
    }

    public VueloDTO(String origen, String destino, Integer pasajeros, Date fecha, Double precio) {
        this.origen = origen;
        this.destino = destino;
        this.pasajeros = pasajeros;
        this.fecha = fecha;
        this.precio = precio;
    }

    public VueloDTO(Long id, String origen, String destino, String aerolinea, Integer pasajeros, Date fecha,
            Double precio) {
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.aerolinea = aerolinea;
        this.pasajeros = pasajeros;
        this.fecha = fecha;
        this.precio = precio;
    }

    public VueloDTO() {
    }

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

    public String getAerolinea() {
        return aerolinea;
    }

    public void setAerolinea(String aerolinea) {
        this.aerolinea = aerolinea;
    }

}
