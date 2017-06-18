package com.example.dani.etakemongo.DevelopFrontends;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dani.etakemongo.Modelo.Captura;
import com.example.dani.etakemongo.R;
import com.example.dani.etakemongo.SysTools.CustomListMisEtakemons;
import com.example.dani.etakemongo.SysTools.GitHubClient;
import com.example.dani.etakemongo.SysTools.RetrofitOwn;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.bumptech.glide.Glide;

import static com.example.dani.etakemongo.R.layout.etakemons_usuario;

public class EtakemonsUsuario extends AppCompatActivity {


    String tag = "EtakemonsUsuario";

       ListView listView;

    private ArrayList<Captura> listarecibida;
    private ArrayList<Captura> listacapturas;
    int idloged,idusuario;

    FloatingActionButton fabExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(etakemons_usuario);

        listView = (ListView) findViewById(R.id.listEtakemons);

        idusuario = getIntent().getExtras().getInt("id");    //tenemos el id del usuario para hacer las llamadas q sean necesarias
        idloged = idusuario;

        try{
            doLista();
        }
        catch (Exception ex){
            ex.getMessage();
        }

        fabExit = (FloatingActionButton) findViewById(R.id.fab_goMenufrometakemons);
        fabExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intres = getIntent();
                setResult(RESULT_OK, intres);
                finish();
            }
        });

    }

    public void doLista(){
        RetrofitOwn retro = new RetrofitOwn();
        Retrofit retrofit = retro.getObjectRetrofit();

        GitHubClient etakemonsusuario = retrofit.create(GitHubClient.class);

        Call<List<Captura>> call = etakemonsusuario.getCapturasUsuario(idloged);

        call.enqueue(new Callback<List<Captura>>() {
            @Override
            public void onResponse(Call<List<Captura>> call, Response<List<Captura>> response) {
                if (response.isSuccessful()){


                    listarecibida = (ArrayList<Captura>) response.body();
                    listacapturas = new ArrayList<Captura>();

                    for (int i = 0; i < listarecibida.size(); i++) {
                        Captura captura = listarecibida.get(i);
                        listacapturas.add(captura);
                    }

                    EtakemonsUsuarioListAdapter adapter = new EtakemonsUsuarioListAdapter(getApplicationContext(), R.layout.etakemons_usuario, listacapturas);
                    listView.setAdapter(adapter);
                }
                else{
                    Toast.makeText(EtakemonsUsuario.this, "Peticion erronea!", Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(Call<List<Captura>> call, Throwable t) {
                System.out.println(t.toString());
                Toast.makeText(EtakemonsUsuario.this, t.toString(), Toast.LENGTH_SHORT).show();
                 Log.d(tag, "ERROR al conectar!");
            }
        });
    }








}
