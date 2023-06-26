package com.example.api.ramsha.mynotes.data;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.api.ramsha.mynotes.model.NotesDataModel;
import com.example.api.ramsha.mynotes.model.UserModel;

import java.util.ArrayList;

public class DatabaseStorage extends SQLiteOpenHelper {

    public static final String USER_ID = "UserId";
    public static final String USER_NAME = "UserName";
    public static final String USER_EMAIL = "UserEmail";
    public static final String USER_GENDER = "UserGender";
    public static final String USER_DOB = "UserDob";
    public static final String USER_TABLE = "UserTable";
    public static final String NOTE_ID = "NoteId";

    public static final String NOTE_TITLE = "NoteTitle";

    public static final String NOTE_CONTENT = "NoteContent";

    public static final String NOTE_DATE = "NoteDate";

    public static final String NOTE_TABLE = "NoteTable";


    public DatabaseStorage(@Nullable Context context) {
        super(context, "MyNotesDB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableStatement = "CREATE TABLE " + USER_TABLE + "(" +
                USER_ID + " Integer PRIMARY KEY AUTOINCREMENT, " +
                USER_NAME + " Text, " +
                USER_EMAIL + " Text," +
                USER_DOB + " Text," +
                USER_GENDER + " Text ) ";
        String NoteTable = "CREATE TABLE " + NOTE_TABLE + "(" +
                NOTE_ID + " INTEGER, " +
                NOTE_TITLE + " TEXT, " +
                NOTE_CONTENT + " TEXT, " +
                NOTE_DATE + " TEXT )";
        db.execSQL(createTableStatement);
        db.execSQL(NoteTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        onCreate(db);
    }

    public void addUser(UserModel user_model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USER_NAME, user_model.getName());
        cv.put(USER_EMAIL, user_model.getEmail());
        cv.put(USER_DOB, user_model.getDob());
        cv.put(USER_GENDER, user_model.getGender());
        db.insert(USER_TABLE, null, cv);
        db.close();
    }

    public void addNote(NotesDataModel note_model, String email) {

        int id = getUserID(email);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NOTE_ID, id);
        cv.put(NOTE_TITLE, note_model.getHeading());
        cv.put(NOTE_CONTENT, note_model.getText());
        cv.put(NOTE_DATE, note_model.getDate());
        db.insert(NOTE_TABLE, null, cv);
        db.close();

    }

    public void updateUser(UserModel userModel, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        String updateQuery = "UPDATE " + USER_TABLE + " SET " + USER_NAME + " = '" +
                userModel.getName() + "' , " + USER_EMAIL + " = '" + userModel.getEmail() + "' , "
                + USER_GENDER + " = '" + userModel.getGender() + "'," + USER_DOB + "='" + userModel.getDob() + "'" + " WHERE " + USER_EMAIL + " = '" + email + "'";
        db.execSQL(updateQuery);
        db.close();
    }

    public int getUserID(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        int id = 0;
        Cursor cursorNotes = db.rawQuery("SELECT * FROM " + USER_TABLE +
                " WHERE " + USER_EMAIL + " = '" + email + "'", null);
        if (cursorNotes.moveToFirst()) {
            do {
                id = cursorNotes.getInt(0);
            } while (cursorNotes.moveToNext());
        }
        cursorNotes.close();
        return id;
    }

    public ArrayList<NotesDataModel> getAllNotes(String email) {
        int id = getUserID(email);
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<NotesDataModel> list = new ArrayList<>();
        Cursor cursorNotes = db.rawQuery("SELECT * FROM " + NOTE_TABLE +
                        " WHERE " +
                        NOTE_ID + "='" + id + "'"
                , null);
        if (cursorNotes.moveToFirst()) {
            do {
                NotesDataModel note = new NotesDataModel(cursorNotes.getString(1),
                        cursorNotes.getString(2),
                        cursorNotes.getString(3));
                list.add(note);

            } while (cursorNotes.moveToNext());
        }
        cursorNotes.close();
        return list;
    }
public void deleteNote(String title,String content,String date,String email){
//        int id = getUserID(email);
//
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        String deleteQuery = "DELETE FROM " + NOTE_TABLE +
//                " WHERE " +
//                NOTE_ID + " = " + id+ " AND "+
//                NOTE_TITLE + " =' " + title + " ' "+ " AND "+
//                NOTE_CONTENT + " =' " + content + " ' "+ "AND "+
//                NOTE_DATE + " =' " + date+" ' " ;
//        db.execSQL(deleteQuery);
//        db.close();



    int id = getUserID(email);

    SQLiteDatabase db = this.getWritableDatabase();

    String whereClause = NOTE_ID + " = ? AND " +
            NOTE_TITLE + " = ? AND " +
            NOTE_CONTENT + " = ? AND " +
            NOTE_DATE + " = ?";
    String[] whereArgs = {String.valueOf(id), title, content, date};

    int rowsAffected = db.delete(NOTE_TABLE, whereClause, whereArgs);
    db.close();

    if (rowsAffected > 0) {
        // Deletion successful
        Log.d(TAG, "Note deleted successfully");
    } else {
        // Deletion failed
        Log.d(TAG, "Failed to delete note");
    }




}
public void updateNote(NotesDataModel prevData,NotesDataModel updatedData,String email){
    int id = getUserID(email);
    SQLiteDatabase db = this.getWritableDatabase();
    String updateQuery = "UPDATE " + NOTE_TABLE +
            " SET " +
            NOTE_TITLE + " = '" + updatedData.getHeading() + "' , " +
            NOTE_CONTENT + " = '" + updatedData.getText() + "' , " +
            NOTE_DATE + " = '" + updatedData.getDate() + "' " +

            " WHERE " +
            NOTE_TITLE + " = '" + prevData.getHeading() + "' " +"AND "+
            NOTE_CONTENT + " = '" + prevData.getText() + "' " +"AND "+
            NOTE_DATE + " = '" + prevData.getDate() + "' " +"AND "+
            NOTE_ID + " = " + id ;
    db.execSQL(updateQuery);
    db.close();
};

}
