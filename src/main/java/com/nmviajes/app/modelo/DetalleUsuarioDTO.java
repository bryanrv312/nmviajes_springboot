package com.nmviajes.app.modelo;

public class DetalleUsuarioDTO {

	private Long id;

    private String correo;
    
    private String dni;

	private String numeroCompra;

	private String asunto;

	private String tipoatencion;

	private String estadia;
    
    
	private String reclamo;


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


    public String getDni() {
        return dni;
    }


    public void setDni(String dni) {
        this.dni = dni;
    }


    public String getNumeroCompra() {
        return numeroCompra;
    }


    public void setNumeroCompra(String numeroCompra) {
        this.numeroCompra = numeroCompra;
    }


    public String getAsunto() {
        return asunto;
    }


    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }


    public String getTipoatencion() {
        return tipoatencion;
    }


    public void setTipoatencion(String tipoatencion) {
        this.tipoatencion = tipoatencion;
    }


    public String getEstadia() {
        return estadia;
    }


    public void setEstadia(String estadia) {
        this.estadia = estadia;
    }


    public String getReclamo() {
        return reclamo;
    }


    public void setReclamo(String reclamo) {
        this.reclamo = reclamo;
    }


    public DetalleUsuarioDTO(Long id, String correo, String dni, String numeroCompra, String asunto,
            String tipoatencion, String estadia, String reclamo) {
        this.id = id;
        this.correo = correo;
        this.dni = dni;
        this.numeroCompra = numeroCompra;
        this.asunto = asunto;
        this.tipoatencion = tipoatencion;
        this.estadia = estadia;
        this.reclamo = reclamo;
    }


    public DetalleUsuarioDTO(String correo, String dni, String numeroCompra, String asunto, String tipoatencion,
            String estadia, String reclamo) {
        this.correo = correo;
        this.dni = dni;
        this.numeroCompra = numeroCompra;
        this.asunto = asunto;
        this.tipoatencion = tipoatencion;
        this.estadia = estadia;
        this.reclamo = reclamo;
    }


    public DetalleUsuarioDTO() {
    }

    

    
}
