package com.example.unitipsnew.Recensioni;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.unitipsnew.DatabaseHelper;
import com.example.unitipsnew.R;
import com.example.unitipsnew.Utente.Utente;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomListAdapterRecensione extends ArrayAdapter<Recensione> {

    Context context;
    int resourses;
    List<Recensione> lista_recensioni;
    DatabaseHelper db;

    public CustomListAdapterRecensione(Context context, int resource, List<Recensione> courses) {
        super(context, resource, courses);

        this.context = context;
        this.resourses = resource;
        this.lista_recensioni = courses;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        db = new DatabaseHelper(getContext());

        View view = inflater.inflate(resourses, null);

        TextView titolo = view.findViewById(R.id.titolo_recensione);
        TextView testo = view.findViewById(R.id.testo_recensione);
        TextView data = view.findViewById(R.id.data_recensione);
        TextView utente = view.findViewById(R.id.utente_recensione);

        final Recensione r = lista_recensioni.get(position);
        Utente u = db.getUser(r.getMatricola());
        titolo.setText(r.getTitle());
        testo.setText(r.getText());
        data.setText(r.getDate());
        if(u != null) {
            utente.setText(u.getNome() + " " + u.getCognome());
        }else{
            utente.setText("Studente sconosciuto");
        }
        return view;


    }
}
