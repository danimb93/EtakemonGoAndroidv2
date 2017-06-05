package com.example.dani.etakemongo.ProductionFrontends;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.dani.etakemongo.DevelopFrontends.InformacionUsuario;
import com.example.dani.etakemongo.Modelo.Logros;
import com.example.dani.etakemongo.Modelo.Objetos;
import com.example.dani.etakemongo.R;
import com.example.dani.etakemongo.SysTools.GitHubClient;
import com.example.dani.etakemongo.SysTools.RetrofitOwn;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ObjetosUsuario extends AppCompatActivity {

    String tag = "ObjetosUsuario";

    String emailloged, emiliologed;

    ListView listViewobjetos;
    private List<Objetos> listarecibida;
    private List<String> objetoslist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objetos);

        emailloged = getIntent().getExtras().getString("emailsito");
        emiliologed = emailloged;

        try{
            doGetObjetos();
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

    public void doGetObjetos(){
        RetrofitOwn retro = new RetrofitOwn();
        Retrofit retrofit = retro.getObjectRetrofit();


        // Create an instance of our GitHub API interface.
        final GitHubClient objetos = retrofit.create(GitHubClient.class);

        // Create a call instance for looking up Retrofit contributors.
        Call<List<Objetos>> call = objetos.getObjetosUser(emiliologed);

        call.enqueue(new Callback<List<Objetos>>() {
            @Override
            public void onResponse(Call<List<Objetos>> call, Response<List<Objetos>> response) {

                if (response.isSuccessful()){
                    listViewobjetos = (ListView) findViewById(R.id.listObjetos);
                   listarecibida = (List<Objetos>) response.body();
                   objetoslist  = new ArrayList<String>();

                    for (int j = 0; j < listarecibida.size(); j++) {
                        Objetos item = listarecibida.get(j);
                        objetoslist.add(String.valueOf(item.getId()));
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>
                            (ObjetosUsuario.this, android.R.layout.simple_list_item_1, objetoslist);
                            listViewobjetos.setAdapter(arrayAdapter);
                }
                else {
                    Toast.makeText(ObjetosUsuario.this, "response unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Objetos>> call, Throwable t) {
                Toast.makeText(ObjetosUsuario.this, "No hay conexión", Toast.LENGTH_SHORT).show();
                Log.d(tag, "ERROR en el recogido de logros del usuario");
            }
        });
    }

}
