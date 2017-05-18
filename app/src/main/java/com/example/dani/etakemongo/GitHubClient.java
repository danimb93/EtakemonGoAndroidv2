package com.example.dani.etakemongo;



import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Dani on 15/05/2017.
 */
public interface GitHubClient {
/*    @GET("/language/translate/v2")
    Call google();
    */
@GET("/repos/{owner}/{repo}/contributors")
Call<List<Contributor>> contributors(
        @Path("owner") String owner,
        @Path("repo") String repo);
        }
/*

*/