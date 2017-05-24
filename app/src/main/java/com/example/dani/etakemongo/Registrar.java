package com.example.dani.etakemongo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dani.etakemongo.Modelo.Usuario;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Registrar extends AppCompatActivity {

    String tag = "Registrar";
    private EditText nombre,nick,password,email,emailR;
    private Button registrar;
    private TextView datos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        Log.d(tag, "Event onCreate()");

        nombre=(EditText)findViewById(R.id.etNombre);
        nick=(EditText)findViewById(R.id.etNick);
        password=(EditText)findViewById(R.id.etPassword);
        email=(EditText)findViewById(R.id.etEmail);
        emailR=(EditText)findViewById(R.id.etEmailR);
        registrar=(Button)findViewById(R.id.btnRegistrar);
        datos=(TextView)findViewById(R.id.tvRespuesta);


        registrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){

                String snombre= nombre.getText().toString();
                String snick = nick.getText().toString();
                String spassword=password.getText().toString();
                String semail= email.getText().toString();
                String semailR= emailR.getText().toString();


                datos.setText(snombre + snick + spassword + semail);


             //prueba chunga
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
                    GitHubClient registrar = retrofit.create(GitHubClient.class);
                    Usuario usuario = new Usuario(snombre,snick,semail,spassword);

                    // Create a call instance for looking up Retrofit contributors.
                    Call<Usuario> call = registrar.registrar(usuario);
                    System.out.println("***********DATOS**************************");


                    // Fetch and print a list of the contributors to the library.
                    call.enqueue(new Callback() {

                        //***************Comprobacion de que recoge los datos**********
                        @Override
                        public void onResponse(Call call, Response response) {
                            Usuario contributor = (Usuario) response.body();
                            Log.d(tag, "Registrado correctamente");
                            System.out.println(contributor.getEmail() + " y " + contributor.getContrasena());
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Toast.makeText(Registrar.this, t.toString(), Toast.LENGTH_SHORT).show();
                            Log.d(tag, "ERROR al Registrar");
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







