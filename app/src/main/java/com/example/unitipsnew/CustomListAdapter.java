package com.example.unitipsnew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomListAdapter extends ArrayAdapter<Course> {

    Context context;
    int resourses;
    List<Course> courses;

    public CustomListAdapter(Context context, int resource, List<Course> courses){
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

        TextView nome_corso = view.findViewById(R.id.nome_corso);
        TextView nome_professore = view.findViewById(R.id.nome_professore);
        TextView numero_recensioni = view.findViewById(R.id.numero_recensioni);

        Course course = courses.get(position);

        nome_corso.setText(course.getNomeCorso());
        nome_professore.setText(course.getNomeProfessore());
        numero_recensioni.setText("" + course.getNumeroRecensioni() + " Recensioni");

        return view;
    }
}
