package com.example.api.ramsha.mynotes.activities;

import static com.example.api.ramsha.mynotes.data.FileStorage.SaveData;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.api.ramsha.mynotes.MyNotes;
import com.example.api.ramsha.mynotes.R;
import com.example.api.ramsha.mynotes.model.UserModel;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

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
        FirebaseCrashlytics.getInstance().log("SigUp Page");

    }

    private void changeListeners() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = findViewById(checkedId);
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
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
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
                //save data to file
                SaveData(usernameET.getText().toString(),userEmailET.getText().toString(),dateOfBirth.getText().toString(),gender);
                //save data to database
                UserModel user = new UserModel(usernameET.getText().toString(),userEmailET.getText().toString(),dateOfBirth.getText().toString(),gender);
                myNotes.getDb().addUser(user);
                Toast.makeText(myNotes, ""+usernameET.getText().toString(), Toast.LENGTH_SHORT).show();
                dateOfBirth.setText("");
                userEmailET.setText("");
                userPasswordET.setText("");
                usernameET.setText("");
                Intent intent = new Intent(SignUp.this, Home.class);
                startActivity(intent);
            }
        });
    }
}

