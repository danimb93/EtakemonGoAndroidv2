package com.example.dani.etakemongo.ProductionFrontends;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.example.dani.etakemongo.DevelopFrontends.Etakemon_list;
import com.example.dani.etakemongo.DevelopFrontends.InformacionUsuario;
import com.example.dani.etakemongo.Modelo.Logros;
import com.example.dani.etakemongo.Modelo.Objetos;
import com.example.dani.etakemongo.R;
import com.example.dani.etakemongo.SysTools.GitHubClient;
import com.example.dani.etakemongo.SysTools.RetrofitOwn;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ObjetosUsuario extends AppCompatActivity {

    String tag = "ObjetosUsuario";

    String emailloged, emiliologed;
    int idloged, idusuario;

    ListView listViewobjetos;
    private List<Objetos> listarecibida;
    private List<String> objetoslist;
    private List<String > objetoslist2;

    private List<Objetos> listarecibidaTodos;
    private List<String> objetoslistTodos;

    private List<Objetos> objetosTratar;
    private List<Objetos> objetosTratados;

    private int count3=0, count4=0, count5=0, count6=0, count7=0;

    private List<String> listmostrar;

    private EditText editText;
    private int textlenght;

    private ArrayList<String> array_sort = new ArrayList<>();

    private FloatingActionButton exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objetos);

        emailloged = getIntent().getExtras().getString("emailsito");
        emiliologed = emailloged;

        idusuario = getIntent().getExtras().getInt("id"); //tenemos el id del usuario logeado para hacer lo q necesitemos
        idloged = idusuario;

       // Toast.makeText(ObjetosUsuario.this, String.valueOf(idloged), Toast.LENGTH_LONG).show();

        exit = (FloatingActionButton) findViewById(R.id.fab_exit_objetos);

        try{
            doGetObjetos();
           // doGetAllObjetos();
        }
        catch (Exception ex){
            ex.getMessage();
        }

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intres = getIntent();
                setResult(RESULT_OK, intres);
                finish();
            }
        });

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

    public void doGetObjetos(){
        RetrofitOwn retro = new RetrofitOwn();
        Retrofit retrofit = retro.getObjectRetrofit();


        // Create an instance of our GitHub API interface.
        final GitHubClient objetos = retrofit.create(GitHubClient.class);

        // Create a call instance for looking up Retrofit contributors.
        Call<List<Objetos>> call = objetos.getObjetosUser(emiliologed);

        call.enqueue(new Callback<List<Objetos>>() {
            @Override
            public void onResponse(Call<List<Objetos>> call, Response<List<Objetos>> response) {

                if (response.isSuccessful()){
                    listViewobjetos = (ListView) findViewById(R.id.listObjetos);
                    listarecibida = (List<Objetos>) response.body();
                    objetoslist  = new ArrayList<String>();
                    objetoslist2 = new ArrayList<String>();

                    for (int j = 0; j < listarecibida.size(); j++) {
                        Objetos item = listarecibida.get(j);
                        objetoslist.add(String.valueOf(item.getId()));

                        if (item.getId()==3){
                            count3++;
                        }
                        else if (item.getId() == 4){
                            count4++;
                        }
                        else if (item.getId() == 5){
                            count5++;
                        }
                        else if (item.getId() == 6){
                            count6++;
                        }
                        else if (item.getId() == 7){
                            count7++;
                        }
                    }

                        if (count3>0){
                            objetoslist2.add("etakeball \n úsala para atrapar a los etakemons salvajes \n Cantidad: "+count3);
                        }
                        if (count4>0){
                            objetoslist2.add("revivir \n trae de vuelta a tu etakemon \n Cantidad: "+count4);
                        }
                        if (count5>0)
                        {
                            objetoslist2.add("pocion1 \n cura a tu etakemon \n Cantidad: "+count5);
                        }
                        if (count6>0){
                            objetoslist2.add("pocion2 \n cura media vida a tu etakemon \n Cantidad: "+count6);
                        }
                        if (count7>0){
                            objetoslist2.add("pocion3 \n cura al maximo a tu etakemon \n Cantidad: "+count7);
                        }


//                    for (int z = 0; z<objetoslist.size(); z++){
//
//                        if (objetoslist.get(z).equals(String.valueOf(3))){  //objetoslist.get(z).equals(3)
//                            count3++;
//                        }
//                        else if (objetoslist.get(z).equals(String.valueOf(4))){
//                            count4++;
//                        }
//                        else if (objetoslist.get(z).equals(String.valueOf(5))){
//                            count5++;
//                        }
//                        else if (objetoslist.get(z).equals(String.valueOf(5))){
//                            count6++;
//                        }
//                        else if (objetoslist.get(z).equals(String.valueOf(7))){
//                            count7++;
//                        }
//                    }

//                    try{
//                        RetrofitOwn retro = new RetrofitOwn();
//                        Retrofit retrofit = retro.getObjectRetrofit();
//
//
//                        // Create an instance of our GitHub API interface.
//                        final GitHubClient todosobjetos = retrofit.create(GitHubClient.class);
//
//                        // Create a call instance for looking up Retrofit contributors.
//                        Call<List<Objetos>> call2 = todosobjetos.getAllObjetos();
//
//                        call2.enqueue(new Callback<List<Objetos>>() {
//                            @Override
//                            public void onResponse(Call<List<Objetos>> call, Response<List<Objetos>> response) {
//
//                                if (response.isSuccessful()){
//                                    listViewobjetos = (ListView) findViewById(R.id.listObjetos);
//                                    listarecibidaTodos = (List<Objetos>) response.body();
//                                    objetoslistTodos  = new ArrayList<String>();
//                                    objetosTratar = new ArrayList<Objetos>();
//
//                                    listmostrar = new ArrayList<String>();
//
//                                    for (int j = 0; j < listarecibidaTodos.size(); j++) {
//                                        //  Objetos item = listarecibidaTodos.get(j);
//                                        // objetoslistTodos.add(String.valueOf(item.getId())+item.getNombre()+item.getDescripcion());
////                                        Objetos jeto = new Objetos();
////                                        jeto.setDescripcion(listarecibidaTodos.get(j).getDescripcion());
////                                        jeto.setId(listarecibidaTodos.get(j).getId());
////                                        jeto.setNombre(listarecibidaTodos.get(j).getNombre());
////
////                                        objetosTratar.add(jeto);
//
//
//                                        if ((count3>0) && listarecibidaTodos.get(j).getId() == 3){
//                                            listmostrar.add(listarecibidaTodos.get(j).getNombre()+"\n"+listarecibidaTodos.get(j).getDescripcion()+" Cantidad: "+count3);
//                                        }
//                                        if ((count4>0) && listarecibidaTodos.get(j).getId() == 4){
//                                            listmostrar.add(listarecibidaTodos.get(j).getNombre()+"\n"+listarecibidaTodos.get(j).getDescripcion()+" Cantidad: "+count4);
//                                        }
//                                        if ((count5>0) && listarecibidaTodos.get(j).getId() == 5){
//                                            listmostrar.add(listarecibidaTodos.get(j).getNombre()+"\n"+listarecibidaTodos.get(j).getDescripcion()+" Cantidad: "+count5);
//                                        }
//                                        if ((count6>0) && listarecibidaTodos.get(j).getId() == 6){
//                                            listmostrar.add(listarecibidaTodos.get(j).getNombre()+"\n"+listarecibidaTodos.get(j).getDescripcion()+" Cantidad: "+count6);
//                                        }
//                                        if ((count7>0) && listarecibidaTodos.get(j).getId() == 7){
//                                            listmostrar.add(listarecibidaTodos.get(j).getNombre()+"\n"+listarecibidaTodos.get(j).getDescripcion()+" Cantidad: "+count7);
//                                        }
//
//
//                                    }
//
//                                }
//                                else {
//                                    Toast.makeText(ObjetosUsuario.this, "response unsuccessful All objetos", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<List<Objetos>> call, Throwable t) {
//                                Toast.makeText(ObjetosUsuario.this, "No hay conexión", Toast.LENGTH_SHORT).show();
//                                Log.d(tag, "ERROR en el recogido todos los objetos");
//                            }
//                        });
//                    }
//                    catch (Exception ex){
//                        ex.getMessage();
//                    }


                    //Tratar objetos para mostrar una lista bonika

                    //tratamiento de objetos para lista presentable arriba

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>
                            (ObjetosUsuario.this, android.R.layout.simple_list_item_1, objetoslist2);
                    listViewobjetos.setAdapter(arrayAdapter);
                }
                else {
                    Toast.makeText(ObjetosUsuario.this, "response unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Objetos>> call, Throwable t) {
                Toast.makeText(ObjetosUsuario.this, "No hay conexión", Toast.LENGTH_SHORT).show();
                Log.d(tag, "ERROR en el recogido de logros del usuario");
            }
        });



    }


//    public List<Objetos> doGetAllObjetos(){
//        RetrofitOwn retro = new RetrofitOwn();
//        Retrofit retrofit = retro.getObjectRetrofit();
//
//
//        // Create an instance of our GitHub API interface.
//        final GitHubClient todosobjetos = retrofit.create(GitHubClient.class);
//
//        // Create a call instance for looking up Retrofit contributors.
//        Call<List<Objetos>> call = todosobjetos.getAllObjetos();
//
//        call.enqueue(new Callback<List<Objetos>>() {
//            @Override
//            public void onResponse(Call<List<Objetos>> call, Response<List<Objetos>> response) {
//
//                if (response.isSuccessful()){
//                    listViewobjetos = (ListView) findViewById(R.id.listObjetos);
//                    listarecibidaTodos = (List<Objetos>) response.body();
//                    objetoslistTodos  = new ArrayList<String>();
//                    objetosTratar = new ArrayList<Objetos>();
//
//                    for (int j = 0; j < listarecibidaTodos.size(); j++) {
//                      //  Objetos item = listarecibidaTodos.get(j);
//                       // objetoslistTodos.add(String.valueOf(item.getId())+item.getNombre()+item.getDescripcion());
//                        Objetos jeto = new Objetos();
//                        jeto.setDescripcion(listarecibidaTodos.get(j).getDescripcion());
//                        jeto.setId(listarecibidaTodos.get(j).getId());
//                        jeto.setNombre(listarecibidaTodos.get(j).getNombre());
//
//                        objetosTratar.add(jeto);
//                    }
//
////                    for (int x = 0; x<objetosTratar.size(); x++){
////                        System.out.println(objetosTratar.get(x).getNombre());
////                        System.out.println(objetosTratar.get(x).getDescripcion());
////                        System.out.println(objetosTratar.get(x).getId());
////                    }
//
//                    //Tratar objetos para mostrar una lista bonika
//
//                    //System.out.println("TAMAÑO: "+objetosTratar.size());
//
//
//                    //tratamiento de objetos para lista presentable arriba
//
////                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>
////                            (ObjetosUsuario.this, android.R.layout.simple_list_item_1, objetoslistTodos);
////                    listViewobjetos.setAdapter(arrayAdapter);
//                }
//                else {
//                    Toast.makeText(ObjetosUsuario.this, "response unsuccessful", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Objetos>> call, Throwable t) {
//                Toast.makeText(ObjetosUsuario.this, "No hay conexión", Toast.LENGTH_SHORT).show();
//                Log.d(tag, "ERROR en el recogido de logros del usuario");
//            }
//        });
//
//        return objetosTratar;
//    }


}
