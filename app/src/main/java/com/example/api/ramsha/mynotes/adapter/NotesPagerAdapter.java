package com.example.api.ramsha.mynotes.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.PagerAdapter;

import com.example.api.ramsha.mynotes.MyNotes;
import com.example.api.ramsha.mynotes.notification.Notification;
import com.example.api.ramsha.mynotes.R;
import com.example.api.ramsha.mynotes.model.NotesDataModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import kotlin.reflect.KVisibility;

public class NotesPagerAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<NotesDataModel> data;
    MyNotes myNotes = MyNotes.getInstance();
    Notification obj = new Notification();

    public NotesPagerAdapter(Context context, ArrayList<NotesDataModel> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        NotesDataModel noteData = data.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_notes_pager, container, false);
        Button btn = view.findViewById(R.id.noteDetailDoneBtn);
        Button updateBtn = view.findViewById(R.id.noteDetailUpdateBtn);
        TextView title = view.findViewById(R.id.noteDetailTitle);
        TextView content = view.findViewById(R.id.noteDetailContent);
        EditText titleET = view.findViewById(R.id.noteDetailTitleET);
        EditText contentET = view.findViewById(R.id.noteDetailContentET);
        ImageButton delete = view.findViewById(R.id.delete);
        ImageButton edit = view.findViewById(R.id.edit);
        ImageButton share = view.findViewById(R.id.share);
        ImageButton location = view.findViewById(R.id.noteDetail_location);
        double[] latlong = noteData.getLocation();
        if (latlong[0] == 0.0 && latlong[1] == 0.0)
            location.setVisibility(View.INVISIBLE);
        else
            location.setVisibility(View.VISIBLE);
        title.setText(noteData.getHeading());
        content.setText(noteData.getText());
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "geo:" + latlong[0] + "," + latlong[1] + "?q=" + latlong[0] + "," + latlong[1];
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                context.startActivity(intent);
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Title: " + noteData.getHeading() + "\n" + "Content: " + noteData.getText());
                context.startActivity(Intent.createChooser(shareIntent, noteData.getHeading()));
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
                titleET.setText(noteData.getHeading());
                contentET.setText(noteData.getText());
                location.setVisibility(View.GONE);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setMessage("Delete this item?");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        myNotes.getDb().deleteNote(noteData.getHeading(), noteData.getText(), noteData.getDate(), myNotes.getEmail());
                        Toast.makeText(context, "Note deleted", Toast.LENGTH_SHORT).show();
                        data.remove(position);
                        notifyDataSetChanged();
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
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) context).finish();
            }
        });
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotesDataModel prevData = new NotesDataModel(noteData.getHeading(), noteData.getText(), noteData.getDate(), noteData.getLocation());
                String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                NotesDataModel updatedData = new NotesDataModel(titleET.getText().toString(), contentET.getText().toString(), date,  noteData.getLocation());
                myNotes.getDb().updateNote(prevData, updatedData, myNotes.getEmail());
                Toast.makeText(context, "Note Updated", Toast.LENGTH_SHORT).show();
                obj.createNotificationChannel(context,"noteupdate","Note Updated");
                obj.showNotification(context, "Note Updated");
                title.setText(titleET.getText().toString());
                content.setText(contentET.getText().toString());
                titleET.setVisibility(View.GONE);
                contentET.setVisibility(View.GONE);
                updateBtn.setVisibility(View.GONE);
                title.setVisibility(View.VISIBLE);
                content.setVisibility(View.VISIBLE);
                btn.setVisibility(View.VISIBLE);
                location.setVisibility(View.VISIBLE);
            }
        });
        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
