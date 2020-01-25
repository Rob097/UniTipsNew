package com.example.unitipsnew.Tips;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.unitipsnew.DatabaseHelper;
import com.example.unitipsnew.R;
import com.example.unitipsnew.Utente.Utente;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class TipsActivity extends AppCompatActivity {

    ListView listview;
    SharedPreferences sp;
    ArrayList<Commento> commenti;
    DatabaseHelper db;
    Tip tip;
    Utente utente;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);
        db = new DatabaseHelper(getApplicationContext());
        sp = getSharedPreferences("login", MODE_PRIVATE);

        utente = db.getUser(sp.getLong("user", 0));

        TextView titolo = findViewById(R.id.titolo_tip);
        TextView utente = findViewById(R.id.utente_tip);
        TextView data = findViewById(R.id.data_tip);
        TextView testo = findViewById(R.id.testo_tip);
        TextView like = findViewById(R.id.like_tip);
        TextView dislike = findViewById(R.id.dislike_tip);
        TextView commento = findViewById(R.id.commenti_tip);

        Intent intent = getIntent();
        tip = db.getTip(Integer.parseInt(intent.getStringExtra(CustomListAdapterTips.EXTRA_TIP)));
        try {

            titolo.setText(tip.getTitolo());
            utente.setText(db.getUser(tip.getMatricola()).getNome() + " " + db.getUser(tip.getMatricola()).getCognome());
            data.setText(tip.getData());
            testo.setText(tip.getTesto());
            like.setText(tip.getLike() + "");
            dislike.setText(tip.getDislike() + "");
            commento.setText(tip.getCommenti().size() + "");
            commenti = tip.getCommenti();

            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.commenti_layout);
            LayoutInflater inflater = LayoutInflater.from(this);
            Utente u = new Utente();
            for (Commento c : commenti) {
                View view  = inflater.inflate(R.layout.list_item_commento, linearLayout, false);

                u = db.getUser(c.getMatricola());
                TextView utenteC = (TextView) view.findViewById(R.id.nome_utente_commento);
                TextView testoC = (TextView) view.findViewById(R.id.testo_commento);
                TextView dataC = (TextView) view.findViewById(R.id.data_commento);

                if (u != null) {
                    utenteC.setText(u.getNome() + " " + u.getCognome());
                } else {
                    utenteC.setText("Studente sconosciuto");
                }
                testoC.setText(c.getTesto());
                dataC.setText(c.getData());

                linearLayout.addView(view);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void openNewRecensione() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //Create a custom layout for the dialog box
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.add_recensione_form, null);

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

                /*try {
                    final Recensione r = new Recensione(0, corso.getId(), utente.getMatricola(), titolo.getText().toString(), testo.getText().toString());
                    db.createRecensione(r);
                    alertDialog.dismiss();
                    final Intent intent = new Intent(view.getContext(), MainActivity.class);
                    view.getContext().startActivity(intent);
                    Toast.makeText(view.getContext(), "Recensione aggiunta correttamente", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.d("Errore add Recensione", "Errore nell'aggiunta della recensione");
                }*/
            }
        });

        alertDialog.show();
    }
}
