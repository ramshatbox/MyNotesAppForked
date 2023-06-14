package com.example.api.ramsha.mynotes;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.Calendar;

public class SignUp extends AppCompatActivity {
    EditText dateOfBirth,usernameET,userEmailET,userPasswordET;
    Button createButton;
    SearchView searchView;
    MyNotes myNotes;
    RadioGroup radioGroup;
    String gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        gender="";
        myNotes=MyNotes.getInstance();
        findViews();
        clickListeners();
        changeListeners();
    }

    private void changeListeners() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Get the selected radio button by its id
                RadioButton selectedRadioButton = findViewById(checkedId);

                // Get the text of the selected radio button
                gender = selectedRadioButton.getText().toString();
            }
        });
    }

    void findViews(){
        dateOfBirth = (EditText) findViewById(R.id.dateOfBirthET);
        usernameET = (EditText) findViewById(R.id.userNameET);
        userEmailET = (EditText) findViewById(R.id.userEmailET);
        userPasswordET = (EditText) findViewById(R.id.userpasswordET);
        createButton=(Button)findViewById(R.id.createButton);
        radioGroup=findViewById(R.id.userGender);
    }
    void clickListeners(){
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
                myNotes.updateVariables("",usernameET.getText().toString(),userPasswordET.getText().toString(),userEmailET.getText().toString(),dateOfBirth.getText().toString(),gender);
                Toast.makeText(myNotes, ""+usernameET.getText().toString(), Toast.LENGTH_SHORT).show();
                dateOfBirth.setText("");
                userEmailET.setText("");
                userPasswordET.setText("");
                usernameET.setText("");
                Intent intent = new Intent(SignUp.this,Home.class);
                startActivity(intent);
            }
        });
    }
}

