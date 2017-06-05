package com.example.dani.etakemongo.DevelopFrontends;

import android.content.Intent;
import android.icu.text.IDNA;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dani.etakemongo.Modelo.Logros;
import com.example.dani.etakemongo.Modelo.Usuario;
import com.example.dani.etakemongo.R;
import com.example.dani.etakemongo.SysTools.GitHubClient;
import com.example.dani.etakemongo.SysTools.RetrofitOwn;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

    public class InformacionUsuario extends AppCompatActivity {
        private ListView listaUser;
        private ImageButton ajustes, informacion;
        String tag = "Info"; // tag que indica el ciclo de vida de la app

        String emailloged, emiliologed;

        private int nivel, expActual, expNextLevel, progresoFin;
        private double res1, progreso;
        private ProgressBar progresoNivel;

        private TextView tvNick;
        private TextView tvNivel;
        private TextView tvExperiencia;

        private TextView tvExpeActual;
        private TextView tvExpeNext;

        private List<String> logrosList;
        private List<Logros> listarecibida;

        private FloatingActionButton fabExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_usuario);

        tvNick = (TextView) findViewById(R.id.tvUsuarionick);
        tvNivel = (TextView) findViewById(R.id.tvSetNivel);
        tvExperiencia = (TextView) findViewById(R.id.tvSetExperiencia);
        progresoNivel = (ProgressBar) findViewById(R.id.pb_nivel);

        tvExpeActual = (TextView) findViewById(R.id.tvExpActual);
        tvExpeNext = (TextView) findViewById(R.id.tvExpNext);

        emailloged = getIntent().getExtras().getString("emailsito");
        emiliologed = emailloged;

        fabExit = (FloatingActionButton) findViewById(R.id.fab_exit_infousuario);
        fabExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intres = getIntent();
                setResult(RESULT_OK, intres);
                finish();
            }
        });

        try{
            doGetData();
        }
        catch (Exception ex){
            ex.getMessage();
        }

        try{
            doGetLogros();
        }
        catch (Exception ex){
            ex.getMessage();
        }


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

        public void doGetData(){
            RetrofitOwn retro = new RetrofitOwn();
            Retrofit retrofit = retro.getObjectRetrofit();


            // Create an instance of our GitHub API interface.
            final GitHubClient usuario = retrofit.create(GitHubClient.class);

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

                            tvExpeActual.setText(String.valueOf(usuariologeado.getNivel()));
                            tvExpeNext.setText(String.valueOf(usuariologeado.getNivel()+1));

                            //barra de nivel
                            nivel = usuariologeado.getNivel();
                            expActual = usuariologeado.getExperiencia();

                            System.out.println("ACUTAAAAAAAAL"+expActual);

                            if (nivel <=5){
                                expNextLevel = 1000;
                                res1 = expNextLevel-expActual;

                                progreso = 100 - (res1/15);
                                progresoFin = (int) progreso;

                                progresoNivel.setProgress(progresoFin);

                            }
                            else if(nivel >= 5 && nivel<= 10){
                                expNextLevel = 1500;
                                res1 = expNextLevel-expActual;

                                progreso = 100 - (res1/15);
                                progresoFin = (int) progreso;

                                progresoNivel.setProgress(progresoFin);
                            }
                            else {
                                expNextLevel = 2000;
                                res1 = expNextLevel-expActual;

                                progreso = 100 - (res1/15);
                                progresoFin = (int) progreso;

                                progresoNivel.setProgress(progresoFin);
                            }


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

        public void doGetLogros(){
            RetrofitOwn retro = new RetrofitOwn();
            Retrofit retrofit = retro.getObjectRetrofit();


            // Create an instance of our GitHub API interface.
            GitHubClient logros = retrofit.create(GitHubClient.class);

            // Create a call instance for looking up Retrofit contributors.
            Call<List<Logros>> call = logros.getLogrosUSer(emiliologed);

            call.enqueue(new Callback<List<Logros>>() {
                @Override
                public void onResponse(Call<List<Logros>> call, Response<List<Logros>> response) {

                    if (response.isSuccessful()){
                        listaUser = (ListView) findViewById(R.id.listLogros);
                        listarecibida = (List<Logros>) response.body();
                        logrosList  = new ArrayList<String>();

                        for (int j = 0; j < listarecibida.size(); j++) {
                            Logros item = listarecibida.get(j);
                            logrosList.add(item.getNombre()+"\n       "+item.getDescripcion());
                        }
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>
                                (InformacionUsuario.this, android.R.layout.simple_list_item_1, logrosList);
                        listaUser.setAdapter(arrayAdapter);
                    }
                    else {
                        Toast.makeText(InformacionUsuario.this, "response unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Logros>> call, Throwable t) {
                    Toast.makeText(InformacionUsuario.this, "No hay conexión", Toast.LENGTH_SHORT).show();
                    Log.d(tag, "ERROR en el recogido de logros del usuario");
                }
            });
        }
}



