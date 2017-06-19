package com.example.dani.etakemongo.DevelopFrontends;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.telecom.Call;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import java.util.ArrayList;
import java.util.List;

import com.example.dani.etakemongo.Modelo.Etakemon;
import com.example.dani.etakemongo.R;
import com.example.dani.etakemongo.SysTools.CustomListEtakedex;
import com.example.dani.etakemongo.SysTools.GitHubClient;
import com.example.dani.etakemongo.SysTools.RetrofitOwn;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Etakemon_list extends AppCompatActivity {

    private FloatingActionButton fabExit;
    private  ListView listView;
    private ArrayList<Etakemon> listarecibida;
    private ArrayList<Etakemon> listaetakemons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etakemon_list);

        listView = (ListView) findViewById(R.id.list);

        try{
            doEtakedex();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        fabExit = (FloatingActionButton) findViewById(R.id.fab_exit_etakedex);
        fabExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intres = getIntent();
                setResult(RESULT_OK, intres);
                finish();
            }
        });

    }

    public void doEtakedex(){

        RetrofitOwn retro = new RetrofitOwn();
        Retrofit retrofit = retro.getObjectRetrofit();

        GitHubClient etakedex = retrofit.create(GitHubClient.class);

        retrofit2.Call<List<Etakemon>> call = etakedex.getListaEtakemons();

        call.enqueue(new Callback<List<Etakemon>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Etakemon>> call, Response<List<Etakemon>> response) {
                if (response.isSuccessful()){
                    listarecibida = (ArrayList<Etakemon>) response.body();
                    listaetakemons = new ArrayList<Etakemon>();

                    for (int i = 0; i < listarecibida.size(); i++){
                        listaetakemons.add(listarecibida.get(i));
                    }

                    EtakedexListAdapter adapter = new EtakedexListAdapter(getApplicationContext(), R.layout.activity_etakemon_list, listaetakemons);
                    listView.setAdapter(adapter);
                }
                else{
                    Toast.makeText(Etakemon_list.this, "Petición erronea!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<List<Etakemon>> call, Throwable t) {
                Toast.makeText(Etakemon_list.this, "Error al conectar", Toast.LENGTH_SHORT).show();

            }
        });
    }

}