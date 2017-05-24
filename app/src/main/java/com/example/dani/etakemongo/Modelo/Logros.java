package com.example.dani.etakemongo.Modelo;


/**
 * Created by ivanm on 24/04/2017.
 */
public class Logros {

    private int id;
    private String nombre, descripcion;
    private int experiencia;

    public Logros(int id, String nombre, String descripcion, int experiencia) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.experiencia = experiencia;
    }

    public Logros(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

}
