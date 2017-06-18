package com.example.dani.etakemongo.ProductionFrontends;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.dani.etakemongo.Modelo.Captura;
import com.example.dani.etakemongo.R;

public class game extends AppCompatActivity {

    private Captura capturarecibed;
    private Captura capturaToUse;

    private int idrecibed;
    private int idToUse;

    private Bundle recibed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

//        recibed = getIntent().getBundleExtra("captura");

          capturarecibed = (Captura) getIntent().getSerializableExtra("captura");
          capturaToUse = capturarecibed;

          idrecibed = getIntent().getExtras().getInt("userLoger");
          idToUse = idrecibed;
//
            Toast.makeText(game.this,capturaToUse.getNombreetakemon()+" "+capturaToUse.getHabilidadetakemon()+" "+idToUse, Toast.LENGTH_LONG).show();
    }
}
