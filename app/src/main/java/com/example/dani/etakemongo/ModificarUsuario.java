package com.example.dani.etakemongo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.dani.etakemongo.Modelo.Usuario;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
         String spassword=password.getText().toString();

        com.example.dani.etakemongo.RetrofitOwn retro = new com.example.dani.etakemongo.RetrofitOwn();
        Retrofit retrofit = retro.getObjectRetrofit();


        // Create an instance of our GitHub API interface.
        GitHubClient registrar = retrofit.create(GitHubClient.class);
        Usuario usuario = new Usuario(snick,spassword);

        // Create a call instance for looking up Retrofit contributors.
        Call<Usuario> call = registrar.modificar(usuario); //porque??

        // Fetch and print a list of the contributors to the library.
        call.enqueue(new Callback() {

            //***************Comprobacion de que recoge los datos**********
            @Override
            public void onResponse(Call call, Response response) {
                Usuario contributor = (Usuario) response.body();
                Log.d(tag, "Registrado correctamente");

                //devuelvo por el intent que me habia llamado el email del usuario registrado
                //y el codigo de respuesta 1600 (OK) a la pantalla LOGIN
                //para alli mostrarle al usuario el email de forma que pueda loguearse
                Intent intres = getIntent();
                intres.putExtra("Cambios realizados correctamente",semail);//coger el email del login para volver atras
                setResult(1600, intres);
                finish();

            }
    });
    //*/
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


