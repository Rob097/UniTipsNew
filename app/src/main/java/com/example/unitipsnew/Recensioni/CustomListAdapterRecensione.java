package com.example.unitipsnew.Recensioni;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.unitipsnew.DatabaseHelper;
import com.example.unitipsnew.MainActivity;
import com.example.unitipsnew.R;
import com.example.unitipsnew.Utente.Utente;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.content.Context.MODE_PRIVATE;

/* Classe per la liusta di recensioni di un corso */
public class CustomListAdapterRecensione extends ArrayAdapter<Recensione> {

    Context context;
    int resourses;
    List<Recensione> lista_recensioni;
    DatabaseHelper db;
    SharedPreferences sp;
    Utente u;

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
        sp = this.getContext().getSharedPreferences("login", MODE_PRIVATE);

        u = db.getUser(sp.getLong("user", 0));

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
        if (u != null) {
            utente.setText(u.getNome() + " " + u.getCognome());
        } else {
            utente.setText("Anonimo");
        }

        if (u != null && r.getMatricola() == u.getMatricola()) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openAlterRecensione(r);
                }
            });
        }

        return view;
    }

    private void openAlterRecensione(final Recensione r) {
        if (r.getMatricola() == u.getMatricola()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());

            //Create a custom layout for the dialog box
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.add_recensione, null);

            Button cancel = layout.findViewById(R.id.button_cancel);
            Button create = layout.findViewById(R.id.button);
            final EditText titolo = layout.findViewById(R.id.add_recensione_title);
            final EditText testo = layout.findViewById(R.id.add_recensione_text);
            builder.setView(layout);
            TextView h1 = layout.findViewById(R.id.txt_title_window);

            h1.setText("Aggiorna Recensione");
            create.setText("Aggiorna");
            titolo.setText(r.getTitle());
            testo.setText(r.getText());

            final AlertDialog alertDialog = builder.create();

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });

            create.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        if (!titolo.getText().toString().equals("") && !testo.getText().toString().equals("")) {
                            final Recensione r1 = new Recensione(r.getId_recensione(), r.getId_corso(), r.getMatricola(), titolo.getText().toString(), testo.getText().toString(), db.getDateTime());
                            db.updateRecensione(r1);
                            alertDialog.dismiss();
                            final Intent intent = new Intent(view.getContext(), MainActivity.class);
                            view.getContext().startActivity(intent);
                            Toast.makeText(view.getContext(), "Recensione aggiornata correttamente", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(view.getContext(), "Il titolo o il testo non sono corretti", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.d("Errore alter Recensione", "Errore nell'aggiornamento della recensione");
                    }
                }
            });

            alertDialog.show();
        }
    }
}
