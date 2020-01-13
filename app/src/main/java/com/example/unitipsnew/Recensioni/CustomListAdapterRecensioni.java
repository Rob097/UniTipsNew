package com.example.unitipsnew.Recensioni;

import android.content.Context;
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

public class CustomListAdapterRecensioni extends ArrayAdapter<Corso> {

    Context context;
    int resourses;
    List<Corso> courses;

    public CustomListAdapterRecensioni(Context context, int resource, List<Corso> courses){
        super(context, resource, courses);

        this.context = context;
        this.resourses = resource;
        this.courses = courses;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(resourses, null);

        if (position % 2 == 0) {
            view.setBackgroundColor(Color.rgb(232, 232, 232));
        } else {
            view.setBackgroundColor(Color.WHITE);
        }

        TextView nome_corso = view.findViewById(R.id.nome_corso);
        TextView nome_professore = view.findViewById(R.id.nome_professore);
        TextView numero_recensioni = view.findViewById(R.id.numero_recensioni);

        Corso course = courses.get(position);

        nome_corso.setText(course.getNomeCorso());
        nome_professore.setText(course.getNomeProfessore());
        numero_recensioni.setText("" + course.getNumeroRecensioni() + " Recensioni");

        return view;
    }
}
