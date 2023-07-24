package com.example.api.ramsha.mynotes.activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.api.ramsha.mynotes.MyNotes;

import com.example.api.ramsha.mynotes.R;
import com.example.api.ramsha.mynotes.model.NotesDataModel;
import com.example.api.ramsha.mynotes.notification.Notification;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;

public class AddNewNote extends AppCompatActivity {
    Toolbar toolbar;
    EditText mtitle, mcontent;
    Button addBtn, addWithLocation;
    MyNotes myNotes = MyNotes.getInstance();
    int REQUEST_LOCATION_PERMISSION = 9;
    double[] locationll = {0, 0};
    private FusedLocationProviderClient fusedLocationClient;
    Notification obj = new Notification();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_note);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        findViews();
        clickListeners();
    }

    private void findViews() {
        toolbar = findViewById(R.id.toolbarAddNewNote);
        mtitle = findViewById(R.id.newNoteTitle);
        addBtn = findViewById(R.id.addNewNoteBtn);
        mcontent = findViewById(R.id.newNoteContent);
        addWithLocation = findViewById(R.id.addNewNoteWithLocationBtn);
    }

    private void clickListeners() {
        addWithLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLoc();

            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mtitle.getText().toString();
                String content = mcontent.getText().toString();
                if (title.equals("")) title = "TITLE";
                else title = title.toUpperCase();
                if (content.equals("")) content = getString(R.string.defaultText);
                String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                NotesDataModel data = new NotesDataModel(title, content, date, new double[]{0, 0});
                myNotes.getDb().addNote(data, myNotes.getEmail());
                Toast.makeText(AddNewNote.this, "Note added", Toast.LENGTH_SHORT).show();
                obj.createNotificationChannel(AddNewNote.this,"newnote","New Note Added");
                obj.showNotification(AddNewNote.this, "New Note Added");
                mtitle.setText("");
                mcontent.setText("");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            // Check if the permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLoc();
            } else {
                // Permission is denied
                Toast.makeText(this, "Location not granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getLoc() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                // Handling the location
                                double latitude = location.getLatitude();
                                double longitude = location.getLongitude();
                                locationll[0] = latitude;
                                locationll[1] = longitude;
                                String title = mtitle.getText().toString();
                                String content = mcontent.getText().toString();
                                if (title.equals("")) title = "TITLE";
                                else title = title.toUpperCase();
                                if (content.equals("")) content = getString(R.string.defaultText);
                                String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                                NotesDataModel data = new NotesDataModel(title, content, date, locationll);
                                myNotes.getDb().addNote(data, myNotes.getEmail());
                                Toast.makeText(AddNewNote.this, "Note added", Toast.LENGTH_SHORT).show();
                                obj.createNotificationChannel(AddNewNote.this,"newnote","New Note Added with Location");
                                obj.showNotification(AddNewNote.this, "New Note Added with Location");
                                mtitle.setText("");
                                mcontent.setText("");
                            }
                        }
                    });
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }
    }
}

