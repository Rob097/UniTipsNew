package com.example.unitipsnew.Tips;

import android.content.Context;
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

public class CustomListAdapterCommento extends ArrayAdapter<Commento> {

    Context context;
    int resourses;
    List<Commento> lista_commenti;
    DatabaseHelper db;

    public CustomListAdapterCommento(Context context, int resource, List<Commento> commenti) {
        super(context, resource, commenti);

        this.context = context;
        this.resourses = resource;
        this.lista_commenti = commenti;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        db = new DatabaseHelper(getContext());

        View view = inflater.inflate(resourses, null);

        final Commento c = lista_commenti.get(position);
        Utente u = db.getUser(c.getMatricola());

        TextView utente = (TextView) view.findViewById(R.id.nome_utente_commento);
        TextView testo = (TextView) view.findViewById(R.id.testo_commento);
        TextView data = (TextView) view.findViewById(R.id.data_commento);

        if (u != null) {
            utente.setText(u.getNome() + " " + u.getCognome());
        } else {
            utente.setText("Studente sconosciuto");
        }
        testo.setText(c.getTesto());
        data.setText(c.getData());

        return view;
    }
}
