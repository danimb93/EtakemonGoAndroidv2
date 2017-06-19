package com.example.dani.etakemongo.ProductionFrontends;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.dani.etakemongo.DevelopFrontends.EtakemonsUsuario;
import com.example.dani.etakemongo.Modelo.Captura;
import com.example.dani.etakemongo.R;

public class BatallaEtakemons extends AppCompatActivity {

    Captura capturaBatalla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batalla_etakemons);

        capturaBatalla = (Captura) getIntent().getSerializableExtra("captura");

        Toast.makeText(BatallaEtakemons.this, capturaBatalla.getNombreetakemon().toString()+capturaBatalla.getHabilidadetakemon()+capturaBatalla.getAtaque()+" "+capturaBatalla.getVida(), Toast.LENGTH_LONG).show();

        

    }
}
