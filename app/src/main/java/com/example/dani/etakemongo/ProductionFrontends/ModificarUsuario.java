package com.example.dani.etakemongo.ProductionFrontends;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.dani.etakemongo.Modelo.Usuario;
import com.example.dani.etakemongo.R;
import com.example.dani.etakemongo.SysTools.GitHubClient;
import com.example.dani.etakemongo.SysTools.RetrofitOwn;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ModificarUsuario extends AppCompatActivity {

    String tag = "ModificarUsuario";
    private EditText nick, password;
    private Button modificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_usuario);
        Log.d(tag, "Event onCreate()");

        nick=(EditText)findViewById(R.id.etNick);
        password=(EditText)findViewById(R.id.etPassword);
        modificar=(Button)findViewById(R.id.btnModificar);


         String snick= nick.getText().toString();
         final String spassword=password.getText().toString();

//                             **************RETROFIT**************************
        RetrofitOwn retro = new RetrofitOwn();
        Retrofit retrofit = retro.getObjectRetrofit();

        // Create an instance of our GitHub API interface.
        GitHubClient modificar = retrofit.create(GitHubClient.class);
        Usuario usuario = new Usuario(snick,spassword);

        // Create a call instance for looking up Retrofit contributors.
        Call<Usuario> call = modificar.modificar(usuario);

        // Fetch and print a list of the contributors to the library.
        call.enqueue(new Callback() {

            //***************Comprobacion de que recoge los datos**********
            @Override
            public void onResponse(Call call, Response response) {
                Usuario contributor = (Usuario) response.body();
                Log.d(tag, "Los datos han sido modificados");

                //devuelvo por el intent que me habia llamado el email del usuario registrado
                //y el codigo de respuesta 1600 (OK) a la pantalla LOGIN
                //para alli mostrarle al usuario el email de forma que pueda loguearse
               Intent intres = new Intent(ModificarUsuario.this,Login.class);
                startActivity(intres);

            }
//Esto es por si hay error
            @Override
            public void onFailure(Call call, Throwable t) {

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


