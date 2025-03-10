package com.malikyasir.notes_taking_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String name, String desc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.SUBJECT, name);
        contentValues.put(DatabaseHelper.DESC, desc);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValues);
    }

    public Cursor fetch() {
        String[] columns = new String[]{DatabaseHelper.ID_COL + " AS _id", DatabaseHelper.SUBJECT, DatabaseHelper.DESC};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long id, String name, String desc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.SUBJECT, name);
        contentValues.put(DatabaseHelper.DESC, desc);
        return database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper.ID_COL + " = ?", new String[]{String.valueOf(id)});
    }

    public void delete(long id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.ID_COL + " = ?", new String[]{String.valueOf(id)});
    }
}
