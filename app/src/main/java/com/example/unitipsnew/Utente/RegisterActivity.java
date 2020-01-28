package com.example.unitipsnew.Utente;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import androidx.annotation.Nullable;
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
    final static int CAMERA_REQUEST = 101;
    final static int GALLERY_REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        sp = getSharedPreferences("login", MODE_PRIVATE);
        db = new DatabaseHelper(getApplicationContext());

        setContentView(R.layout.activity_registrazione);


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
        Login = findViewById(R.id.back_to_login1);

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
                if (validate(db)) {
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

    private boolean validate(DatabaseHelper db) {

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

        try {
            m = Long.parseLong(matricola);
            for (Utente u : utenti) {
                if (u.getMatricola() == m) {
                    check = false;
                    justmat = true;
                    counter++;
                }
                if (u.getEmail().equals(email)) {
                    check = false;
                    justemail = true;
                    counter++;
                }
            }
        } catch (Exception e) {
            check = false;
            counter++;
            info = "Il tuo numero di matricola non sembra essere corretto";
            Matricola.setBackgroundColor(Color.RED);
        }

        if (nome == null || nome.equals("")) {
            check = false;
            counter++;
            info = "Il tuo nome non sembra essere corretto";
            Nome.setBackgroundColor(Color.RED);
        }

        if (cognome == null || cognome.equals("")) {
            check = false;
            counter++;
            info = "Il tuo cognome non sembra essere corretto";
            Cognome.setBackgroundColor(Color.RED);
        }

        if (email == null || email.equals("") || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            check = false;
            counter++;
            info = "La tua email non sembra essere corretta";
            Email.setBackgroundColor(Color.RED);
        }

        if (password == null || password.equals("")) {
            check = false;
            counter++;
            info = "La tua password non sembra essere corretta";
            Password.setBackgroundColor(Color.RED);
        }

        if (repeat == null || repeat.equals("") || !repeat.equals(password)) {
            check = false;
            counter++;
            info = "Le password non coincidono";
            Password.setBackgroundColor(Color.RED);
            RipetiPassword.setBackgroundColor(Color.RED);
        }

        if (counter > 1) {
            info = "Più campi non sembrano essere corretti.";
        }

        int immagine = RegisterActivity.this.getResources().getIdentifier("logo", "drawable", RegisterActivity.this.getPackageName());

        if (check) {
            user = new Utente(m, email, nome, cognome, password, immagine);
        } else {
            if (justemail && !justmat) {
                info = "Esiste già un account con questa email";
            } else if (!justemail && justmat) {
                info = "Il numero di matricola è gia stato registrato.";
            } else if (justemail && justmat) {
                info = "Ti sei già registrato con questo numero di matricola e questa email";
            }
            Info.setText(info);
        }

        return check;
    }

    public void openCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(intent, CAMERA_REQUEST);
        }
    }

    public void openGallery(){
        //lanch gallery request
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select picture"), GALLERY_REQUEST);
    }

    private void selectImage() {
        final CharSequence[] options = {"Scatta una foto", "Scegli dalla Galleria", "Annulla"};
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("Aggiungi un'immagine!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Scatta una foto")) {
                    openCamera();
                } else if (options[item].equals("Scegli dalla Galleria")) {
                   openGallery();
                } else if (options[item].equals("Annulla")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
            try{
                Uri selectedImage = data.getData();
                InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                AlterImmagine.setImageBitmap(BitmapFactory.decodeStream(imageStream));

            }catch (IOException io){
                io.printStackTrace();
            }
        }

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK){
            try{
                Bundle extras = data.getExtras();
                Bitmap image = (Bitmap) extras.get("data");

                AlterImmagine.setImageBitmap(image);

            }catch(Exception io){

            }
        }
    }
}
