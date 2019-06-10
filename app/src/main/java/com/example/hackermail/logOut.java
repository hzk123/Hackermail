package com.example.hackermail;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static com.example.hackermail.MainActivity.emailId;

public class logOut extends AppCompatActivity {

    private TextView textEmail;
    private TextView textID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_out);

        Intent intent = getIntent();
        String email = intent.getStringExtra(MainActivity.LOGOUT_MESSAGE);
        TextView textEmail = findViewById(R.id.textViewEmail);
        textEmail.setText(email);
    }

    public void logOut(View view) {
        emailId = "";

        //auto jump to MainActivity
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent jumpIntent = new Intent(logOut.this,
                        MainActivity.class);
                logOut.this.startActivity(jumpIntent);
                logOut.this.finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        }, 1000);
    }

    public void cancel(View view) {

        //auto jump to MainActivity
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent jumpIntent = new Intent(logOut.this,
                        MainActivity.class);
                logOut.this.startActivity(jumpIntent);
                logOut.this.finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        }, 1000);
    }
}
