package com.example.dani.etakemongo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    private EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.etEmail);
        password = (EditText) findViewById(R.id.etPassword);


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = new Retrofit.Builder()
                // .baseUrl("https://translation.googleapis.com")
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create());


        Retrofit retrofit =
                builder
                        .client(
                                httpClient.build()
                        )
                        .build();

      //  GitHubClient client = retrofit.create(GitHubClient.class);

//        Call call = client.google();


        //NUEVO CODIGO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // Create an instance of our GitHub API interface.
        GitHubClient github = retrofit.create(GitHubClient.class);

        // Create a call instance for looking up Retrofit contributors.
        Call<List<Contributor>> call = github.contributors("munozloisivan", "EetakemonGoCBL");

        // Fetch and print a list of the contributors to the library.

            //FIN NUEVO CODIGO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response){
                List<Contributor> contributors = (List<Contributor>)response.body();
                System.out.println("HOLAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA*********");
                for (Contributor contributor : contributors) {
                    System.out.println(contributor.login + " (" + contributor.contributions + ")");
                }
            }
            @Override
            public void onFailure(Call call, Throwable t){
                Toast.makeText(Login.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
/*
        List<Contributor> contributors = null;

        for (Contributor contributor : contributors) {
            System.out.println(contributor.login + " (" + contributor.contributions + ")");
        }
try {
            contributors = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
 */
/*
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response){
                response.body();

            }
            @Override
            public void onFailure(Call call, Throwable t){
                Toast.makeText(Login.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        /*/










