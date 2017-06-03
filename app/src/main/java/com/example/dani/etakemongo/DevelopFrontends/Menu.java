package com.example.dani.etakemongo.DevelopFrontends;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.dani.etakemongo.Modelo.Objetos;
import com.example.dani.etakemongo.R;

public class Menu extends AppCompatActivity {

    String tag = "Menu";

    private boolean enviado;

    FloatingActionButton botonMail, botonWeb, botonReturnMap, botonEtakedex, botonEtakemons, botonUsuario, botonObjetos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

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
                                Uri.parse("http://10.0.2.2:8080"));   // cuando sea publico cambiar aqui la URL
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

        botonEtakedex = (FloatingActionButton) findViewById(R.id.fab_etakedex_menu);
        botonEtakedex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, Etakedex.class);
                startActivityForResult(intent, 930);
            }
        });

        botonEtakemons = (FloatingActionButton) findViewById(R.id.fab_etakemons_menu);
        botonEtakemons.setOnClickListener(new View.OnClickListener() {
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
                startActivityForResult(intent, 910);
            }
        });

        botonObjetos = (FloatingActionButton) findViewById(R.id.fab_objetos_menu);
        botonObjetos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, Objetos.class);
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
