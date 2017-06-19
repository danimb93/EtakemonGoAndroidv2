package com.example.dani.etakemongo.DevelopFrontends;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dani.etakemongo.Modelo.Captura;
import com.example.dani.etakemongo.Modelo.Etakemon;
import com.example.dani.etakemongo.R;

import java.util.ArrayList;

/**
 * Created by Roberto on 19/06/2017.
 */

public class EtakedexListAdapter extends ArrayAdapter<Etakemon> {

    ArrayList<Etakemon> etakemonArrayList;
    Context context;
    int resource;

    public EtakedexListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Etakemon> objects) {
        super(context, resource, objects);
        this.context = context;
        this.etakemonArrayList = objects;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        EtakedexListAdapter.ViewHolder holder;
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_etakedex_item, null, true);
            holder = new EtakedexListAdapter.ViewHolder();
            holder.hImage11 = (ImageView) convertView.findViewById(R.id.iv_avataretakedex);
            holder.hText11 = (TextView) convertView.findViewById(R.id.tv_name_etakedex);
            holder.hText22 = (TextView) convertView.findViewById(R.id.tv_habilidad_etakedex);
            holder.hText33 = (TextView) convertView.findViewById(R.id.tipo_etakedex);
            convertView.setTag(holder);
        }

        else {
            holder = (EtakedexListAdapter.ViewHolder) convertView.getTag();
        }

        Etakemon etakemon = getItem(position);

        holder.hText11.setText(etakemon.getNombre());
        holder.hText22.setText("Habilidad:  "+etakemon.getHabilidad());
        holder.hText33.setText("Tipo:  "+String.valueOf(etakemon.getTipo()));
        Glide.with(context).load(etakemon.getImagen()).into(holder.hImage11);

        return convertView;
    }

    public static class ViewHolder{
        private ImageView hImage11;
        private TextView hText11;
        private TextView hText22;
        private TextView hText33;
    }

}
