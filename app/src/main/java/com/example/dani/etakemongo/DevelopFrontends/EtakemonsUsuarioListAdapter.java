package com.example.dani.etakemongo.DevelopFrontends;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dani.etakemongo.Modelo.Captura;
import com.example.dani.etakemongo.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivanm on 13/06/2017.
 */

public class EtakemonsUsuarioListAdapter extends ArrayAdapter<Captura> {

    ArrayList<Captura> capturaArrayList;
    Context context;
    int resource;



    public EtakemonsUsuarioListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Captura> objects) {
        super(context, resource, objects);
        this.context = context;
        this.capturaArrayList = objects;
        this.resource = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder;
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item_etakemon, null, true);
            holder = new ViewHolder();
            holder.hImage1 = (ImageView) convertView.findViewById(R.id.iv_avatar);
            holder.hText1 = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        }

        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Captura captura = getItem(position);

        holder.hText1.setText(captura.getNombreetakemon());
        Glide.with(context).load(captura.getImagen()).into(holder.hImage1);

        return convertView;
    }

    public static class ViewHolder{
        private ImageView hImage1;
        private TextView hText1;
    }



}
