package com.example.sistemas.tomapedidos.Entidades;

import java.io.Serializable;

public class Productos implements Serializable {

    private String Nombre;
    private String Codigo;
    private String Stock;
    private String Precio;
    private String Cantidad;
    private String Flete;

    public Productos() {
    }


    public Productos(String nombre, String codigo, String stock, String precio, String cantidad, String flete) {
        Nombre = nombre;
        Codigo = codigo;
        Stock = stock;
        Precio = precio;
        Cantidad = cantidad;
        Flete = flete;
    }

    public String getFlete() {
        return Flete;
    }

    public void setFlete(String flete) {
        Flete = flete;
    }

    public String getCantidad() {
        return Cantidad;
    }

    public void setCantidad(String cantidad) {
        Cantidad = cantidad;
    }

    public String getStock() {
        return Stock;
    }

    public void setStock(String stock) {
        Stock = stock;
    }

    public String getPrecio() {
        return Precio;
    }

    public void setPrecio(String precio) {
        Precio = precio;
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

}
