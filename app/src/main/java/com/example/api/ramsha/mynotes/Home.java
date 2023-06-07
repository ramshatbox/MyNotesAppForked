package com.example.api.ramsha.mynotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.MaterialShapeDrawable;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    SearchView searchView;
    Toolbar toolbar;
    ListView listView;
    DrawerLayout drawerLayout;
    ArrayList<NotesDataModel> notesList = new ArrayList<NotesDataModel>();
    BottomAppBar bottomAppBar;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViews();
        searchViewSetUp();
        toolbarSetUp();
        addNotesData();
        adapterSetUp();
        bottomAppBarSetup();
    }


    private void bottomAppBarSetup() {
        bottomAppBar.setFabCradleRoundedCornerRadius(0);


        MaterialShapeDrawable bottomBarBackground = (MaterialShapeDrawable) bottomAppBar.getBackground();
        bottomBarBackground.setShapeAppearanceModel(bottomBarBackground.getShapeAppearanceModel().toBuilder().setTopRightCorner(CornerFamily.ROUNDED, 80).setTopLeftCorner(CornerFamily.ROUNDED, 80).build());

    }

    private void adapterSetUp() {
        NotesAdapter adapter = new NotesAdapter(Home.this, notesList);
        listView.setAdapter(adapter);
    }

    private void addNotesData() {
        notesList.add(new NotesDataModel("ANY DOC", getString(R.string.defaultText), "03-06-2023"));
        notesList.add(new NotesDataModel("ANY DOC", getString(R.string.defaultText), "03-06-2023"));
        notesList.add(new NotesDataModel("ANY DOC", getString(R.string.defaultText), "03-06-2023"));
        notesList.add(new NotesDataModel("ANY DOC", getString(R.string.defaultText), "03-06-2023"));
        notesList.add(new NotesDataModel("ANY DOC", getString(R.string.defaultText), "03-06-2023"));
        notesList.add(new NotesDataModel("ANY DOC", getString(R.string.defaultText), "03-06-2023"));
        notesList.add(new NotesDataModel("ANY DOC", getString(R.string.defaultText), "03-06-2023"));
        notesList.add(new NotesDataModel("ANY DOC", getString(R.string.defaultText), "03-06-2023"));
        notesList.add(new NotesDataModel("ANY DOC", getString(R.string.defaultText), "03-06-2023"));
        notesList.add(new NotesDataModel("ANY DOC", getString(R.string.defaultText), "03-06-2023"));
        notesList.add(new NotesDataModel("ANY DOC", getString(R.string.defaultText), "03-06-2023"));
        notesList.add(new NotesDataModel("ANY DOC", getString(R.string.defaultText), "03-06-2023"));
        notesList.add(new NotesDataModel("ANY DOC", getString(R.string.defaultText), "03-06-2023"));
        notesList.add(new NotesDataModel("ANY DOC", getString(R.string.defaultText), "03-06-2023"));
        notesList.add(new NotesDataModel("ANY DOC", getString(R.string.defaultText), "03-06-2023"));
        notesList.add(new NotesDataModel("ANY DOC", getString(R.string.defaultText), "03-06-2023"));


    }

    private void toolbarSetUp() {
        toolbar.setNavigationIcon(R.drawable.menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void searchViewSetUp() {

    }

    private void findViews() {
        searchView = (SearchView) findViewById(R.id.searchHomePage);
        toolbar = (Toolbar) findViewById(R.id.toolbarHomePage);
        listView = (ListView) findViewById(R.id.listViewHomePage);
        drawerLayout = findViewById(R.id.drawer);
        bottomAppBar = findViewById(R.id.bottomAppBar);
        fab = findViewById(R.id.fab);
    }
}