package com.example.dani.etakemongo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void abrirRegistrar (View view){
        Intent i=new Intent(this,Registrar.class);
        startActivity(i);
    }
    public void goToRecuperarDatos(View view){
        Intent inb1 = new Intent(this,RecuperarDatos.class);
        startActivity(inb1);
    }


}
