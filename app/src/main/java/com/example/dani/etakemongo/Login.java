package com.example.dani.etakemongo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.gson.Gson;
import com.google.gson.internal.UnsafeAllocator;

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
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.etEmail);
        password = (EditText) findViewById(R.id.etPassword);
        login= (Button) findViewById(R.id.btnLogin);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String semail=  email.getText().toString();
                String spassword= password.getText().toString();

                System.out.println("***********DATOS**************************");

                OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl("http://172.16.10.111:8080")
                        .addConverterFactory(GsonConverterFactory.create());
//
                Retrofit retrofit =
                        builder
                                .client(
                                        httpClient.build()
                                )
                                .build();

                // Create an instance of our GitHub API interface.
                GitHubClient login = retrofit.create(GitHubClient.class);
                Usuario usuario= new Usuario(semail, spassword);

                // Create a call instance for looking up Retrofit contributors.
                Call<Usuario> call = login.login(usuario);
                System.out.println("***********DATOS**************************");


                // Fetch and print a list of the contributors to the library.
                call.enqueue(new Callback() {

                    //***************Comprobacion de que recoge los datos**********
                    @Override
                    public void onResponse(Call call, Response response) {
                        Usuario contributor = (Usuario) response.body();
                        System.out.println(contributor.getEmail() +" y "+ contributor.getContrasena());
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(Login.this, t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    public void abrirRegistrar (View view) {
        Intent i = new Intent(this, GoogleMap.class);
        startActivity(i);
    }
    public void abrirRecuperar (View view) {
        Intent i = new Intent(this, RecuperarDatos.class);
        startActivity(i);
    }
}
