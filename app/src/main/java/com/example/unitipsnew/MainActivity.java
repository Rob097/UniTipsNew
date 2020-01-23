package com.example.unitipsnew;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.unitipsnew.Recensioni.Corso;
import com.example.unitipsnew.Recensioni.Recensione;
import com.example.unitipsnew.Utente.LoginActivity;
import com.example.unitipsnew.Utente.ProfiloActivity;
import com.example.unitipsnew.Utente.Utente;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.widget.Toast;

import com.example.unitipsnew.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    SharedPreferences sp;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new DatabaseHelper(getApplicationContext());
        sp = getSharedPreferences("login", MODE_PRIVATE);

        /*List<Recensione> recensioni = new ArrayList<>();
        recensioni.add(new Recensione(0, 1, 0, "Assurdo", "Consiglio di studiare"));
        recensioni.add(new Recensione(0, 1, 185035, "Assurdo", "Consiglio di studiare"));
        recensioni.add(new Recensione(0, 2, 185035, "Assurdo", "Consiglio di studiare"));
        recensioni.add(new Recensione(0, 2, 185035, "Assurdo", "Consiglio di studiare"));

        for(Recensione r : db.getAllRecensionesByCorso(1)) {
            db.deleteRecensione(r.getId_recensione());
        }*/

        Utente utente = db.getUser(sp.getLong("user", 0));

        Toast.makeText(this, "Bentornato " + utente.getNome(), Toast.LENGTH_SHORT).show();

        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        toolbar = (Toolbar) findViewById(R.id.toolbarmia);
        toolbar.inflateMenu(R.menu.menu);

        //Azioni da eseguire al click del menu
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                //Logout
                if(item.getItemId()==R.id.logout1){
                    //Setto le prreferenze in modo da sloggare l'utente
                    sp = MainActivity.this.getSharedPreferences("login", MODE_PRIVATE);
                    sp.edit().putBoolean("logged", false).apply();
                    sp.edit().putLong("user", 0).apply();

                    sp = MainActivity.this.getSharedPreferences("checkbox", MODE_PRIVATE);
                    sp.edit().putBoolean("remember", false).apply();

                    //Avviso di avvenuto logout
                    Toast.makeText(MainActivity.this, "Log Out Effettuato", Toast.LENGTH_SHORT).show();

                    //Lo rimando alla pagina di login
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                }
                else if(item.getItemId()==R.id.account_profile){
                    //Lo rimando alla pagina del profilo utente
                    Intent i = new Intent(MainActivity.this, ProfiloActivity.class);
                    startActivity(i);
                }
                else if(item.getItemId()==R.id.refresh){
                    //Lo rimando alla pagina del profilo utente
                    Toast.makeText(MainActivity.this, "Funzione Aggiorna da implementare", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }
}