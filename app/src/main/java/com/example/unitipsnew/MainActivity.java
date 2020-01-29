package com.example.unitipsnew;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.example.unitipsnew.Eventi.Evento;
import com.example.unitipsnew.Recensioni.Corso;
import com.example.unitipsnew.Recensioni.Recensione;
import com.example.unitipsnew.Tips.Commento;
import com.example.unitipsnew.Tips.Tip;
import com.example.unitipsnew.Utente.LoginActivity;
import com.example.unitipsnew.Utente.ProfiloActivity;
import com.example.unitipsnew.Utente.Utente;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.unitipsnew.ui.main.SectionsPagerAdapter;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    SharedPreferences sp;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //this.deleteDatabase("uniTipsDB");

        db = new DatabaseHelper(getApplicationContext());
        sp = getSharedPreferences("login", MODE_PRIVATE);


        //db.onUpgrade(db.getReadableDatabase(), 1, 1);
        //Commentare in databaseHelper le righe delle tabelle user, corso e recensione in oncreate, decommentare il seguente codice, eseguire l'app, commentare di nuovo il codice, decommentare le righe in databasehelper

        //db.onCreate(db.getReadableDatabase());
        /*Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.careerfair_unitn);
        String s = bitmapToString(icon);*/

       // List<Evento> eventi = new ArrayList<>();
        /*eventi.add(new Evento(10, s, new ArrayList<Long>(), "Festa Universitaria Centrale", "Descrizione Festa Universitaria Centrale", "Trento", db.getDateTime()));
        eventi.add(new Evento(11, s, new ArrayList<Long>(), "Career Fair", "Descrizione Career Fair", "Trento", db.getDateTime()));*/


        /*eventi = db.getAllEventi();
        for(Evento e : eventi) {
            db.deleteEvento(e.getId());
        }*/
        //sp.edit().putBoolean("logged", true).apply();
        //sp.edit().putLong("user", 1).apply();


        /*  CODICE PER POPOLARE LA TABELLA DEI CORSI
        ArrayList<Corso> corsi = new ArrayList<>();
        corsi.add(new Corso("Analisi Matematica 1", "Anneliese Defranceschi"));
        corsi.add(new Corso("Analisi Matematica 2", "Anneliese Defranceschi"));
        corsi.add(new Corso("Ingegneria del software 1", "Paolo Giorgini"));
        corsi.add(new Corso("Ingegneria del software 2", "Adolfo Villafiorita"));
        corsi.add(new Corso("Fisica", "Roberto Battiston"));
        corsi.add(new Corso("Economia ed innovazione d'impresa", "Mariasole Bannò"));
        corsi.add(new Corso("Geometria e Algebra Lineare", "Ochetta"));

        for(Corso c : corsi){
            db.createCorso(c);
        }*/

        if(sp.getBoolean("logged", true) != true || sp.getLong("user", 01) == 0){
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
        }else {


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

    /*private String bitmapToString(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }*/
}