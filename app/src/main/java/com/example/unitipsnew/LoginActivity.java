package com.example.unitipsnew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private int counter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Name = (EditText) findViewById(R.id.username);
        Password = (EditText) findViewById(R.id.password);
        Info = (TextView) findViewById(R.id.info);
        Login = (Button) findViewById(R.id.login);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(Name.getText().toString(), Password.getText().toString());
            }
        });
    }

    private void validate(String user, String password){
        /*if ((user.equals("Admin"))&& (password.equals("admin"))){*/
        if ((user.equals(""))&& (password.equals(""))){
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
        }else{
            counter--;

            Info.setText("Email o password errati, riprova");

            if(counter == 0){
                Login.setEnabled(false);
                Login.setBackgroundColor(Color.GRAY);
                Info.setText("Tentativi terminati");
            }
        }
    }
}
