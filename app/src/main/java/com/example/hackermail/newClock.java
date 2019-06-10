package com.example.hackermail;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.TimeUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.view.View.OnClickListener;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import static com.example.hackermail.MainActivity.EXTRA_DATA_UPDATE_CLOCK;

public class newClock extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.hackermail.REPLY";
    public static final int RESULT_INSERT = 2;
    public static final int RESULT_UPDATE = 3;

    private EditText mEditViewToEmail;
    private EditText mEditViewTime;
    private EditText mEditViewContent;
    private TextView mTest;
    private Clock clock;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_clock);

        mEditViewToEmail = findViewById(R.id.editTextEmail);
        mEditViewTime = findViewById(R.id.editTextTime);
        mEditViewContent = findViewById(R.id.editTextContent);
        mEditViewTime.setFocusable(false);


        mEditViewTime.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
            // 为设置时间按钮绑定监听器
                mEditViewTime.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Calendar c = Calendar.getInstance();
                        // create TimePickerDialog and show
                        new TimePickerDialog(newClock.this,3,
                                // Listener
                                new TimePickerDialog.OnTimeSetListener() {

                                    @Override
                                    public void onTimeSet(TimePicker view,
                                                          int hourOfDay, int minute) {
                                        mEditViewTime.setText(hourOfDay + " : " + minute );

                                    }
                                }
                                // set init time
                                , c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),
                                // true --> 24 hour
                                true).show();
                    }
                });
            }
        });


        final Bundle extras = getIntent().getExtras();

        // If we are passed content, fill it in for the user to edit.
        if (extras != null) {
            clock = (Clock) extras.getSerializable(EXTRA_DATA_UPDATE_CLOCK);
            if (clock != null && !TextUtils.isEmpty(clock.getToemail())) {
                mEditViewToEmail.setText(clock.getToemail());
                mEditViewToEmail.setSelection(clock.getToemail().length());
                mEditViewToEmail.requestFocus();
            }
            if (clock != null && !TextUtils.isEmpty(clock.getTime())) {
                mEditViewTime.setText(clock.getTime());
                mEditViewTime.setSelection(clock.getTime().length());
            }
            if (clock != null && !TextUtils.isEmpty(clock.getContent())) {
                mEditViewContent.setText(clock.getContent());
                mEditViewContent.setSelection(clock.getContent().length());
            }
        }

        // Otherwise, start with empty fields.
        final Button button = findViewById(R.id.buttonSave);

        // When the user presses the Save button, create a new Intent for the reply.
        // The reply Intent will be sent back to the calling activity (in this case, MainActivity)
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (clock == null) {
                    //insert
                    if (TextUtils.isEmpty(mEditViewToEmail.getText()) && TextUtils.isEmpty(mEditViewTime.getText())
                            && TextUtils.isEmpty(mEditViewContent.getText())) {
                        // No clock was entered, set the result accordingly.
                        setResult(RESULT_CANCELED, replyIntent);
                    } else {
                        // Get the new word that the user entered.
                        String toEmail = mEditViewToEmail.getText().toString();
                        String time = mEditViewTime.getText().toString();
                        String content = mEditViewContent.getText().toString();
                        // Put the new clock in the extras for the reply Intent.
                        Clock resultClock = new Clock(toEmail, time, content);
                        replyIntent.putExtra(EXTRA_REPLY, resultClock);
                        // Set the result status to indicate success.
                        setResult(RESULT_INSERT, replyIntent);
                    }
                } else {
                    //update
                    // Get the new clock that the user entered.
                    String toEmail = mEditViewToEmail.getText().toString();
                    String time = mEditViewTime.getText().toString();
                    String content = mEditViewContent.getText().toString();
                    // Put the new clock in the extras for the reply Intent.
                    newClock.this.clock.setToemail(toEmail);
                    newClock.this.clock.setTime(time);
                    newClock.this.clock.setContent(content);
                    replyIntent.putExtra(EXTRA_REPLY, newClock.this.clock);
                    // Set the result status to indicate success.
                    setResult(RESULT_UPDATE, replyIntent);
                }
                finish();
            }
        });

    }



}