package com.nmviajes.app.modelo;

public class PagoDTO {
    private Long id;

	private String correo;

	private String metodo;

	private Double precioFinal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public Double getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(Double precioFinal) {
        this.precioFinal = precioFinal;
    }

    public PagoDTO(Long id, String correo, String metodo, Double precioFinal) {
        this.id = id;
        this.correo = correo;
        this.metodo = metodo;
        this.precioFinal = precioFinal;
    }

    public PagoDTO(String correo, String metodo, Double precioFinal) {
        this.correo = correo;
        this.metodo = metodo;
        this.precioFinal = precioFinal;
    }

    public PagoDTO() {
    }
    
}
