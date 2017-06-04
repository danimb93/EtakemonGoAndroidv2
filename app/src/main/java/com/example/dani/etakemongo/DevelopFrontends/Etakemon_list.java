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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;
import java.util.ArrayList;

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

    private EditText editText;
    private int textlenght = 0;
    private ArrayList<String> array_sort = new ArrayList<>();

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

        editText = (EditText) findViewById(R.id.etSearch_etlist);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textlenght = editText.getText().length();
                array_sort.clear();

                for (int i = 0; i < web.length; i++){
                    if (textlenght < web[i].length()){
                        if (editText.getText().toString().equalsIgnoreCase((String) web[i].subSequence(0, textlenght))){
                            array_sort.add(web[i]);
                        }
                    }

                }

                list.setAdapter((new ArrayAdapter<String>(Etakemon_list.this, android.R.layout.simple_list_item_1, array_sort)));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}