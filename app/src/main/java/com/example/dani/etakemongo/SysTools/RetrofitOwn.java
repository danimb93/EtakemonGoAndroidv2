package com.example.dani.etakemongo.SysTools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dani on 27/05/2017.
 */

public class RetrofitOwn {

    public Retrofit getObjectRetrofit(){

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = new retrofit2.Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/myapp/")                //poner esta para atacar a la api nuestra 10.0.2.2
                .addConverterFactory(GsonConverterFactory.create(gson));

        Retrofit retrofit =
                builder
                        .client(
                                httpClient.build()
                        )
                        .build();


        return retrofit;
    }
}