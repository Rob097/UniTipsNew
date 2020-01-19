package com.example.unitipsnew.Recensioni;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.unitipsnew.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomListAdapterRecensione extends ArrayAdapter<Recensione> {

    public static final String EXTRA_TEXT = "com.example.unitipsnew.Recensioni.EXTRA_TEXT";

    Context context;
    int resourses;
    List<Recensione> lista_recensioni;

    public CustomListAdapterRecensione(Context context, int resource, List<Recensione> courses){
        super(context, resource, courses);

        this.context = context;
        this.resourses = resource;
        this.lista_recensioni = courses;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(resourses, null);


        TextView titolo = view.findViewById(R.id.ad_titolo_recensione_corso);
        TextView testo = view.findViewById(R.id.ad_testo_recensione_rorso);

        final Recensione r = lista_recensioni.get(position);

        titolo.setText(r.getTitle());
        testo.setText(r.getText());

        return view;



    }
}
