package com.example.dani.etakemongo.Modelo;


import java.sql.Date;

/**
 * Created by ivanm on 24/04/2017.
 */
public class Captura{

    private int id, idusuariosss, idetakemon, idlocalizacion, nivel, experiencia, vida, ataque, defensa, estado;
    private Date fecha;

    public Captura(int id, int idusuariosss, int idetakemon, int idlocalizacion, int nivel, int experiencia, int vida, int ataque, int defensa, int estado, Date fecha) {
        this.id = id;
        this.idusuariosss = idusuariosss;
        this.idetakemon = idetakemon;
        this.idlocalizacion = idlocalizacion;
        this.nivel = nivel;
        this.experiencia = experiencia;
        this.vida = vida;
        this.ataque = ataque;
        this.defensa = defensa;
        this.estado = estado;
        this.fecha = fecha;
    }

    //para los SPAWNS de las capturas
    public Captura(int idetakemon, int idlocalizacion){
        this.idetakemon = idetakemon;
        this.idlocalizacion = idlocalizacion;
    }

    public Captura(int idusuariosss, int idetakemon, int idlocalizacion, int estado, Date fecha){
        this.idusuariosss = idusuariosss;
        this.idetakemon = idetakemon;
        this.idlocalizacion = idlocalizacion;
        this.estado = estado;
        this.fecha = fecha;
    }

    public Captura(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdusuariosss() {
        return idusuariosss;
    }

    public void setIdusuariosss(int idusuariosss) {
        this.idusuariosss = idusuariosss;
    }

    public int getIdetakemon() {
        return idetakemon;
    }

    public void setIdetakemon(int idetakemon) {
        this.idetakemon = idetakemon;
    }

    public int getIdlocalizacion() {
        return idlocalizacion;
    }

    public void setIdlocalizacion(int idlocalizacion) {
        this.idlocalizacion = idlocalizacion;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
