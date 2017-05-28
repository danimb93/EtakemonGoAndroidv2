package com.example.dani.etakemongo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dani.etakemongo.Modelo.Usuario;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecuperarDatos extends AppCompatActivity {

    String tag = "RecuperarDatos";

    private EditText email;
    private Button recuperardatos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_datos);
        Log.d(tag, "Event onCreate()");

        email = (EditText) findViewById(R.id.etEmail);
        recuperardatos = (Button) findViewById(R.id.btnRecuperar);

        recuperardatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final String sssemail = email.getText().toString();

                System.out.println("***********DATOS**************************");

                OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:8080")                //poner esta para atacar a la api nuestra 10.0.2.2
                        .addConverterFactory(GsonConverterFactory.create());
//
                Retrofit retrofit =
                        builder
                                .client(
                                        httpClient.build()
                                )
                                .build();

                // Create an instance of our GitHub API interface.
                GitHubClient recuperardatos = retrofit.create(GitHubClient.class);
              //  Usuario usuario = new Usuario(semail);

                // Create a call instance for looking up Retrofit contributors.
                Call<Usuario> call = recuperardatos.recuperardatos(sssemail);
                System.out.println("***********DATOS**************************");


                // Fetch and print a list of the contributors to the library.
                call.enqueue(new Callback() {

                    //***************Comprobacion de que recoge los datos**********
                    @Override
                    public void onResponse(Call call, Response response) {
                        Usuario contributor = (Usuario) response.body();
                        //si se ha logueado llamas a la funcion que te pasa a la siguiente actividad
                        Log.d(tag, "Email enviado correctamente");
                        String msg = "email enviado correctamente";
                        Toast.makeText(RecuperarDatos.this, msg.toString() ,Toast.LENGTH_LONG).show();
                        Intent intres = getIntent();
                        intres.putExtra("enviado",sssemail);
                        setResult(1700, intres);
                        finish();
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                       // Toast.makeText(RecuperarDatos.this, t.toString(), Toast.LENGTH_SHORT).show();
                        Log.d(tag, "ERROR al enviar el email");
                        Intent intres = getIntent();
                        setResult(RESULT_CANCELED, intres);
                        finish();
                    }
                });
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(tag, "Event onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(tag, "Event onResume()");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(tag, "Event onPause()");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(tag, "Event onStop()");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(tag, "Event onRestart()");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(tag, "Event onDestroy()");

    }


}
