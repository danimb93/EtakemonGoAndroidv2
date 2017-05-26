package com.example.dani.etakemongo;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public abstract class AjustesUsuario extends AppCompatActivity {

    String tag = "AjustesUsuario";
    private Button modificar, borrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes_usuario);
        Log.d(tag, "Event onCreate()");

        modificar = (Button) findViewById(R.id.btnModificar);
        //PORQUE NO VA?
        //borrar=(Button) findViewById(R.id.btnBorrar);
    }

    public void abrirModificar(View view) {
        Intent intent = new Intent(this, ModificarUsuario.class);
        startActivityForResult(intent, 400);
    }

    public void abrirBorrar(View view) {
        Intent intent = new Intent(this, Borrar.class);
        startActivityForResult(intent, 500);
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

    public abstract void onCreateDialog(Bundle savedInstanceState);

}
