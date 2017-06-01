package com.example.dani.etakemongo.SysTools;



import com.example.dani.etakemongo.Modelo.*;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Dani on 15/05/2017.
 */
public interface GitHubClient {

//login
        @POST("usuario/login")
        Call<Usuario> login(@Body Usuario body);

//Registro
        @POST("usuario/new")
        Call<Usuario> registrar(@Body Usuario body);

//Modificar datos usuario
        @POST("usuario/edit")
        Call<Usuario> modificar (@Body Usuario body);

//Eliminar Usuario
        @GET ("usuario/delete")
        Call<Usuario> borrar (@Body Usuario body);

//Recuperar datos por correo
        @POST ("usuario/datos")
        Call<Usuario> recuperardatos(@Body String body);

//Devolver lista de objetos
        @GET("usuario/get_objetos")
        Call<List<com.example.dani.etakemongo.Modelo.Objetos>> ListaObjetos();

//Devolver lista  informacion usuario (experiencia actual, siguiente nivel)
        @GET ("usuario/got_email")
        Call<List<Usuario>> ListaExperiencia();



}



/*

*/