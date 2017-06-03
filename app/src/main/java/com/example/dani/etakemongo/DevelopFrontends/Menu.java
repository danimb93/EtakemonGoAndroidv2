package com.example.dani.etakemongo.DevelopFrontends;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.dani.etakemongo.R;

public class Menu extends AppCompatActivity {
    String tag = "Menu";

    ImageButton botonMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        botonMail = (ImageButton) findViewById(R.id.btn_ticket_menu);
        botonMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                startActivity(Intent.createChooser(emailIntent, "Selecciona aplicación..."));

                Intent intent = getIntent();
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle result = data.getExtras();

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
