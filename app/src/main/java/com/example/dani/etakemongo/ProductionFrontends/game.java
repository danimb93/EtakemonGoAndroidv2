package com.example.dani.etakemongo.ProductionFrontends;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dani.etakemongo.Modelo.Captura;
import com.example.dani.etakemongo.Modelo.Usuario;
import com.example.dani.etakemongo.R;
import com.example.dani.etakemongo.SysTools.GitHubClient;
import com.example.dani.etakemongo.SysTools.ImageLoadTask;
import com.example.dani.etakemongo.SysTools.RetrofitOwn;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class game extends AppCompatActivity {

    private Captura capturarecibed;
    private Captura capturaToUse;

    private int idrecibed;
    private int idToUse;

    private Button buttoncapturar;

    TextView eetakemon, habilidad;
    ImageView eetakemonImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        eetakemon = (TextView) findViewById(R.id.eetakemon);
        habilidad = (TextView) findViewById(R.id.habilidad);
        eetakemonImg = (ImageView) findViewById(R.id.eetakemonImg);

        capturarecibed = (Captura) getIntent().getSerializableExtra("captura");
        capturaToUse = capturarecibed;

        idrecibed = getIntent().getExtras().getInt("userLoger");
        idToUse = idrecibed;

        new ImageLoadTask(capturaToUse.getImagen(), eetakemonImg).execute();
        eetakemon.setText(eetakemon.getText()+" "+capturaToUse.getNombreetakemon());
        habilidad.setText(habilidad.getText()+" "+capturaToUse.getHabilidadetakemon());

        buttoncapturar = (Button) findViewById(R.id.button2);
        buttoncapturar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    doCapture();
                    Toast.makeText(game.this, "¡Capturado!", Toast.LENGTH_SHORT).show();
                    Intent intent = getIntent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });


//        recibed = getIntent().getBundleExtra("captura");

//

    }

    public void doCapture(){

        RetrofitOwn retrofitOwn = new RetrofitOwn();
        Retrofit retrofit = retrofitOwn.getObjectRetrofit();


        //Creamos una instancia de retrofit
        GitHubClient capturar = retrofit.create(GitHubClient.class);

        //Hacemos la llamada http
        Call<Captura> call = capturar.setCapturaToUsuario(idToUse, capturaToUse);

        call.enqueue(new Callback<Captura>() {

            @Override
            public void onResponse(Call<Captura> call, Response<Captura> response) {

                if (response.isSuccessful()){
                    Toast.makeText(game.this, "Capturado!", Toast.LENGTH_SHORT).show();
                    Intent intent = getIntent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Captura> call, Throwable t) {

            }
        });
    }
}
