package com.example.unitipsnew.Recensioni;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.unitipsnew.DatabaseHelper;
import com.example.unitipsnew.MainActivity;
import com.example.unitipsnew.R;
import com.example.unitipsnew.Utente.LoginActivity;
import com.example.unitipsnew.Utente.ProfiloActivity;
import com.example.unitipsnew.Utente.Utente;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.appcompat.widget.Toolbar;

public class RecensioniActivity extends AppCompatActivity {

    ListView listview;
    SharedPreferences sp;
    ArrayList<Recensione> recens;
    DatabaseHelper db;
    Corso corso;
    Utente utente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recensioni_corso);
        db = new DatabaseHelper(getApplicationContext());
        sp = getSharedPreferences("login", MODE_PRIVATE);

        utente = db.getUser(sp.getLong("user", 0));

        FloatingActionButton btn_add_recensione_form = (FloatingActionButton) findViewById(R.id.fab_show_addrecensione_form);

        ListView mListView = (ListView) findViewById(R.id.recensioni_list);
        //creo una nuova finestra
        Intent intent = getIntent();
        try {
            corso = db.getCorso(Long.parseLong(intent.getStringExtra(CustomListAdapterRecensioni.EXTRA_CORSO)));
            recens = new ArrayList<Recensione>();
            String nome_corso = corso.getNomeCorso();
            String nome_professore = corso.getNomeProfessore();
            long numero_recensioni = corso.getNumeroRecensioni();
            TextView info_corso = this.findViewById(R.id.info_corso);

            info_corso.setText(nome_professore + "\n" + numero_recensioni + " Recensioni:\n");

            recens = db.getAllRecensionesByCorso(corso.getId());

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarmia);
            toolbar.setTitle(nome_corso);

            CustomListAdapterRecensione adapter = new CustomListAdapterRecensione(this, R.layout.list_item_recensione_corso, recens);
            mListView.setAdapter(adapter);

            btn_add_recensione_form.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openNewRecensione();
                }
            });

            for(Recensione r : recens){
                if(r.getMatricola() == utente.getMatricola()){

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            Intent i = new Intent(RecensioniActivity.this, MainActivity.class);
            startActivity(i);
        }
    }

    private void openNewRecensione() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //Create a custom layout for the dialog box
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.add_recensione, null);

        Button cancel = (Button) layout.findViewById(R.id.button_cancel);
        Button create = (Button) layout.findViewById(R.id.button);
        final EditText titolo = (EditText) layout.findViewById(R.id.add_recensione_title);
        final EditText testo = (EditText) layout.findViewById(R.id.add_recensione_text);
        builder.setView(layout);

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
                    if(!titolo.getText().toString().equals("") && !testo.getText().toString().equals("")) {
                        final Recensione r = new Recensione(0, corso.getId(), utente.getMatricola(), titolo.getText().toString(), testo.getText().toString());
                        db.createRecensione(r);
                        alertDialog.dismiss();
                        final Intent intent = new Intent(view.getContext(), MainActivity.class);
                        view.getContext().startActivity(intent);
                        Toast.makeText(view.getContext(), "Recensione aggiunta correttamente", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(view.getContext(), "Il titolo o il testo non sono corretti", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.d("Errore add Recensione", "Errore nell'aggiunta della recensione");
                }
            }
        });

        alertDialog.show();
    }
}
