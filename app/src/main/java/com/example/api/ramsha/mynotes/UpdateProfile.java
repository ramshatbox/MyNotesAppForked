package com.example.api.ramsha.mynotes;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class UpdateProfile extends AppCompatActivity {
    Uri uri=null;
    private static final int PICK_IMAGE = 123;
    ImageView imageView;
    MyNotes myNotes;
    EditText updatedName,updatedPass,updatedEmail,updatedDob;
    String updatedProfile,updatedGender;
    Button updateBtn;
    RadioGroup radioGroup;
    TextView nameTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        updatedGender="";
        updatedProfile="";
        myNotes = MyNotes.getInstance();
        findViews();
        getProfileAndName();
        clickListeners();
        changeListeners();
    }

    private void getProfileAndName() {
        List<String> data = myNotes.getVariableValues();
        nameTitle.setText(data.get(1));
        Glide.with(this).load(uri.parse(data.get(0))).into(imageView);
    }

    private void changeListeners() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Get the selected radio button by its id
                RadioButton selectedRadioButton = findViewById(checkedId);

                // Get the text of the selected radio button
                updatedGender = selectedRadioButton.getText().toString();

            }
        });
    }

    private void clickListeners() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=updatedName.getText().toString();
                String pass=updatedPass.getText().toString();
                String email=updatedEmail.getText().toString();
                String dob=updatedDob.getText().toString();

                myNotes.updateVariables(updatedProfile,name,pass,email,dob,updatedGender);
                List<String> data = myNotes.getVariableValues();
                nameTitle.setText(data.get(1));
                Glide.with(UpdateProfile.this).load(uri.parse(updatedProfile)).into(imageView);

            }
        });


    }

    private void findViews() {
        imageView = findViewById(R.id.updateUserProfilePicture);
        updatedName=findViewById(R.id.updateUserNameET);
        updatedEmail=findViewById(R.id.updateUserEmailET);
        updatedPass=findViewById(R.id.updateUserpasswordET);
        updatedDob=findViewById(R.id.updateDateOfBirthET);
        updateBtn=findViewById(R.id.updateButton);
        radioGroup=findViewById(R.id.updateUserGender);
        nameTitle=findViewById(R.id.updateTitleNotes);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            if (data != null) {
                uri = data.getData();
                updatedProfile=uri.toString();
            }
            else
                updatedProfile="";
                //Glide.with(this).load(uri).into(imageView);
        }
    }


}