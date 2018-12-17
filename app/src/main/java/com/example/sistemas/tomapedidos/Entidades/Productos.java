package com.example.sistemas.tomapedidos.Entidades;

import java.io.Serializable;

public class Productos implements Serializable {

    private String Nombre;
    private String Codigo;
    private String stock;

    public Productos(String nombre, String codigo, String stock) {
        Nombre = nombre;
        Codigo = codigo;
        this.stock = stock;
    }

    public Productos() {
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        Codigo = codigo;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }
}
