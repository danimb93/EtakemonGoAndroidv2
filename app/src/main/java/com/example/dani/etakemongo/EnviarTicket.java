package com.example.dani.etakemongo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.content.Intent;


public class EnviarTicket extends AppCompatActivity {

    String tag = "EnviarTicket"; // tag que indica el ciclo de vida de la app

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_ticket);

        Log.d(tag, "Event onCreate()");


        Button botonMail = (Button) findViewById(R.id.btnTicket);
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
}
