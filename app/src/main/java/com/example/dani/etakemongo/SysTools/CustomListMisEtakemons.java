package com.example.dani.etakemongo.SysTools;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dani.etakemongo.R;

import java.util.ArrayList;

public class CustomListMisEtakemons extends ArrayAdapter<String>{

    private final Activity context2;
    private final ArrayList<String> web2;
    private final Integer[] imageId2;
    public CustomListMisEtakemons(Activity context,
                                  ArrayList<String> web2, Integer[] imageId) {
        super(context, R.layout.list_single, web2);
        this.context2 = context;
        this.web2 = web2;
        this.imageId2 = imageId;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context2.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single2, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt2);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img2);
        txtTitle.setText(web2.get(position));

        imageView.setImageResource(imageId2[position]);
        return rowView;
    }
}