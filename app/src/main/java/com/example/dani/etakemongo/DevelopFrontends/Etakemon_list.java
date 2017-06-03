package com.example.dani.etakemongo.DevelopFrontends;

//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//
//import com.example.dani.etakemongo.R;
//
//public class Etakemon_list extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_etakemon_list);
//    }
//}


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;

import com.example.dani.etakemongo.R;

public class Etakemon_list extends Activity {
    ListView list;
    String[] web = {
            "Google Plus \n Este es el kasalikoh",
            "Twitter",
            "Windows",
            "Bing",
            "Itunes",
            "Wordpress",
            "Drupal"
    } ;
    Integer[] imageId = {
            R.drawable.alakasals,
            R.drawable.danisloth,
            R.drawable.davidos,
            R.drawable.livanny,
            R.drawable.robat,
            R.drawable.tonix,
            R.drawable.wooperal

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etakemon_list);

        CustomList adapter = new
                CustomList(Etakemon_list.this, web, imageId);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(Etakemon_list.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();

            }
        });

    }

}