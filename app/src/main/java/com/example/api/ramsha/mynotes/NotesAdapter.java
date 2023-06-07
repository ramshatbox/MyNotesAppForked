package com.example.api.ramsha.mynotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class NotesAdapter extends ArrayAdapter<NotesDataModel> {


    public NotesAdapter(@NonNull Context context, ArrayList<NotesDataModel> notesList) {
        super(context,R.layout.item_notes_list ,notesList);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        NotesDataModel list = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_notes_list, parent, false);

        TextView heading = convertView.findViewById(R.id.noteHeading);
        TextView content = convertView.findViewById(R.id.noteContent);
        TextView date = convertView.findViewById(R.id.noteDate);

        heading.setText(list.getHeading());
        content.setText(list.getText());
        date.setText(list.getDate());
        return convertView;

    }
}
