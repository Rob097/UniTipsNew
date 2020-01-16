package com.example.unitipsnew.Utente;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.unitipsnew.DatabaseHelper;
import com.example.unitipsnew.MainActivity;
import com.example.unitipsnew.R;

import java.io.File;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private ImageButton AlterImmagine;
    private ImageView Immagine;
    private EditText Matricola;
    private EditText Nome;
    private EditText Cognome;
    private EditText Email;
    private EditText Password;
    private EditText RipetiPassword;
    private TextView Info;
    private Button Registrati;
    private Button Login;
    private Utente user;
    // Database Helper
    DatabaseHelper db;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db = new DatabaseHelper(getApplicationContext());

        AlterImmagine = findViewById(R.id.alter_avatar_register);
        Immagine = findViewById(R.id.avatar_register);
        Matricola = findViewById(R.id.matricola_register);
        Nome = findViewById(R.id.nome_register);
        Cognome = findViewById(R.id.cognome_register);
        Email = findViewById(R.id.email_register);
        Password = findViewById(R.id.password_register);
        RipetiPassword = findViewById(R.id.repeat_password_register);
        Info = findViewById(R.id.info_register);
        Registrati = findViewById(R.id.register_account);
        Login = findViewById(R.id.back_to_login);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        Registrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate(db)){
                    sp = getSharedPreferences("login", MODE_PRIVATE);
                    db.createUser(user);
                    sp.edit().putBoolean("logged", true).apply();
                    sp.edit().putLong("user", user.getMatricola()).apply();
                    Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });

        AlterImmagine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    private boolean validate(DatabaseHelper db){

        List<Utente> utenti = db.getAllUsers();

        String matricola = Matricola.getText().toString();
        String nome = Nome.getText().toString();
        String cognome = Cognome.getText().toString();
        String email = Email.getText().toString();
        String password = Password.getText().toString();
        String repeat = RipetiPassword.getText().toString();

        String info = "";
        boolean check = true, justmat = false, justemail = false;
        int counter = 0;
        long m = 0;

        try{
            m = Long.parseLong(matricola);
            for(Utente u : utenti){
                if(u.getMatricola() == m){
                    check = false;
                    justmat = true;
                    counter++;
                }
                if(u.getEmail().equals(email)){
                    check = false;
                    justemail = true;
                    counter++;
                }
            }
        }catch(Exception e){
            check = false;
            counter++;
            info = "Il tuo numero di matricola non sembra essere corretto";
            Matricola.setBackgroundColor(Color.RED);
        }

        if(nome == null || nome.equals("")){
            check = false;
            counter++;
            info = "Il tuo nome non sembra essere corretto";
            Nome.setBackgroundColor(Color.RED);
        }

        if(cognome == null || cognome.equals("")){
            check = false;
            counter++;
            info = "Il tuo cognome non sembra essere corretto";
            Cognome.setBackgroundColor(Color.RED);
        }

        if(email == null || email.equals("") || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            check = false;
            counter++;
            info = "La tua email non sembra essere corretta";
            Email.setBackgroundColor(Color.RED);
        }

        if(password == null || password.equals("")){
            check = false;
            counter++;
            info = "La tua password non sembra essere corretta";
            Password.setBackgroundColor(Color.RED);
        }

        if(repeat == null || repeat.equals("") || !repeat.equals(password)){
            check = false;
            counter++;
            info = "Le password non coincidono";
            Password.setBackgroundColor(Color.RED);
            RipetiPassword.setBackgroundColor(Color.RED);
        }
        
        if(counter > 1){
            info = "Più campi non sembrano essere corretti.";
        }

        int immagine= RegisterActivity.this.getResources().getIdentifier("logo", "drawable", RegisterActivity.this.getPackageName());
        
        if(check){
            user = new Utente(m, email, nome, cognome, password, immagine);
        }else{
            if(justemail && !justmat){
                info = "Esiste già un account con questa email";
            }else if(!justemail && justmat){
                info = "Il numero di matricola è gia stato registrato.";
            }else if(justemail && justmat){
                info = "Ti sei già registrato con questo numero di matricola e questa email";
            }
            Info.setText(info);
        }

        return check;
    }

    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

}
