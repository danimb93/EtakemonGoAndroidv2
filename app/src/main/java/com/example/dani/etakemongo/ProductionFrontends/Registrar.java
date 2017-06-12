package com.example.dani.etakemongo.ProductionFrontends;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dani.etakemongo.Modelo.Usuario;
import com.example.dani.etakemongo.R;
import com.example.dani.etakemongo.SysTools.GitHubClient;
import com.example.dani.etakemongo.SysTools.RetrofitOwn;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Registrar extends AppCompatActivity {

    //Variables necesarias de los objetos del layout
    String tag = "Registrar";
    private EditText nombre,nick,password,email,emailR;
    private Button registrar;
    private TextView datos;

    private FloatingActionButton fabExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        Log.d(tag, "Event onCreate()");

        //Traemos a las variables los objetos del layout

        nombre=(EditText)findViewById(R.id.etNombre);
        nick=(EditText)findViewById(R.id.etNick);
        password=(EditText)findViewById(R.id.etPassword);
        email=(EditText)findViewById(R.id.etEmail);
        emailR=(EditText)findViewById(R.id.etEmailR);
        registrar=(Button)findViewById(R.id.btnRegistrar);

//Al pulsar el boton lanzamos la activadad de registrar
        registrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){

                final String snombre= nombre.getText().toString();
                String snick = nick.getText().toString();
                String spassword=password.getText().toString();
                final String semail= email.getText().toString();
                String semailR= emailR.getText().toString();
                //RETROFIT
                RetrofitOwn retrofitOwn = new RetrofitOwn();
                Retrofit retrofit = retrofitOwn.getObjectRetrofit();

                //Creamos una instancia de retrofit
                GitHubClient registrar = retrofit.create(GitHubClient.class);
                Usuario usuario = new Usuario(snombre,snick,semail,spassword);

                //Hacemos la llamada http
                Call<Usuario> call = registrar.registrar(usuario);

                //Recibimos la llamada
                call.enqueue(new Callback() {
                    //Si va bien va al login
                        @Override
                        public void onResponse(Call call, Response response) {
                            Usuario contributor = (Usuario) response.body();
                            Log.d(tag, "Registrado correctamente");

                            //devuelvo por el intent que me habia llamado el email del usuario registrado
                            //y el codigo de respuesta 1600 (OK) a la pantalla LOGIN
                            //para alli mostrarle al usuario el email de forma que pueda loguearse
                            Intent intres = getIntent();
                            intres.putExtra("registrado",semail);
                            setResult(1600, intres);
                            finish();
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Toast.makeText(Registrar.this, t.toString(), Toast.LENGTH_SHORT).show();
                            Log.d(tag, "ERROR al Registrar");
                            Intent intres = getIntent();
                            setResult(RESULT_CANCELED, intres);
                            finish();
                        }
                    });
                }
            });

        fabExit = (FloatingActionButton) findViewById(R.id.fab_exit_registrar);
        fabExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                setResult(RESULT_OK, intent);
                finish();
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







