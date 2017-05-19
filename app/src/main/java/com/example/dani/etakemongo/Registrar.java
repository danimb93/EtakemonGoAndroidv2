package com.example.dani.etakemongo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Registrar extends AppCompatActivity {

    private EditText nombre,nick,password,email,emailR;
    private Button registrar;
    private TextView datos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        nombre=(EditText)findViewById(R.id.etNombre);
        nick=(EditText)findViewById(R.id.etNick);
        password=(EditText)findViewById(R.id.etPassword);
        email=(EditText)findViewById(R.id.etEmail);
        emailR=(EditText)findViewById(R.id.etEmailR);
        registrar=(Button)findViewById(R.id.btnRegistrar);
        datos=(TextView)findViewById(R.id.tvRespuesta);


        registrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){

                String snombre= nombre.getText().toString();
                String snick = nick.getText().toString();
                String spassword=password.getText().toString();
                String semail= email.getText().toString();
                String semailR= emailR.getText().toString();


                datos.setText(snombre + snick + spassword + semail);
            }
        })
        ;}


}







