package com.example.hackermail;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.hackermail.MainActivity.emailId;

public class logIn extends AppCompatActivity {

    private TextView txtLogEmail;
    public static final String EXTRA_REPLY
            = "LOGIN.REPLY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        txtLogEmail = findViewById(R.id.editTextEmailLogIn);
    }

    public void login(View view) {

        String email = txtLogEmail.getText().toString();
        emailId = email;
//        Toast.makeText(this,email, Toast.LENGTH_SHORT).show();

        //auto jump to MainActivity
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent jumpIntent = new Intent(logIn.this,
                        MainActivity.class);
                logIn.this.startActivity(jumpIntent);
                logIn.this.finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        }, 1000);

    }

    public void cancel(View view) {
        emailId = "";
        //auto jump to MainActivity
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent jumpIntent = new Intent(logIn.this,
                        MainActivity.class);
                logIn.this.startActivity(jumpIntent);
                logIn.this.finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        }, 1000);
    }
}
