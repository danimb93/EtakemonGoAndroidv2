package com.example.dani.etakemongo.ProductionFrontends;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dani.etakemongo.DevelopFrontends.EtakemonsUsuario;
import com.example.dani.etakemongo.Modelo.Batalla;
import com.example.dani.etakemongo.Modelo.Captura;
import com.example.dani.etakemongo.R;
import com.example.dani.etakemongo.SysTools.ImageLoadTask;

public class BatallaEtakemons extends AppCompatActivity {

    Captura capturaBatalla;
    Captura capturaRival;
    ImageView eetakemonBatalla;
    ImageView eetakemonRival;
    TextView atk, def, hp, atkRival, defRival, hpRival;
    Button atacar, defender, recargar;
    RadioButton energy;
    ProgressBar vida, vidaRival;
    int idUsuario;
    Boolean energyRival;
    int hpMeBatalla, hpRivalBatalla;

  //  MediaPlayer ring2;


    String tag = "Login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batalla_etakemons);

    //   ring2 = MediaPlayer.create(BatallaEtakemons.this,R.raw.batalla);
      //  ring2.start();

        capturaBatalla = (Captura) getIntent().getSerializableExtra("captura");
        capturaRival = (Captura) getIntent().getSerializableExtra("rival");
        idUsuario = getIntent().getExtras().getInt("idusuario");

//        Toast.makeText(BatallaEtakemons.this, capturaBatalla.getNombreetakemon().toString()+capturaBatalla.getHabilidadetakemon()+capturaBatalla.getAtaque()+" "+capturaBatalla.getVida(), Toast.LENGTH_LONG).show();

        //Botones
        atacar = (Button) findViewById(R.id.attack);
        defender = (Button) findViewById(R.id.defense);
        recargar = (Button) findViewById(R.id.reload);

        //Eetakemon que entrenas
        eetakemonBatalla = (ImageView) findViewById(R.id.eetakemonImgBatalla);
        new ImageLoadTask(capturaBatalla.getImagen(),eetakemonBatalla).execute();

        atk = (TextView) findViewById(R.id.atk);
        atk.setText(atk.getText()+Integer.toString(capturaBatalla.getAtaque()));

        def = (TextView) findViewById(R.id.def);
        def.setText(def.getText()+ Integer.toString(capturaBatalla.getDefensa()));

        hp = (TextView) findViewById(R.id.hp);
        hp.setText(Integer.toString(capturaBatalla.getVida()));

        energy = (RadioButton) findViewById(R.id.energy);
        vida = (ProgressBar) findViewById(R.id.vida);
        vida.setProgress(100);

        //Eetakemon rival
        eetakemonRival = (ImageView) findViewById(R.id.eetakemonImgRival);
        new ImageLoadTask(capturaRival.getImagen(),eetakemonRival).execute();

        atkRival = (TextView) findViewById(R.id.atkRival);
        atkRival.setText(atkRival.getText()+Integer.toString(capturaRival.getAtaque()));

        defRival = (TextView) findViewById(R.id.defRival);
        defRival.setText(defRival.getText()+ Integer.toString(capturaRival.getDefensa()));

        hpRival = (TextView) findViewById(R.id.hpRival);
        hpRival.setText(Integer.toString(capturaRival.getVida()));

        vidaRival = (ProgressBar) findViewById(R.id.vidaRival);
        vidaRival.setProgress(100);

        energy.setChecked(true);
        energyRival = true;

        hpMeBatalla = capturaBatalla.getVida();
        hpRivalBatalla = capturaRival.getVida();

        if (energy.isChecked())
        {
//            recargar.setActivated(false);
            recargar.setClickable(false);
        }

        atacar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int movRival = selectionRival();

                if (movRival == 1){
                    hpRival.setText(Integer.toString(hpRivalBatalla-capturaBatalla.getAtaque()));
                    hp.setText(Integer.toString(hpMeBatalla-capturaRival.getAtaque()));
                    energyRival = false;
                    Toast.makeText(BatallaEtakemons.this, "ATAQUE VS ATAQUE", Toast.LENGTH_LONG).show();
                }else if (movRival ==2){
                    boolean defensed;

                    if (capturaBatalla.getAtaque()<capturaRival.getDefensa()) defensed = true;
                    else defensed = false;

                    if (!defensed){
                        hpRival.setText(Integer.toString(hpRivalBatalla-(capturaBatalla.getAtaque()-capturaRival.getDefensa())));
                    }
                    Toast.makeText(BatallaEtakemons.this, "ATAQUE VS DEFENSA", Toast.LENGTH_LONG).show();
                }else if (movRival == 3) {
                    hpRival.setText(Integer.toString(hpRivalBatalla-capturaBatalla.getAtaque()));
                    energyRival = true;
                    Toast.makeText(BatallaEtakemons.this, "ATAQUE VS RECARGA", Toast.LENGTH_LONG).show();
                }
                energy.setChecked(false);
                recargar.setClickable(true);
                atacar.setClickable(false);

                //Se acabó la batalla?
            }
        });

        defender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int movRival = selectionRival();

                if (movRival == 1){
                    boolean defensed;

                    if (capturaRival.getAtaque()<capturaBatalla.getDefensa()) defensed = true;
                    else defensed = false;

                    if (!defensed){
                        hp.setText(Integer.toString(hpMeBatalla-(capturaRival.getAtaque()-capturaBatalla.getDefensa())));
                    }

                    energyRival = false;
                    Toast.makeText(BatallaEtakemons.this, "DEFENSA VS ATAQUE", Toast.LENGTH_LONG).show();
                }else if (movRival ==2){
                    Toast.makeText(BatallaEtakemons.this, "DEFENSA VS DEFENSA", Toast.LENGTH_LONG).show();
                }else if (movRival == 3) {
                    energyRival = true;
                    Toast.makeText(BatallaEtakemons.this, "DEFENSA VS RECARGA", Toast.LENGTH_LONG).show();
                }
            }
        });

        recargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int movRival = selectionRival();

                if (movRival == 1){
                    hp.setText(Integer.toString(hpMeBatalla-capturaRival.getAtaque()));
                    Toast.makeText(BatallaEtakemons.this, "RECARGA VS ATAQUE", Toast.LENGTH_LONG).show();
                }else if (movRival ==2){
                    Toast.makeText(BatallaEtakemons.this, "RECARGA VS DEFENSA", Toast.LENGTH_LONG).show();
                }else if (movRival == 3) {
                    energyRival = true;
                    Toast.makeText(BatallaEtakemons.this, "RECARGA VS RECARGA", Toast.LENGTH_LONG).show();
                }
                energy.setChecked(true);
                recargar.setClickable(false);
                atacar.setClickable(true);
            }
        });

    }

    private int selectionRival(){
        int selected;

        if (energyRival){ //Si tiene energía, ATACA O DEFIENTE CON ENERGÍA (1 o 2)
            selected= (int) Math.floor(Math.random()*((2-1)+1));
        }
        else { //No tiene energía, DEFIENDE SIN ENERGÍA O RECARGA (2 o 3)
            selected = (int) Math.floor(Math.random()*((3-2)+2));
        }
        return selected;
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
