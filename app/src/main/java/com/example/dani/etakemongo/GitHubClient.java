package com.example.dani.etakemongo;



import com.example.dani.etakemongo.Modelo.Usuario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Dani on 15/05/2017.
 */
public interface GitHubClient {


        @POST("/myapp/usuario/login")
        Call<Usuario> login(@Body Usuario body);

        @POST("/myapp/usuario/new")
        Call<Usuario> registrar(@Body Usuario body);

        @POST("/myapp/usuario/edit")
        Call<Usuario> modificar (@Body Usuario body);

        @POST ("/myapp/usuario/delete")
        Call<Usuario> borrar (@Body Usuario body);

        @POST ("/myapp/usuario/datos")
        Call<Usuario> recuperardatos(@Body Usuario body);

}



/*

*/