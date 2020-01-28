package com.example.unitipsnew.Utente;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.unitipsnew.DatabaseHelper;
import com.example.unitipsnew.R;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RestoreActivity extends AppCompatActivity {

    DatabaseHelper db;
    Utente u;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new DatabaseHelper(getApplicationContext());

        setContentView(R.layout.forgot_password);

        final EditText email = findViewById(R.id.email_restore);
        final EditText matricola = findViewById(R.id.matricola_restore);
        final EditText password = findViewById(R.id.password_restore);
        final TextView info = findViewById(R.id.info_restore_password);
        Button restore = findViewById(R.id.restore_password_button);

        restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean check = true;
                try {
                    if (!matricola.getText().toString().equals("") && db.getUser(Long.parseLong(matricola.getText().toString())) != null) {
                        u = db.getUser(Long.parseLong(matricola.getText().toString()));
                        if (!email.getText().toString().equals("") && u.getEmail().equals(email.getText().toString())) {
                            if (!password.getText().toString().equals("")) {
                                u.setPassword(password.getText().toString());
                                db.updateUser(u);
                            } else {
                                info.setText("La password deve essere più complessa");
                                check = false;
                            }
                        } else {
                            info.setText("Il numero di matricola e l'email inseriti non coincidono");
                            check = false;
                        }
                    } else {
                        info.setText("Non esiste un account con questo numero di matricola");
                        check = false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(check) {
                    Intent i = new Intent(RestoreActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            }
        });
    }
}
