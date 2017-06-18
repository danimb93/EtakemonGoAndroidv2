package com.example.dani.etakemongo.DevelopFrontends;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.dani.etakemongo.Modelo.Objetos;
import com.example.dani.etakemongo.Modelo.Usuario;
import com.example.dani.etakemongo.ProductionFrontends.Login;
import com.example.dani.etakemongo.ProductionFrontends.ObjetosUsuario;
import com.example.dani.etakemongo.R;
import com.example.dani.etakemongo.SysTools.GitHubClient;
import com.example.dani.etakemongo.SysTools.RetrofitOwn;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Menu extends AppCompatActivity {

    String tag = "Menu";

    String emailusuario, emailloged;
    int idloged, idlogedparatodos;
    private boolean enviado;

    FloatingActionButton botonMail, botonWeb, botonReturnMap, botonEtakedex, botonEtakemons, botonUsuario, botonObjetos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        emailusuario = getIntent().getExtras().getString("email2");
        emailloged = emailusuario;

        doGetData(emailloged);

        botonMail = (FloatingActionButton) findViewById(R.id.fab_ticket);
        botonMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                enviado = false;

                try {
                    String textoEmail = "Explicación de la consulta / incidencia: \n";
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"eetakemongocbl@gmail.com"});
                    emailIntent.putExtra(Intent.EXTRA_CC, new String[]{"munozloisivan@gmail.com"});
                    emailIntent.putExtra(Intent.EXTRA_BCC, new String[]{"roberto.arranz.93@gmail.com"});
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Ticket de usuario");
                    //Recordad que la barra invertida más "n" es un salto de linea "n" así, escribiremos el email con varios saltos de linea.
                    String textoApp = "Envio un email desde mi App de android Creado gracias al equipo de desarrollo de EtakemonGoCBL";
                    emailIntent.putExtra(Intent.EXTRA_TEXT, textoEmail);
                    emailIntent.setType("message/rfc822");
                    //Damos la opción al usuario que elija desde que app enviamos el email.
                    startActivityForResult(Intent.createChooser(emailIntent, "Selecciona aplicación..."),940);

                    Intent intent = getIntent();
                    setResult(RESULT_OK,intent);
                    finish();
                    enviado = true;
                }
                catch (Exception ex){
                    ex.getMessage();
                }
            }
        });

        botonWeb = (FloatingActionButton) findViewById(R.id.fab_redireccion_web);
        botonWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("http://147.83.7.155:8080"));   // cuando sea publico cambiar aqui la URL
                startActivity(viewIntent);
            }
        });

        botonReturnMap = (FloatingActionButton) findViewById(R.id.fab_gomaps);
        botonReturnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });

        botonEtakemons = (FloatingActionButton) findViewById(R.id.fab_etakemons_menu);
        botonEtakemons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, EtakemonsUsuario.class);
                intent.putExtra("id",idlogedparatodos);
                startActivityForResult(intent, 930);
            }
        });

        botonEtakedex = (FloatingActionButton) findViewById(R.id.fab_etakedex_menu);
        botonEtakedex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, Etakemon_list.class);
                startActivityForResult(intent, 920);
            }
        });

        botonUsuario = (FloatingActionButton) findViewById(R.id.fab_user_menu);
        botonUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, InformacionUsuario.class);
                intent.putExtra("emailsito",emailloged);
                startActivityForResult(intent, 910);
            }
        });

        botonObjetos = (FloatingActionButton) findViewById(R.id.fab_objetos_menu);
        botonObjetos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, ObjetosUsuario.class);
                intent.putExtra("emailsito",emailloged);
                intent.putExtra("id",idlogedparatodos);
                startActivityForResult(intent, 900);
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle result = data.getExtras();



        //900 objetos

        //910 usuario

        //920 etakemon list

        // 930 etakedex

        //940 envio ticket

    }

    public void doGetData(String em){
        RetrofitOwn retrofitOwn = new RetrofitOwn();
        Retrofit retrofit = retrofitOwn.getObjectRetrofit();


        //Creamos una instancia de retrofit
        GitHubClient datos = retrofit.create(GitHubClient.class);

        //Hacemos la llamada http
        Call<Usuario> call2 = datos.getUsuario(em);

        call2.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {

                if (response.isSuccessful()){
                    Usuario usuario = new Usuario();
                    // idusuario = usuario.getId();
                    idloged = response.body().getId();
                    idlogedparatodos = idloged;
                 //   Toast.makeText(Menu.this, String.valueOf(idlogedparatodos), Toast.LENGTH_SHORT).show();
                    Log.d(tag, "Datos obtenidos correctamente");

                }
                else{
                    Log.d(tag, "Datos mal cogidos");
                }

            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(Menu.this, "Error al obtener datos del usuario", Toast.LENGTH_SHORT).show();
                Log.d(tag, "No conectado para coger datos");
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

