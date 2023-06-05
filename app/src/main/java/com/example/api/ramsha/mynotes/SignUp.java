package com.example.api.ramsha.mynotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

public class SignUp extends AppCompatActivity {
    EditText dateOfBirth,usernameET,userEmailET,userPasswordET;
    Button createButton;
    String setDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        dateOfBirth = (EditText) findViewById(R.id.dateOfBirthET);
        usernameET = (EditText) findViewById(R.id.userNameET);
        userEmailET = (EditText) findViewById(R.id.userEmailET);
        userPasswordET = (EditText) findViewById(R.id.userpasswordET);
        createButton=(Button)findViewById(R.id.createButton);
        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                       SignUp.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                                dateOfBirth.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateOfBirth.setText("");
                userEmailET.setText("");
                userPasswordET.setText("");
                usernameET.setText("");
            }
        });

//        dateOfBirth.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                dateOfBirth.setInputType(InputType.TYPE_NULL);
//                return false;
//            }
//        });
    }
}

