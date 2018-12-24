package com.example.sistemas.tomapedidos.Entidades;

import java.io.Serializable;

public class Productos implements Serializable {

    private String Descripcion;
    private String Nombre;
    private String Codigo;
    private String Stock;
    private String Precio;
    private String Cantidad;
    private String Flete;
    private String IdProducto;
    private String Marca;
    private String Unidad;
    private String Estado;

    public Productos(String descripcion, String nombre, String codigo, String stock, String precio,
                     String cantidad, String flete, String idProducto, String marca, String unidad,
                     String estado) {
        Descripcion = descripcion;
        Nombre = nombre;
        Codigo = codigo;
        Stock = stock;
        Precio = precio;
        Cantidad = cantidad;
        Flete = flete;
        IdProducto = idProducto;
        Marca = marca;
        Unidad = unidad;
        Estado = estado;
    }

    public Productos() {
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getIdProducto() {
        return IdProducto;
    }

    public void setIdProducto(String idProducto) {
        IdProducto = idProducto;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String marca) {
        Marca = marca;
    }

    public String getUnidad() {
        return Unidad;
    }

    public void setUnidad(String unidad) {
        Unidad = unidad;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
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


    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        Codigo = codigo;
    }

}
