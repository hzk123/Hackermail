package com.example.hackermail;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import static com.example.hackermail.MainActivity.EXTRA_DATA_UPDATE_Template;

public class newTemplate extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.hackermail.REPLY";
    public static final int RESULT_INSERT = 2;
    public static final int RESULT_UPDATE = 3;

    private EditText mEditViewToEmail;
    private EditText mEditViewTime;
    private EditText mEditViewContent;
    private TextView mTest;
    private Template Template;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_Template);

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
                        new TimePickerDialog(newTemplate.this,3,
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
            Template = (Template) extras.getSerializable(EXTRA_DATA_UPDATE_Template);
            if (Template != null && !TextUtils.isEmpty(Template.getContent())) {
                mEditViewContent.setText(Template.getContent());
                mEditViewContent.setSelection(Template.getContent().length());
            }
        }

        // Otherwise, start with empty fields.
        final Button button = findViewById(R.id.buttonSave);

        // When the user presses the Save button, create a new Intent for the reply.
        // The reply Intent will be sent back to the calling activity (in this case, MainActivity)
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (Template == null) {
                    //insert
                    if (TextUtils.isEmpty(mEditViewToEmail.getText()) && TextUtils.isEmpty(mEditViewTime.getText())
                            && TextUtils.isEmpty(mEditViewContent.getText())) {
                        // No Template was entered, set the result accordingly.
                        setResult(RESULT_CANCELED, replyIntent);
                    } else {
                        // Get the new word that the user entered.
                        String toEmail = mEditViewToEmail.getText().toString();
                        String time = mEditViewTime.getText().toString();
                        String content = mEditViewContent.getText().toString();
                        // Put the new Template in the extras for the reply Intent.
                        Template resultTemplate = new Template(toEmail, time, content);
                        replyIntent.putExtra(EXTRA_REPLY, resultTemplate);
                        // Set the result status to indicate success.
                        setResult(RESULT_INSERT, replyIntent);
                    }
                } else {
                    //update
                    // Get the new Template that the user entered.
                    String content = mEditViewContent.getText().toString();
                    // Put the new Template in the extras for the reply Intent.
                    newTemplate.this.Template.setContent(content);
                    replyIntent.putExtra(EXTRA_REPLY, newTemplate.this.Template);
                    // Set the result status to indicate success.
                    setResult(RESULT_UPDATE, replyIntent);
                }
                finish();
            }
        });

    }



}