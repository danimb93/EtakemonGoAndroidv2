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
import com.google.android.gms.maps.GoogleMap;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    String tag = "Login"; // tag que indica el ciclo de vida de la app
    private EditText email, password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d(tag, "Event onCreate()");

        email = (EditText) findViewById(R.id.etEmail);
        password = (EditText) findViewById(R.id.etPassword);
        login = (Button) findViewById(R.id.btnLogin);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String semail = email.getText().toString();
                String spassword = password.getText().toString();

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
                GitHubClient login = retrofit.create(GitHubClient.class);
                Usuario usuario = new Usuario(semail, spassword);

                // Create a call instance for looking up Retrofit contributors.
                Call<Usuario> call = login.login(usuario);
                System.out.println("***********DATOS**************************");


                // Fetch and print a list of the contributors to the library.
                call.enqueue(new Callback() {

                    //***************Comprobacion de que recoge los datos**********
                    @Override
                    public void onResponse(Call call, Response response) {
                        Usuario contributor = (Usuario) response.body();
                        goToMapsActivity(v);  //si se ha logueado llamas a la funcion que te pasa a la siguiente actividad
                        Log.d(tag, "Logueado correctamente");
                        System.out.println(contributor.getEmail() + " y " + contributor.getContrasena());
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(Login.this, t.toString(), Toast.LENGTH_SHORT).show();
                        Log.d(tag, "ERROR al loguear");
                    }
                });
            }
        });

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bundle result = data.getExtras();

        //operar segun el resultCode recibido por las otras actividades
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

    public void abrirRegistrar (View view) {
        Intent intent = new Intent(this, Registrar.class);
        startActivityForResult(intent, 200);
    }
    public void abrirRecuperar (View view) {
        Intent intent = new Intent(this, RecuperarDatos.class);
        startActivityForResult(intent, 300);
    }

    public void goToMapsActivity(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivityForResult(intent, 100);    //ponemos el codigo 100 para monitorizar esta actividad y sus futuros resultados
    }
}
