package com.example.api.ramsha.mynotes.data;

import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;

import java.util.Locale;

public class FileStorage {
    private static final String FILE_NAME = "UserData.txt";
    private static final String path = "/data/data/com.example.api.ramsha.mynotes/files";
public static void SaveData(String name,String email,String dob,String gender){
    String logTime = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    String logMessage = "SignUp Time: "+logTime + "\n" + "Name: " + name + "\nEmail: " + email + "\nDoB: " + dob+ "\nGender: " + gender+"\n"  ;
    try {
        File logFile = new File(path, FILE_NAME);
        FileOutputStream fileOutputStream = new FileOutputStream(logFile, true);
        OutputStreamWriter streamWriter = new OutputStreamWriter(fileOutputStream);
        streamWriter.append(logMessage);
        streamWriter.close();
    } catch (IOException e) {
        Log.e("", "Error saving log to file: " + e.getMessage());
    } catch (Exception e) {
        Log.e("TAG", "Exception: " + e.getMessage());
    }

}

}
