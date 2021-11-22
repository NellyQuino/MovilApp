package com.example.myapplication.ui.entity;

public class ListElement {
    public String color;
    public String titulo;
    public String descripcion;

    public ListElement(String color, String titulo, String descripcion) {
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
}
