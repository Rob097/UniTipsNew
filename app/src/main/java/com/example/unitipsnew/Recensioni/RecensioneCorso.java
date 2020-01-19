package com.example.unitipsnew.Recensioni;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.unitipsnew.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import androidx.fragment.app.Fragment;

public class RecensioneCorso extends AppCompatActivity {

    ListView listview;
    SharedPreferences sp;
    ArrayList<Recensione> recens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recensione_corso);

        ListView mListView = (ListView)findViewById(R.id.recensioni_list);
        //creo una nuova finestra
        Intent intent = getIntent();

        recens = new ArrayList<Recensione>();
        String nome_corso = intent.getStringExtra(CustomListAdapterRecensioni.EXTRA_TEXT);
        String recensioni = intent.getStringExtra(CustomListAdapterRecensioni.EXTRA_RECENSIONI);

        String[] s = recensioni.split(";");
        for(int i = 0; i<s.length;i++){
            String[] p = s[i].split(":");
            recens.add(new Recensione(p[0],p[1],"october"));
            Log.d("Activity","---------------------------------------"+recens.get(i).toString());

        }


        //TextView nome_corsotw = (TextView) findViewById(R.id.nome_corso);
        //nome_corsotw.setText(nome_corso);

        CustomListAdapterRecensione adapter = new CustomListAdapterRecensione(this,R.layout.adapter_view_recensione, recens);
        mListView.setAdapter(adapter);
    }
}
