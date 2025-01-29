package com.example.project_db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "comentarios.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "comentarios";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "titulo";
    private static final String COLUMN_TEXT = "texto";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER " +
                        "PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITLE + " TEXT" +
                        " NOT NULL, " + COLUMN_TEXT + " TEXT NOT NULL);";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void agregarComentario(String titulo, String texto) {
        SQLiteDatabase db = this .getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, titulo);
        values.put(COLUMN_TEXT, texto);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void eliminarComentario(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + "=?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public ArrayList<Comentario> obtenerComentarios() {
        ArrayList<Comentario> comentarios = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                comentarios.add(new Comentario(cursor.getInt(0),
                        cursor.getString(1), cursor.getString(2)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return comentarios;
    }
}
