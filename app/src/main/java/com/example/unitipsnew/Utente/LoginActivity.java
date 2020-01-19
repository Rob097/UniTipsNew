package com.example.unitipsnew.Utente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.unitipsnew.DatabaseHelper;
import com.example.unitipsnew.MainActivity;
import com.example.unitipsnew.R;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Registrati;
    private Button Login;
    private CheckBox Remember;
    private int counter = 3;
    // Database Helper
    DatabaseHelper db;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Controllo se l'utente aveva selezionato il remember me
        sp = getSharedPreferences("checkbox", MODE_PRIVATE);
        long l = sp.getLong("user", 01);

        //Stampa nei log tutti gli utenti registrati
        db = new DatabaseHelper(getApplicationContext());
        List<Utente> utenti = db.getAllUsers();
        for(Utente u : utenti){
            Log.d("utente: ", u.toString());
        }
        //Se i dati coincidono lo faccio passare alla main activity altrimenti gli chideo di autenticarsi
        if(sp.getBoolean("remember", true) == true && l != 0){
            sp.edit().putBoolean("logged", true).apply();
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
        }else{
            Toast.makeText(this, "Autenticati o Registrati.", Toast.LENGTH_SHORT).show();
        }

        Name = (EditText) findViewById(R.id.username);
        Password = (EditText) findViewById(R.id.password);
        Info = (TextView) findViewById(R.id.info);
        Login = (Button) findViewById(R.id.login);
        Remember = (CheckBox) findViewById(R.id.remember);
        Registrati = (Button) findViewById(R.id.registrati_action);

        //Valido i dati
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(Name.getText().toString(), Password.getText().toString());
            }
        });

        //Salvo nelle preferenze che l'utente aveva chiesto di ricordarlo
        Remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sp = getSharedPreferences("checkbox", MODE_PRIVATE);
                if(compoundButton.isChecked()){
                    sp.edit().putBoolean("remember", true).apply();
                }else{
                    sp.edit().putBoolean("remember", false).apply();
                }
            }
        });

        Registrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
        //Stampa nei log tutti gli utenti registrati
        /*db = new DatabaseHelper(getApplicationContext());
        List<Utente> utenti = db.getAllUsers();
        for(Utente u : utenti){
            Log.d("utente: ", u.toString());
        }*/
    }

    private void validate(String user, String password) {
        sp = getSharedPreferences("login", MODE_PRIVATE);
        sp.edit().putBoolean("logged", false).apply();
        db = new DatabaseHelper(getApplicationContext());

        //Prendo tutti gli utenti salvati nel DB
        List<Utente> allUsers = db.getAllUsers();

        for (Utente utente : allUsers) {

            //Se trovo i dati che coincidono e l'utente esiste salvo che si è loggato e lo faccio andare nella main activity
            if (user.equals(utente.getEmail()) && password.equals(utente.getPassword())) {
                sp.edit().putBoolean("logged", true).apply();
                sp.edit().putLong("user", utente.getMatricola()).apply();
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
            }

        }

        //Se i dati sono sbagliati scalo il counter. Dopo 3 tentativi disabilito il tasto
        if (!sp.getBoolean("logged", false)) {
            counter--;

            Info.setText("Email o password errati, riprova");

            if (counter == 0) {
                Login.setEnabled(false);
                Login.setBackgroundColor(Color.GRAY);
                Info.setText("Tentativi terminati");
            }
        }

        db.closeDB();
    }
}
