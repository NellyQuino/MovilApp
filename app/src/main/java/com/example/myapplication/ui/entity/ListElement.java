package com.example.myapplication.ui.entity;

import java.io.Serializable;

public class ListElement implements Serializable {
    public String color;
    public String titulo;
    public String descripcion;
    public int imagen;

    public ListElement(int imagen, String color, String titulo, String descripcion) {
        this.imagen = imagen;
        this.color = color;
        this.titulo = titulo;
        this.descripcion = descripcion;
    }


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getImagen() { return imagen; }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
