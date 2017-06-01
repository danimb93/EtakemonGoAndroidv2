package com.example.dani.etakemongo.ProductionFrontends;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dani.etakemongo.Modelo.Usuario;
import com.example.dani.etakemongo.R;
import com.example.dani.etakemongo.SysTools.EnviarTicket;
import com.example.dani.etakemongo.SysTools.GitHubClient;
import com.example.dani.etakemongo.SysTools.RetrofitOwn;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Login extends AppCompatActivity {

    //Variables necesarias de los objetos del layout
    String tag = "Login"; // tag que indica el ciclo de vida de la app
    private EditText email, password;
    private Button login;
    private Usuario loged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d(tag, "Event onCreate()");

        //Traemos a las variables los objetos del layout
        email = (EditText) findViewById(R.id.etEmail);
        password = (EditText) findViewById(R.id.etPassword);
        login = (Button) findViewById(R.id.btnLogin);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        email = (EditText) findViewById(R.id.etEmail);
        Bundle result = data.getExtras();

        //operar segun el resultCode recibido por las otras actividades

       if (requestCode==200 && resultCode == 1600){
            String emailToShow = result.getString("registrado");
            email.setText(emailToShow);
        }

        else if (requestCode==300 && resultCode == 1700){                   // no se por que no lo hace. REVISAR
            Log.d(tag,"ENTRA EN ACTIV RES");
            String emailMostrar = (String) result.get("enviado");
            email.setText(emailMostrar);
        }

        else if (requestCode == 500 ){
           if (resultCode == RESULT_OK){
               Log.d(tag, "E-mail enviado ok");
           }
       }
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

    public void doLogin(final View v){

        RetrofitOwn retrofitOwn = new RetrofitOwn();
        Retrofit retrofit = retrofitOwn.getObjectRetrofit();

        //Creamos una instancia de retrofit
        GitHubClient login = retrofit.create(GitHubClient.class);
        Usuario usuario = new Usuario(email.getText().toString() ,password.getText().toString());

        //Hacemos la llamada http
        Call<Usuario> call = login.login(usuario);

        //Recibimos la llamada
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                //Si OK, procedemos a entrar en el mapa
                if (response.isSuccessful()){
                    Usuario loged = (Usuario) response.body();
                    goToMapsActivity(v);
                    Log.d(tag, "Logueado correctamente");
                }
                else{
                    //Si los datos son erroneos y el user no esta
                    Toast.makeText(Login.this, "Los datos introducidos son incorrectos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            //Si no hay conexión
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(Login.this, "No hay conexión", Toast.LENGTH_SHORT).show();
                Log.d(tag, "ERROR en el loguin");
            }
        });
    }

    public void abrirRegistrar (View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivityForResult(intent, 200);
    }
    public void abrirRecuperar (View view) {
        Intent intent = new Intent(this, RecuperarDatos.class);
        startActivityForResult(intent, 300);
    }

    public void goToMapsActivity(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("loged", (Serializable)loged); //Pasar al maps el usuario entero que hemos recibido en el registro
        startActivityForResult(intent, 100);    //ponemos el codigo 100 para monitorizar esta actividad y sus futuros resultados
    }

    public void goToEnviarTicket(View view){
        Intent intent = new Intent(this, EnviarTicket.class);
        startActivityForResult(intent, 500);
    }

}
