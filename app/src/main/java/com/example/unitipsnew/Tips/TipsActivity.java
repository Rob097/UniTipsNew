package com.example.unitipsnew.Tips;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.unitipsnew.DatabaseHelper;
import com.example.unitipsnew.MainActivity;
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
        TextView data = findViewById(R.id.data_tip);
        TextView testo = findViewById(R.id.testo_tip);
        final TextView like = findViewById(R.id.like_tip);
        final TextView dislike = findViewById(R.id.dislike_tip);
        TextView commento = findViewById(R.id.commenti_tip);
        Button addCommento = findViewById(R.id.add_commento_button);

        Intent intent = getIntent();
        tip = db.getTip(Integer.parseInt(intent.getStringExtra(CustomListAdapterTips.EXTRA_TIP)));
        try {

            titolo.setText(tip.getTitolo());
            data.setText(tip.getData());
            testo.setText(tip.getTesto());
            like.setText(tip.getLike().size() + "");
            dislike.setText(tip.getDislike().size() + "");
            commento.setText(tip.getCommenti().size() + "");
            commenti = db.getAllCommentiByTip(tip.getId());

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
                    utenteC.setText("Anonimo");
                }
                testoC.setText(c.getTesto());
                dataC.setText(c.getData());

                linearLayout.addView(view);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        addCommento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewCommento();
            }
        });

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean checkLike = false, checkDislike = false;
                Utente u = TipsActivity.this.utente;
                for(Long l : tip.getLike()){
                    if(l == u.getMatricola())
                        checkLike = true;
                }
                for(Long l : tip.getDislike()){
                    if(l == u.getMatricola())
                        checkDislike = true;
                }

                if(!checkLike)
                    tip.getLike().add(TipsActivity.this.utente.getMatricola());
                else
                    tip.getLike().remove(TipsActivity.this.utente.getMatricola());
                if(checkDislike)
                    tip.getDislike().remove(TipsActivity.this.utente.getMatricola());

                db.updateTip(tip);
                like.setText(tip.getLike().size() + "");
                dislike.setText(tip.getDislike().size() + "");
            }
        });

        dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean checkLike = false, checkDislike = false;
                Utente u = TipsActivity.this.utente;
                for(Long l : tip.getLike()){
                    if(l == u.getMatricola())
                        checkLike = true;
                }
                for(Long l : tip.getDislike()){
                    if(l == u.getMatricola())
                        checkDislike = true;
                }

                if(!checkDislike)
                    tip.getDislike().add(TipsActivity.this.utente.getMatricola());
                else
                    tip.getDislike().remove(TipsActivity.this.utente.getMatricola());
                if(checkLike)
                    tip.getLike().remove(TipsActivity.this.utente.getMatricola());

                db.updateTip(tip);
                like.setText(tip.getLike().size() + "");
                dislike.setText(tip.getDislike().size() + "");
            }
        });
    }

    private void openNewCommento() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //Create a custom layout for the dialog box
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.add_commento, null);

        Button cancel = (Button) layout.findViewById(R.id.button_cancel_commento);
        Button create = (Button) layout.findViewById(R.id.button_add_commento);
        final EditText testo = (EditText) layout.findViewById(R.id.add_commento_text);
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

                if(testo.getText().toString() != null && !testo.getText().toString().equals("")) {
                    try {
                        final Commento c = new Commento(tip.getId(), utente.getMatricola(), testo.getText().toString());
                        db.createCommento(c);
                        alertDialog.dismiss();
                        final Intent intent1 = new Intent(view.getContext(), MainActivity.class);
                        view.getContext().startActivity(intent1);
                        final Intent intent = new Intent(view.getContext(), TipsActivity.class);
                        intent.putExtra(CustomListAdapterTips.EXTRA_TIP, "" + tip.getId());
                        view.getContext().startActivity(intent);
                        Toast.makeText(view.getContext(), "Commento aggiunto correttamente", Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(view.getContext(), "Errore nell'aggiunta del commento", Toast.LENGTH_SHORT).show();
                        Log.d("Errore add Commento", "Errore nell'aggiunta del commento");
                    }
                }
            }
        });

        alertDialog.show();
    }
}
