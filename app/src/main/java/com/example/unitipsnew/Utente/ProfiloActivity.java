package com.example.unitipsnew.Utente;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.unitipsnew.DatabaseHelper;
import com.example.unitipsnew.MainActivity;
import com.example.unitipsnew.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static com.example.unitipsnew.Utente.RegisterActivity.CAMERA_REQUEST;
import static com.example.unitipsnew.Utente.RegisterActivity.GALLERY_REQUEST;

public class ProfiloActivity extends AppCompatActivity{
    // Database Helper
    DatabaseHelper db;
    SharedPreferences sp;
    ImageView Immagine;
    Bitmap img_bit;
    private String Document_img1="";
    private static String REGISTER_REQUEST_URL="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilo);

        db = new DatabaseHelper(getApplicationContext());
        sp = getSharedPreferences("login", MODE_PRIVATE);

        final Utente utente = db.getUser(sp.getLong("user", 0));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarmia);
        toolbar.setTitle(utente.nome);

        TextView matricola = findViewById(R.id.matricola_utente);
        EditText nome = findViewById(R.id.nome_utente);
        EditText cognome = findViewById(R.id.cognome_utente);
        TextView email = findViewById(R.id.email_utente);
        final EditText password = findViewById(R.id.password_utente);
        Immagine = findViewById(R.id.avatar_utente);

        matricola.setText("" + utente.getMatricola());
        nome.setText(utente.getNome());
        cognome.setText(utente.getCognome());
        email.setText(utente.getEmail());
        password.setText(utente.getPassword());

        img_bit = stringToBitmap(utente.getImmagine());
        Immagine.setImageBitmap(img_bit);

        Button delete = findViewById(R.id.delete_account);
        Button update = findViewById(R.id.update_account);
        ImageButton alter = findViewById(R.id.alter_avatar);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAreYouSure(utente, view);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utente u = new Utente();
                u.setMatricola(utente.getMatricola());
                u.setNome(((EditText) ProfiloActivity.this.findViewById(R.id.nome_utente)).getText().toString());
                u.setCognome(((EditText) ProfiloActivity.this.findViewById(R.id.cognome_utente)).getText().toString());
                //int rh= ProfiloActivity.this.getResources().getIdentifier("user_image", "drawable", ProfiloActivity.this.getPackageName());
                String s = bitmapToString(img_bit);
                u.setImmagine(s);
                u.setEmail(utente.getEmail());
                u.setPassword(password.getText().toString());
                db.updateUser(u);
                Toast.makeText(view.getContext(), "Account Aggiornato", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ProfiloActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        alter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


        db.closeDB();

    }

    private void openAreYouSure(final Utente utente, final View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Eliminare Account");
        builder.setMessage("Sei sicuro di vole eliminare definitivamente il tuo account?");

        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                db.deleteUser(utente.getMatricola());
                Toast.makeText(view.getContext(), "Account Eliminato", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ProfiloActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private Bitmap stringToBitmap(String string){
        Bitmap bitmap = null;
        try{
            byte[] encodeByte = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);


        }catch(Exception e){
            e.printStackTrace();
        }

        return bitmap;
    }

    private String bitmapToString(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte[] b = baos.toByteArray();
        long lengthbmp = b.length;
        if(lengthbmp / 1000000 >= 3.8){
            return "";
        }
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public void openCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(intent, CAMERA_REQUEST);
        }
    }

    public void openGallery(){
        //lanch gallery request
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Seleziona immagine"), GALLERY_REQUEST);
    }

    private void selectImage() {
        final CharSequence[] options = {"Scatta una foto", "Scegli dalla Galleria", "Annulla"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfiloActivity.this);
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
                img_bit = BitmapFactory.decodeStream(imageStream);
                Immagine.setImageBitmap(img_bit);

            }catch (IOException io){
                io.printStackTrace();
            }
        }

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK){
            try{
                Bundle extras = data.getExtras();
                Bitmap image = (Bitmap) extras.get("data");
                img_bit = image;
                Immagine.setImageBitmap(image);

            }catch(Exception io){

            }
        }
    }
}
