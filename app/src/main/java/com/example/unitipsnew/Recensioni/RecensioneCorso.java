package com.example.unitipsnew.Recensioni;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.content.SharedPreferences;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recensione_corso);
        //creo una nuova finestra
        Intent intent = getIntent();
        String nome_corso = intent.getStringExtra(CustomListAdapterRecensioni.EXTRA_TEXT);

        TextView nome_corsotw = (TextView) findViewById(R.id.nome_corso);
        nome_corsotw.setText(nome_corso);
    }
}
