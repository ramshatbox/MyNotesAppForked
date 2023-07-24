package com.example.api.ramsha.mynotes;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.api.ramsha.mynotes.data.DatabaseStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MyNotes extends Application {
    private static MyNotes instance;
    private SharedPreferences sharedPreferences;
    private String mimagePath = "";
    private boolean notification = false;

    private String mname = "";

    private String memail = "";

    private String mpassword = "";
    private String mdob = "";
    private String mgender = "";
    private DatabaseStorage db;

    @Override
    public void onCreate() {
        super.onCreate();
        db = new DatabaseStorage(getApplicationContext());
        instance = this;
        sharedPreferences = getSharedPreferences("MyNotesPrefs", Context.MODE_PRIVATE);
        mimagePath = sharedPreferences.getString("ImagePath", "");
        mname = sharedPreferences.getString("Name", "");
        memail = sharedPreferences.getString("Email", "");
        mpassword = sharedPreferences.getString("Password", "");
        mdob = sharedPreferences.getString("Dob", "");
        mgender = sharedPreferences.getString("Gender", "");
        notification = sharedPreferences.getBoolean("notification", false);
    }

    public DatabaseStorage getDb() {
        return db;
    }

    public static MyNotes getInstance() {
        return instance;
    }

    public void updateVariables(String path, String name, String pass, String email, String dob, String gender) {
        //retain values which are not updated and update value added
        if (!path.equals(""))
            mimagePath = path;
        else
            sharedPreferences.getString("ImagePath", "");

        if (!name.equals(""))
            mname = name;
        else
            sharedPreferences.getString("Name", "");

        if (!email.equals(""))
            memail = email;
        else
            sharedPreferences.getString("Email", "");

        if (!pass.equals(""))
            mpassword = pass;
        else
            sharedPreferences.getString("Password", "");

        if (!gender.equals(""))
            mgender = gender;
        else
            sharedPreferences.getString("Gender", "");

        if (!dob.equals(""))
            mdob = dob;
        else
            sharedPreferences.getString("Dob", "");

        // Save the updated value in SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ImagePath", mimagePath);
        editor.putString("Name", mname);
        editor.putString("Email", memail);
        editor.putString("Password", mpassword);
        editor.putString("Dob", mdob);
        editor.putString("Gender", mgender);

        editor.apply();
    }

    public void setnotification(boolean notification) {
        this.notification = notification;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("notification", this.notification);
        editor.apply();
    }
    public boolean notificationState(){
        return this.notification;
    }

    public List<String> getVariableValues() {
        ArrayList<String> data = new ArrayList<>();
        data.add(mimagePath);
        data.add(mname);
        data.add(memail);
        data.add(mpassword);
        data.add(mdob);
        data.add(mgender);

        return data;
    }

    public String getEmail() {
        return memail;
    }
}
