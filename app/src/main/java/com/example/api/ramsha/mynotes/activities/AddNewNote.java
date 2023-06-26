package com.example.api.ramsha.mynotes.activities;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.api.ramsha.mynotes.MyNotes;
import com.example.api.ramsha.mynotes.R;
import com.example.api.ramsha.mynotes.model.NotesDataModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddNewNote extends AppCompatActivity {
    Toolbar toolbar;
    EditText mtitle, mcontent;
    Button addBtn;
    MyNotes myNotes = MyNotes.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_note);
        findViews();
        clickListeners();
    }

    private void findViews() {
        toolbar = findViewById(R.id.toolbarAddNewNote);
        mtitle = findViewById(R.id.newNoteTitle);
        addBtn = findViewById(R.id.addNewNoteBtn);
        mcontent = findViewById(R.id.newNoteContent);
    }

    private void clickListeners() {
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
                NotesDataModel data = new NotesDataModel(title, content, date);
                myNotes.getDb().addNote(data, myNotes.getEmail());
                Toast.makeText(AddNewNote.this, "Note added", Toast.LENGTH_SHORT).show();
                mtitle.setText("");
                mcontent.setText("");
            }
        });
    }


}

