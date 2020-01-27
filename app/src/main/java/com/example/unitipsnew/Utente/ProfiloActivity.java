package com.example.unitipsnew.Utente;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ProfiloActivity extends AppCompatActivity{
    // Database Helper
    DatabaseHelper db;
    SharedPreferences sp;
    ImageView avatar;
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
        avatar = findViewById(R.id.avatar_utente);

        matricola.setText("" + utente.getMatricola());
        nome.setText(utente.getNome());
        cognome.setText(utente.getCognome());
        email.setText(utente.getEmail());
        password.setText(utente.getPassword());
        avatar.setImageDrawable(this.getResources().getDrawable(utente.getImmagine()));

        Button delete = findViewById(R.id.delete_account);
        Button update = findViewById(R.id.update_account);
        //ImageButton alter = findViewById(R.id.alter_avatar);

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
                u.setImmagine(utente.getImmagine());
                u.setEmail(utente.getEmail());
                u.setPassword(password.getText().toString());
                db.updateUser(u);
                Toast.makeText(view.getContext(), "Account Aggiornato", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ProfiloActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        /*alter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });*/


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

    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfiloActivity.this);
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

    public String BitMapToString(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] b = baos.toByteArray();
        Document_img1 = Base64.encodeToString(b, Base64.DEFAULT);
        return Document_img1;
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
}
