package com.example.dani.etakemongo.Modelo;



import java.sql.Date;

/**
 * Created by ivanm on 24/04/2017.
 */

public class Cofre{

    private int id;
    private String nombre, descripcion;
    private Date temporizador;

    public Cofre(int id, String nombre, String descripcion, Date temporizador) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.temporizador = temporizador;
    }

    public Cofre(){}

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

    public Date getTemporizador() {
        return temporizador;
    }

    public void setTemporizador(Date temporizador) {
        this.temporizador = temporizador;
    }
}
