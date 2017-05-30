package com.example.dani.etakemongo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.dani.etakemongo.Modelo.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

    public class InformacionUsuario extends AppCompatActivity {
        private ListView listaUser;
        private ImageButton ajustes, informacion;
        String tag = "Info"; // tag que indica el ciclo de vida de la app

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_usuario);
        listaUser= (ListView) findViewById(R.id.lvUsuario);
        ajustes=(ImageButton)findViewById(R.id.btnAjustes);
        informacion=(ImageButton)findViewById(R.id.btnInfo);

        com.example.dani.etakemongo.RetrofitOwn retro = new com.example.dani.etakemongo.RetrofitOwn();
        Retrofit retrofit = retro.getObjectRetrofit();


        // Create an instance of our GitHub API interface.
        GitHubClient mostrarEstadisticas = retrofit.create(GitHubClient.class);
        Usuario usuario = new Usuario();


        // Create a call instance for looking up Retrofit contributors.
        Call<List<Usuario>> call = mostrarEstadisticas.ListaExperiencia();

        // Fetch and print a list of the contributors to the library.
        call.enqueue(new Callback() {

            //***************Comprobacion de que recoge los datos**********
            @Override
            public void onResponse(Call call, Response response) {
                Usuario contributor = (Usuario) response.body();
                Log.d(tag, "Datos mostrados");
    }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }
}



