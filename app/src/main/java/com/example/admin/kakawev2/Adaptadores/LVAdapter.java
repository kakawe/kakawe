package com.example.admin.kakawev2.Adaptadores;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.admin.kakawev2.Entidades.Comunidad;
import com.example.admin.kakawev2.R;

import java.util.ArrayList;

/**
 * Created by admin on 08/03/2018.
 */

public class LVAdapter implements ListAdapter {

    ArrayList<String> listaComunidades;
    Context context;

    public LVAdapter(ArrayList<String> listaComunidades, Context context) {
        this.listaComunidades = listaComunidades;
        this.context = context;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return listaComunidades.size();
    }

    @Override
    public Object getItem(int position) {
        return listaComunidades.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View vista = inflater.inflate(R.layout.vista_comunidad,parent,false);
        TextView tv_vistaCom_nomCom = (TextView)vista.findViewById(R.id.tv_vistaCom_nomCom);
        tv_vistaCom_nomCom.setText(listaComunidades.get(position).toString());

        return vista;
    }

    @Override
    public int getItemViewType(int position) {
        return listaComunidades.size();
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return listaComunidades.isEmpty();
    }
}
