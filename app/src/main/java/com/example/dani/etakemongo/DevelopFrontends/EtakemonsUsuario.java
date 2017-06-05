package com.example.dani.etakemongo.DevelopFrontends;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dani.etakemongo.Modelo.Captura;
import com.example.dani.etakemongo.R;
import com.example.dani.etakemongo.SysTools.CustomListMisEtakemons;
import com.example.dani.etakemongo.SysTools.GitHubClient;
import com.example.dani.etakemongo.SysTools.RetrofitOwn;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EtakemonsUsuario extends AppCompatActivity {

    String tag = "EtakemonsUsuario";

    ListView listView;
//    String[] web2 = {
//    };
    ArrayList<String> web2 = new ArrayList<>();
    Integer[] imageId2 = {
            R.drawable.davidos,
            R.drawable.livanny,
    };
    private List<Captura> capturaList = new ArrayList<>();
    int idloged,idusuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.etakemons_usuario);

        idusuario = getIntent().getExtras().getInt("id");    //tenemos el id del usuario para hacer las llamadas q sean necesarias
        idloged = idusuario;


        listView = (ListView) findViewById(R.id.listEtakemons);

            RetrofitOwn retrofitOwn = new RetrofitOwn();
            Retrofit retrofit = retrofitOwn.getObjectRetrofit();


            //Creamos una instancia de retrofit
            GitHubClient etakemons = retrofit.create(GitHubClient.class);

            //Hacemos la llamada http
            Call<List<Captura>> call = etakemons.listaCapturas(7);  //habra que pasarle el id usuario hasta aqui

            call.enqueue(new Callback<List<Captura>>() {
                @Override
                public void onResponse(Call<List<Captura>> call, Response<List<Captura>> response) {

                    System.out.println("RESPONSEE:"+response);
                    if (response.isSuccessful()){
                    capturaList = (List<Captura>) response.body();
                    System.out.println("TAAAAAAAAAAAAAAAAAAAAAAAAAAAMAÑOOOOOOOOOOOOOOOOOOO   " + capturaList.size());
                    for (int i = 0; i < capturaList.size(); i++) {
                        web2.add(capturaList.get(i).getNombreetakemon());
                        //     imageId2[i] = capturaList.get(i);         hay que añadirle imagen al etakemon en la bbddd la url
                    }
                    CustomListMisEtakemons adapter2 = new CustomListMisEtakemons(EtakemonsUsuario.this, web2, imageId2);
                    listView.setAdapter(adapter2);
                       }
                else{
                    //Si los datos son erroneos y el user no esta
                    Toast.makeText(EtakemonsUsuario.this, "Peticion erronea", Toast.LENGTH_SHORT).show();
                }
                }

                @Override
                public void onFailure(Call<List<Captura>> call, Throwable t) {
                    Toast.makeText(EtakemonsUsuario.this, "No hay conexión", Toast.LENGTH_SHORT).show();
                    Log.d(tag, "ERROR al conectar");
                }
            });




    }








}
