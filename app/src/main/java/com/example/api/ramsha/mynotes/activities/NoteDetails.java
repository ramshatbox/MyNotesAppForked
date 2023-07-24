package com.example.api.ramsha.mynotes.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.api.ramsha.mynotes.MyNotes;
import com.example.api.ramsha.mynotes.R;
import com.example.api.ramsha.mynotes.adapter.NotesPagerAdapter;
import com.example.api.ramsha.mynotes.model.NotesDataModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NoteDetails extends AppCompatActivity {
   // Button btn, updateBtn;
    Toolbar toolbar;
    MyNotes myNotes = MyNotes.getInstance();
    //TextView title, content;
    //EditText titleET, contentET;
    //ImageButton delete, edit, share, location;
    //String noteContent, noteTitle, noteDate;
    //double[] latlong={0.0,0.0};
    ViewPager viewPager;
    ArrayList<NotesDataModel> notesData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        notesData=myNotes.getDb().getAllNotes(myNotes.getEmail());
        findViews();
        intentSetup();
        clickListeners();
        if(notesData.size()>0){
            NotesPagerAdapter adapter = new NotesPagerAdapter(this, notesData);
            viewPager.setAdapter(adapter);
        }

    }

    private void intentSetup() {
        Intent intent = getIntent();
//        noteDate = intent.getStringExtra("note_date"); // Provide a default value if needed
//        noteTitle = intent.getStringExtra("note_title");
//        noteContent = intent.getStringExtra("note_content");
//        latlong = intent.getDoubleArrayExtra("location");
//        if (latlong[0] == 0.0 && latlong[1] == 0.0)
//            location.setVisibility(View.INVISIBLE);
//        else
//            location.setVisibility(View.VISIBLE);
//        title.setText(noteTitle);
//        content.setText(noteContent);
        NotesDataModel data = new NotesDataModel(intent.getStringExtra("note_title"),intent.getStringExtra("note_content"),intent.getStringExtra("note_date"),intent.getDoubleArrayExtra("location"));

    }

    private void clickListeners() {
//        location.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String uri = "geo:" + latlong[0] + "," + latlong[1] + "?q=" + latlong[0] + "," + latlong[1];
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//                intent.setPackage("com.google.android.apps.maps");
//                startActivity(intent);
//            }
//        });
//        share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                shareIntent.setType("text/plain");
//                shareIntent.putExtra(Intent.EXTRA_TEXT, "Title: " + noteTitle + "\n" + "Content: " + noteContent);
//                startActivity(Intent.createChooser(shareIntent, noteTitle));
//            }
//        });
//        edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                title.setVisibility(View.GONE);
//                content.setVisibility(View.GONE);
//                btn.setVisibility(View.GONE);
//                titleET.setVisibility(View.VISIBLE);
//                contentET.setVisibility(View.VISIBLE);
//                updateBtn.setVisibility(View.VISIBLE);
//                titleET.setText(noteTitle);
//                contentET.setText(noteContent);
//            }
//        });
//        delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder1 = new AlertDialog.Builder(NoteDetails.this);
//                builder1.setMessage("Delete this item?");
//                builder1.setCancelable(true);
//                builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        myNotes.getDb().deleteNote(noteTitle, noteContent, noteDate, myNotes.getEmail());
//                        Toast.makeText(NoteDetails.this, "Note deleted", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                });
//                AlertDialog alert11 = builder1.create();
//                alert11.show();
//            }
//        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        updateBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NotesDataModel prevData = new NotesDataModel(noteTitle, noteContent, noteDate, new double[]{0, 0});
//                String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
//                NotesDataModel updatedData = new NotesDataModel(titleET.getText().toString(), contentET.getText().toString(), date, new double[]{0, 0});
//                myNotes.getDb().updateNote(prevData, updatedData, myNotes.getEmail());
//                Toast.makeText(NoteDetails.this, "Note Updated", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void findViews() {
        viewPager = findViewById(R.id.viewPager);

//        btn = findViewById(R.id.noteDetailDoneBtn);
//        updateBtn = findViewById(R.id.noteDetailUpdateBtn);
        toolbar = findViewById(R.id.toolbarNoteDetail);
//        title = findViewById(R.id.noteDetailTitle);
//        content = findViewById(R.id.noteDetailContent);
//        titleET = findViewById(R.id.noteDetailTitleET);
//        contentET = findViewById(R.id.noteDetailContentET);
//        delete = findViewById(R.id.delete);
//        edit = findViewById(R.id.edit);
//        share = findViewById(R.id.share);
//        location = findViewById(R.id.noteDetail_location);
    }
}