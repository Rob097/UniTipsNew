package com.example.unitipsnew.Recensioni;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.unitipsnew.DatabaseHelper;
import com.example.unitipsnew.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* Classe per la lista dei corsi nel tab 1 */
public class CustomListAdapterRecensioni extends ArrayAdapter<Corso> {

    public static final String EXTRA_CORSO = "idCorso";

    Context context;
    int resourses;
    List<Corso> courses;
    DatabaseHelper db;

    public CustomListAdapterRecensioni(Context context, int resource, List<Corso> courses) {
        super(context, resource, courses);
        this.context = context;
        this.resourses = resource;
        this.courses = courses;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);

        final View view = inflater.inflate(resourses, null);

        db = new DatabaseHelper(view.getContext());

        if (position % 2 == 0) {
            view.setBackgroundColor(Color.rgb(232, 232, 232));
        } else {
            view.setBackgroundColor(Color.WHITE);
        }

        TextView nome_corso = view.findViewById(R.id.nome_corso);
        final TextView nome_professore = view.findViewById(R.id.nome_professore);
        final TextView numero_recensioni = view.findViewById(R.id.numero_recensioni);

        final Corso corso = courses.get(position);

        final Intent intent = new Intent(context, RecensioniActivity.class);
        nome_corso.setText(corso.getNomeCorso());
        nome_professore.setText(corso.getNomeProfessore());
        numero_recensioni.setText(corso.getNumeroRecensioni() + " Recensioni");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(EXTRA_CORSO, "" + corso.getId());
                context.startActivity(intent);
            }
        });

        return view;
    }

}
