package com.example.unitipsnew;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.unitipsnew.Recensioni.Corso;
import com.example.unitipsnew.Utente.LoginActivity;
import com.example.unitipsnew.Utente.ProfiloActivity;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.widget.Toast;

import com.example.unitipsnew.ui.main.SectionsPagerAdapter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    SharedPreferences sp;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new DatabaseHelper(getApplicationContext());
        sp = getSharedPreferences("login", MODE_PRIVATE);

        /*  CODICE PER POPOLARE LA TABELLA DEI CORSI */

        if(db.getAllCorsi() == null || db.getAllCorsi().isEmpty() || db.getAllCorsi().get(0).getId() >= 8) {
            ArrayList<Corso> corsi = new ArrayList<>();
            corsi.add(new Corso("Analisi Matematica 1", "Anneliese Defranceschi" ,0 , 1));
            corsi.add(new Corso("Analisi Matematica 2", "Anneliese Defranceschi" ,0 , 2));
            corsi.add(new Corso("Ingegneria del software 1", "Paolo Giorgini" ,0 , 3));
            corsi.add(new Corso("Ingegneria del software 2", "Adolfo Villafiorita" ,0 , 4));
            corsi.add(new Corso("Fisica", "Roberto Battiston" ,0 , 5));
            corsi.add(new Corso("Economia ed innovazione d'impresa", "Mariasole Bannò" ,0 , 6));
            corsi.add(new Corso("Geometria e Algebra Lineare", "Ochetta" ,0 , 7));

            for (Corso c : corsi) {
                db.createCorso(c);
            }
        }
        if(!sp.getBoolean("logged", true) || sp.getLong("user", 01) == 0){
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
        }else {


            setContentView(R.layout.activity_main);
            SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
            ViewPager viewPager = findViewById(R.id.view_pager);
            viewPager.setAdapter(sectionsPagerAdapter);
            TabLayout tabs = findViewById(R.id.tabs);
            tabs.setupWithViewPager(viewPager);

            toolbar = findViewById(R.id.toolbarmia);
            toolbar.inflateMenu(R.menu.menu);

            //Azioni da eseguire al click del menu
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    //Logout
                    if (item.getItemId() == R.id.logout1) {
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
                    } else if (item.getItemId() == R.id.account_profile) {
                        //Lo rimando alla pagina del profilo utente
                        Intent i = new Intent(MainActivity.this, ProfiloActivity.class);
                        startActivity(i);
                    } else if (item.getItemId() == R.id.refresh) {
                        //Lo rimando alla pagina del profilo utente
                        Intent i = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(i);
                        Toast.makeText(MainActivity.this, "Pagine Aggiornate", Toast.LENGTH_SHORT).show();
                    }
                    return false;
                }
            });
        }
    }
}