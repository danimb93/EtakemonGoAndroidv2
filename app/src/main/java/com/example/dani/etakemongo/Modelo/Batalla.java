package com.example.dani.etakemongo.Modelo;

import java.sql.Date;

/**
 * Created by ivanm on 24/04/2017.
 */
public class Batalla{

    private int id, idcaptura, resultado, experiencia;
    private Date fecha;

    public Batalla(int id, int idcaptura, int resultado, int experiencia, Date fecha) {
        this.id = id;
        this.idcaptura = idcaptura;
        this.resultado = resultado;
        this.experiencia = experiencia;
        this.fecha = fecha;
    }

    public Batalla(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdcaptura() {
        return idcaptura;
    }

    public void setIdcaptura(int idcaptura) {
        this.idcaptura = idcaptura;
    }

    public int getResultado() {
        return resultado;
    }

    public void setResultado(int resultado) {
        this.resultado = resultado;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
