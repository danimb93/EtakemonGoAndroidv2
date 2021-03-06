package com.example.dani.etakemongo.ProductionFrontends;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class RecuperarDatos extends AppCompatActivity {

    String tag = "RecuperarDatos";

    private EditText email;
    private Button recuperardatos;
    private FloatingActionButton fabexit;

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

                //RETROFIT
                RetrofitOwn retrofitOwn = new RetrofitOwn();
                Retrofit retrofit = retrofitOwn.getObjectRetrofit();

                //Creamos una instancia de retrofit
                GitHubClient recuperardatos = retrofit.create(GitHubClient.class);
              //  Usuario usuario = new Usuario(semail);

                //Hacemos la llamada http
                Call<Usuario> call = recuperardatos.recuperardatos(sssemail);
                System.out.println("***********DATOS**************************");


                //Recibimos la llamada
                call.enqueue(new Callback() {

                    //***************Comprobacion de que recoge los datos**********
                    @Override
                    public void onResponse(Call call, Response response) {
                        if (response.isSuccessful()){
                            Usuario contributor = (Usuario) response.body();
                            //si se ha logueado llamas a la funcion que te pasa a la siguiente actividad
                            Log.d(tag, "Email enviado correctamente");
                            Toast.makeText(RecuperarDatos.this, "email enviado correctamente" ,Toast.LENGTH_SHORT).show();
                            Intent intres = getIntent();
                            intres.putExtra("enviado",sssemail);
                            setResult(1700, intres);
                            finish();
                        }
                        else {
                            Toast.makeText(RecuperarDatos.this, "Error en la petición", Toast.LENGTH_SHORT).show();
                        }

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

        fabexit = (FloatingActionButton) findViewById(R.id.fab_exit_recuperar);
        fabexit.setOnClickListener(new View.OnClickListener() {
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
