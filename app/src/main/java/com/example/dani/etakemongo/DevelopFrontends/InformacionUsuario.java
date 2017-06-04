package com.example.dani.etakemongo.DevelopFrontends;

import android.icu.text.IDNA;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dani.etakemongo.Modelo.Usuario;
import com.example.dani.etakemongo.R;
import com.example.dani.etakemongo.SysTools.GitHubClient;
import com.example.dani.etakemongo.SysTools.RetrofitOwn;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

    public class InformacionUsuario extends AppCompatActivity {
        private ListView listaUser;
        private ImageButton ajustes, informacion;
        String tag = "Info"; // tag que indica el ciclo de vida de la app

        String emailloged, emiliologed;

        private TextView tvNick;
        private TextView tvNivel;
        private TextView tvExperiencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_usuario);

        tvNick = (TextView) findViewById(R.id.tvUsuarionick);
        tvNivel = (TextView) findViewById(R.id.tvSetNivel);
        tvExperiencia = (TextView) findViewById(R.id.tvSetExperiencia);

        emailloged = getIntent().getExtras().getString("emailsito");
        emiliologed = emailloged;

        RetrofitOwn retro = new RetrofitOwn();
        Retrofit retrofit = retro.getObjectRetrofit();


        // Create an instance of our GitHub API interface.
        GitHubClient usuario = retrofit.create(GitHubClient.class);

        // Create a call instance for looking up Retrofit contributors.
        Call<Usuario> call = usuario.getUsuario(emiliologed);

        // Fetch and print a list of the contributors to the library.
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {

                if (response.isSuccessful()) {
                    Usuario usuariologeado = (Usuario) response.body();

                    try{
                    tvNick.setText(usuariologeado.getNick());
                    tvNivel.setText(String.valueOf(usuariologeado.getNivel()));
                    tvExperiencia.setText(String.valueOf(usuariologeado.getExperiencia()));
                    }
                    catch (Exception e){
                        e.getMessage();
                    }
                }
                else {
                    Toast.makeText(InformacionUsuario.this, "response unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(InformacionUsuario.this, "No hay conexión", Toast.LENGTH_SHORT).show();
                Log.d(tag, "ERROR en el recogido de datos del usuario");
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



