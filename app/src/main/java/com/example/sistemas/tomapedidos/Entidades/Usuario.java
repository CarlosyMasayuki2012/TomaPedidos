package com.example.sistemas.tomapedidos.Entidades;

import java.io.Serializable;

public class Usuario implements Serializable {

    private String usuario;
    private String clave;
    private String codigo;
    private String nombre;
    private String apellido;
    private String moneda;
    private String almacen;

    public Usuario(String usuario, String clave, String codigo, String nombre, String apellido,
                   String moneda, String almacen) {
        this.usuario = usuario;
        this.clave = clave;
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.moneda = moneda;
        this.almacen = almacen;
    }

    public Usuario() {
    }

    public String getMoneda() { return moneda; }

    public void setMoneda(String moneda) { this.moneda = moneda; }

    public String getAlmacen() { return almacen; }

    public void setAlmacen(String almacen) { this.almacen = almacen; }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
