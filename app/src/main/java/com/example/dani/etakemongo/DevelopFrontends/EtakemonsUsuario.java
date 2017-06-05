package com.example.dani.etakemongo.DevelopFrontends;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
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

public class EtakemonsUsuario extends AppCompatActivity {

    String tag = "EtakemonsUsuario";

       ListView listView;
////    String[] web2 = {
////    };
//    ArrayList<String> web2 = new ArrayList<>();
//    Integer[] imageId2 = {
//            R.drawable.davidos,
//            R.drawable.livanny,
//    };
    private List<Captura> capturaList = new ArrayList<>();
    private List<Captura> listarecibida;
    private List<String> listacapturas;
    int idloged,idusuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.etakemons_usuario);

        idusuario = getIntent().getExtras().getInt("id");    //tenemos el id del usuario para hacer las llamadas q sean necesarias
        idloged = idusuario;

        try{
            doLista();
        }
        catch (Exception ex){
            ex.getMessage();
        }

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

                    listView = (ListView) findViewById(R.id.listEtakemons);
                    listarecibida = (List<Captura>) response.body();
                    listacapturas = new ArrayList<String>();

                    for (int i = 0; i < listarecibida.size(); i++) {
                        Captura captura = listarecibida.get(i);
                        listacapturas.add(captura.getNombreetakemon());
                    }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>
                            (EtakemonsUsuario.this, android.R.layout.simple_list_item_1, listacapturas);
                    listView.setAdapter(arrayAdapter);
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
