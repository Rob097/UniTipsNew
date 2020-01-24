package com.example.unitipsnew.Eventi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.unitipsnew.DatabaseHelper;
import com.example.unitipsnew.MainActivity;
import com.example.unitipsnew.R;
import com.example.unitipsnew.Utente.Utente;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EventiActivity extends AppCompatActivity {

    DatabaseHelper db;
    SharedPreferences sp;
    ArrayList<Long> longs = new ArrayList<>();
    Evento evento = new Evento();
    Utente utente = new Utente();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento);
        db = new DatabaseHelper(getApplicationContext());
        sp = getSharedPreferences("login", MODE_PRIVATE);

        utente = db.getUser(sp.getLong("user", 0));

        TextView titolo_evento = findViewById(R.id.titolo_evento);
        ImageView immagine_evento = findViewById(R.id.immagine_evento);
        TextView luogo_evento = findViewById(R.id.luogo_evento);
        TextView data_evento = findViewById(R.id.data_evento);
        final TextView interessati_evento = findViewById(R.id.interessati_evento);
        TextView descrizione_evento = findViewById(R.id.descrizione_evento);
        final Button interessato = findViewById(R.id.interessato);

        Intent intent = getIntent();
        evento = db.getEvento(Integer.parseInt(intent.getStringExtra(CustomListAdapterEventi.EXTRA_EVENTO)));
        try {
            titolo_evento.setText(evento.getTitolo());
            immagine_evento.setImageDrawable(getResources().getDrawable(evento.getImmagine()));
            luogo_evento.setText(evento.getLuogo());
            data_evento.setText(evento.getData());
            interessati_evento.setText(evento.getInteressati().size() + " interessati");
            descrizione_evento.setText(evento.getDescrizione());
            longs = evento.getInteressati();

            interessato.setPadding(10, 0, 10, 0);
            if(isInteressato()) {
                interessato.setText("NON SONO PIU INTERESSATO");
                interessato.setBackgroundColor(getResources().getColor(R.color.red));
                interessato.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable( R.drawable.ic_thumb_down_white_24dp ), null, null, null);
            }else{
                interessato.setText("SONO INTERESSATO");
                interessato.setBackgroundColor(getResources().getColor(R.color.blu));
                interessato.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable( R.drawable.ic_thumb_up_white_24dp ), null, null, null);
            }

            interessato.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(isInteressato()) {
                        longs.remove(utente.getMatricola());
                        interessato.setText("SONO INTERESSATO");
                        interessato.setBackgroundColor(getResources().getColor(R.color.blu));
                        interessato.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_thumb_up_white_24dp), null, null, null);
                    }else{
                        longs.add(utente.getMatricola());
                        interessato.setText("NON SONO PIU INTERESSATO");
                        interessato.setBackgroundColor(getResources().getColor(R.color.red));
                        interessato.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_thumb_down_white_24dp), null, null, null);
                    }

                    evento.setInteressati(longs);
                    db.updateEvento(evento);
                    interessati_evento.setText(evento.getInteressati().size() + " interessati");

                }
            });

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public boolean isInteressato(){
        for(long i : evento.getInteressati()) {
            if (i == utente.getMatricola()) {
                return true;
            }
        }
        return false;
    }
}
