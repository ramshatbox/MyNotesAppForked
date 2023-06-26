package com.example.api.ramsha.mynotes.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.api.ramsha.mynotes.MyNotes;
import com.example.api.ramsha.mynotes.R;
import com.example.api.ramsha.mynotes.model.UserModel;

import java.util.List;

public class UpdateProfile extends AppCompatActivity {
    Uri uri=null;
    private static final int PICK_IMAGE = 123;
    ImageView imageView;
    MyNotes myNotes;
    EditText updatedName,updatedPass,updatedEmail,updatedDob;
    String updatedProfile,updatedGender;
    String previousEmail="";
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
        previousEmail=myNotes.getEmail();
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
                RadioButton selectedRadioButton = findViewById(checkedId);
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
                UserModel userModel = new UserModel(data.get(1),data.get(2),data.get(4),data.get(5));
                myNotes.getDb().updateUser(userModel,previousEmail);
                Toast.makeText(UpdateProfile.this, "User Data updated", Toast.LENGTH_SHORT).show();
                finish();
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