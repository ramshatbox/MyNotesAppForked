package com.example.api.ramsha.mynotes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.api.ramsha.mynotes.MyNotes;
import com.example.api.ramsha.mynotes.R;
import com.example.api.ramsha.mynotes.adapter.NotesAdapter;
import com.example.api.ramsha.mynotes.model.NotesDataModel;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {
    SearchView searchView;
    Toolbar toolbar;
    ListView listView;
    DrawerLayout drawerLayout;
    ArrayList<NotesDataModel> notesList;
    BottomAppBar bottomAppBar;
    FloatingActionButton fab;
    ImageButton drawerBackBtn;
    ImageView drawerProfile;
    TextView drawerUserName;
    NavigationView navigationView;
    MyNotes myNotes;
    View headerView;
    NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FirebaseCrashlytics.getInstance().log("onCreate");

        myNotes = MyNotes.getInstance();
        findViews();

        listeners();
        searchViewSetUp();
        toolbarSetUp();
        addNotesData();
        adapterSetUp();
        bottomAppBarSetup();
        navigationViewSetup();


    }

    private void listeners() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Home.this, AddNewNote.class);
                startActivity(i);

//                FirebaseCrashlytics.getInstance().log("Home Page");
//                FirebaseCrashlytics.getInstance().log("Forced Crash");
//
//                throw new RuntimeException("Test Crash");

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NotesDataModel clickedNote = (NotesDataModel) adapter.getItem(position);

                Intent intent = new Intent(Home.this, NoteDetails.class);

                intent.putExtra("note_date", clickedNote.getDate());
                intent.putExtra("note_title", clickedNote.getHeading());
                intent.putExtra("note_content", clickedNote.getText());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isOpen()) {
            drawerLayout.close();
        } else {
            super.onBackPressed();
        }
    }

    private void navigationViewSetup() {
        headerView = navigationView.getHeaderView(0);
        drawerBackBtn = headerView.findViewById(R.id.drawerBackArrow);
        drawerProfile = headerView.findViewById(R.id.drawerUserProfilePicture);
        drawerUserName = headerView.findViewById(R.id.drawerUserName);
        List<String> data = myNotes.getVariableValues();
        String updatedProfile = data.get(0);
        String name = data.get(1);
        Glide.with(Home.this).load(updatedProfile).into(drawerProfile);
        Toast.makeText(myNotes, "" + updatedProfile, Toast.LENGTH_SHORT).show();
        drawerUserName.setText(name);
        drawerBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.close();
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int menuItemid = item.getItemId();
                if (menuItemid == R.id.drawerNewNote) {
                    drawerLayout.close();

                    Toast.makeText(Home.this, "New Note", Toast.LENGTH_SHORT).show();
                } else if (menuItemid == R.id.drawerFav) {
                    drawerLayout.close();
                    Toast.makeText(Home.this, "Favourites", Toast.LENGTH_SHORT).show();
                } else if (menuItemid == R.id.drawerAllNote) {
                    drawerLayout.close();
                    Toast.makeText(Home.this, "All Notes", Toast.LENGTH_SHORT).show();
                } else if (menuItemid == R.id.drawerNoti) {
                    drawerLayout.close();
                    Toast.makeText(Home.this, "Notification", Toast.LENGTH_SHORT).show();
                } else if (menuItemid == R.id.drawerUpdate) {
                    drawerLayout.close();
                    Intent i = new Intent(Home.this, UpdateProfile.class);
                    startActivity(i);
                }
                return false;
            }

        });
    }


    private void bottomAppBarSetup() {
        bottomAppBar.setFabCradleRoundedCornerRadius(0);


        MaterialShapeDrawable bottomBarBackground = (MaterialShapeDrawable) bottomAppBar.getBackground();
        bottomBarBackground.setShapeAppearanceModel(bottomBarBackground.getShapeAppearanceModel().toBuilder().setTopRightCorner(CornerFamily.ROUNDED, 80).setTopLeftCorner(CornerFamily.ROUNDED, 80).build());

    }

    private void adapterSetUp() {
         adapter = new NotesAdapter(Home.this, notesList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

    private void addNotesData() {
        notesList=myNotes.getDb().getAllNotes(myNotes.getEmail());
//        notesList.add(new NotesDataModel("ANY DOC", getString(R.string.defaultText), "03-06-2023"));
//        notesList.add(new NotesDataModel("ANY DOC", getString(R.string.defaultText), "03-06-2023"));
//        notesList.add(new NotesDataModel("ANY DOC", getString(R.string.defaultText), "03-06-2023"));
//        notesList.add(new NotesDataModel("ANY DOC", getString(R.string.defaultText), "03-06-2023"));
//        notesList.add(new NotesDataModel("ANY DOC", getString(R.string.defaultText), "03-06-2023"));
//        notesList.add(new NotesDataModel("ANY DOC", getString(R.string.defaultText), "03-06-2023"));
//        notesList.add(new NotesDataModel("ANY DOC", getString(R.string.defaultText), "03-06-2023"));
//        notesList.add(new NotesDataModel("ANY DOC", getString(R.string.defaultText), "03-06-2023"));
//        notesList.add(new NotesDataModel("ANY DOC", getString(R.string.defaultText), "03-06-2023"));
//        notesList.add(new NotesDataModel("ANY DOC", getString(R.string.defaultText), "03-06-2023"));
//        notesList.add(new NotesDataModel("ANY DOC", getString(R.string.defaultText), "03-06-2023"));
//        notesList.add(new NotesDataModel("ANY DOC", getString(R.string.defaultText), "03-06-2023"));
//        notesList.add(new NotesDataModel("ANY DOC", getString(R.string.defaultText), "03-06-2023"));
//        notesList.add(new NotesDataModel("ANY DOC", getString(R.string.defaultText), "03-06-2023"));
//        notesList.add(new NotesDataModel("ANY DOC", getString(R.string.defaultText), "03-06-2023"));
//        notesList.add(new NotesDataModel("ANY DOC", getString(R.string.defaultText), "03-06-2023"));
    }

    private void toolbarSetUp() {
        toolbar.setNavigationIcon(R.drawable.menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.open();
            }
        });
    }

    private void searchViewSetUp() {

    }

    private void findViews() {
        FirebaseCrashlytics.getInstance().log("inFindViews");

        searchView = (SearchView) findViewById(R.id.searchHomePage);
        toolbar = (Toolbar) findViewById(R.id.toolbarHomePage);
        listView = (ListView) findViewById(R.id.listViewHomePage);
        drawerLayout = findViewById(R.id.drawer);
        bottomAppBar = findViewById(R.id.bottomAppBar);
        fab = findViewById(R.id.fab);
        navigationView = findViewById(R.id.Navigation_menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<String> data = myNotes.getVariableValues();
        String updatedProfile = data.get(0);
        String name = data.get(1);

        Glide.with(Home.this).load(updatedProfile).into(drawerProfile);
        addNotesData();
        adapterSetUp();
    }
}