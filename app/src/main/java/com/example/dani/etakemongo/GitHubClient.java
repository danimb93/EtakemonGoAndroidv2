package com.example.dani.etakemongo;



import com.example.dani.etakemongo.Modelo.Usuario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Dani on 15/05/2017.
 */
public interface GitHubClient {

        /*@GET("/repos/{owner}/{repo}/contributors")
        Call<List<Contributor>> contributors(
                @Path("owner") String owner,
                @Path("repo") String repo);
        */

        @POST("/myapp/usuario/login")
        Call<Usuario> login(@Body Usuario body);

        @POST("/myapp/usuario/new")
        Call<Usuario> registrar(@Body Usuario body);
}



/*

*/