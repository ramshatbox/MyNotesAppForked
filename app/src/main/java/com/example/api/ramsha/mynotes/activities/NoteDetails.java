package com.example.api.ramsha.mynotes.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.api.ramsha.mynotes.MyNotes;
import com.example.api.ramsha.mynotes.R;
import com.example.api.ramsha.mynotes.model.NotesDataModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NoteDetails extends AppCompatActivity {
    Button btn, updateBtn;
    Toolbar toolbar;
    MyNotes myNotes = MyNotes.getInstance();
    TextView title, content;
    EditText titleET, contentET;
    ImageButton delete, edit, share;
    String noteContent, noteTitle, noteDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        findViews();
        intentSetup();
        clickListeners();
    }

    private void intentSetup() {
        Intent intent = getIntent();
        noteDate = intent.getStringExtra("note_date"); // Provide a default value if needed
        noteTitle = intent.getStringExtra("note_title");
        noteContent = intent.getStringExtra("note_content");
        title.setText(noteTitle);
        content.setText(noteContent);
    }

    private void clickListeners() {
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Title: " + noteTitle + "\n" + "Content: " + noteContent);
                startActivity(Intent.createChooser(shareIntent, noteTitle));
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setVisibility(View.GONE);
                content.setVisibility(View.GONE);
                btn.setVisibility(View.GONE);
                titleET.setVisibility(View.VISIBLE);
                contentET.setVisibility(View.VISIBLE);
                updateBtn.setVisibility(View.VISIBLE);
                titleET.setText(noteTitle);
                contentET.setText(noteContent);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(NoteDetails.this);
                builder1.setMessage("Delete this item?");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        myNotes.getDb().deleteNote(noteTitle, noteContent, noteDate, myNotes.getEmail());
                        Toast.makeText(NoteDetails.this, "Note deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotesDataModel prevData = new NotesDataModel(noteTitle, noteContent, noteDate);
                String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                NotesDataModel updatedData = new NotesDataModel(titleET.getText().toString(), contentET.getText().toString(), date);
                myNotes.getDb().updateNote(prevData, updatedData, myNotes.getEmail());
                Toast.makeText(NoteDetails.this, "Note Updated", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void findViews() {
        btn = findViewById(R.id.noteDetailDoneBtn);
        updateBtn = findViewById(R.id.noteDetailUpdateBtn);
        toolbar = findViewById(R.id.toolbarNoteDetail);
        title = findViewById(R.id.noteDetailTitle);
        content = findViewById(R.id.noteDetailContent);
        titleET = findViewById(R.id.noteDetailTitleET);
        contentET = findViewById(R.id.noteDetailContentET);
        delete = findViewById(R.id.delete);
        edit = findViewById(R.id.edit);
        share = findViewById(R.id.share);
    }
}