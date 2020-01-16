package com.example.unitipsnew;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.unitipsnew.Utente.Utente;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "uniTipsDB";

    // Table Names
    private static final String TABLE_USER = "utente";

    // USER Table - column names
    private static final String KEY_MATRICOLA = "matricola";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_NOME = "nome";
    private static final String KEY_COGNOME = "cognome";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_AVATAR = "avatar";

    // Table Create Statements
    // USER table create statement
    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "(" +
            KEY_MATRICOLA + " LONG PRIMARY KEY," +
            KEY_EMAIL + " TEXT," +
            KEY_NOME + " TEXT," +
            KEY_COGNOME + " TEXT," +
            KEY_PASSWORD + " TEXT," +
            KEY_AVATAR + " INTEGER" + ")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_USER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // create new tables
        onCreate(db);
    }

    /*
     * Creating a user
     */
    public long createUser(Utente user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MATRICOLA, user.getMatricola());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_NOME, user.getNome());
        values.put(KEY_COGNOME, user.getCognome());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_AVATAR, user.getImmagine());

        // insert row
        long user_id = db.insert(TABLE_USER, null, values);

        /* assigning tags to todo
        for (long tag_id : tag_ids) {
            createTodoTag(todo_id, tag_id);
        }*/

        return user_id;
    }

    /*
     * Updating a user
     */
    public int updateUser(Utente user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MATRICOLA, user.getMatricola());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_NOME, user.getNome());
        values.put(KEY_COGNOME, user.getCognome());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_AVATAR, user.getImmagine());

        // updating row
        return db.update(TABLE_USER, values, KEY_MATRICOLA + " = ?",
                new String[] { String.valueOf(user.getMatricola()) });
    }

    /*
     * Deleting a user
     */
    public void deleteUser(long user_matricola) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, KEY_MATRICOLA + " = ?",
                new String[] { String.valueOf(user_matricola) });
    }

    /*
     * get single user
     */
    public Utente getUser(double matricola) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE "
                + KEY_MATRICOLA + " = " + matricola;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Utente u = new Utente();
        u.setMatricola(c.getLong(c.getColumnIndex(KEY_MATRICOLA)));
        u.setEmail(c.getString(c.getColumnIndex(KEY_EMAIL)));
        u.setNome(c.getString(c.getColumnIndex(KEY_NOME)));
        u.setCognome(c.getString(c.getColumnIndex(KEY_COGNOME)));
        u.setPassword(c.getString(c.getColumnIndex(KEY_PASSWORD)));
        u.setImmagine(c.getInt(c.getColumnIndex(KEY_AVATAR)));

        return u;
    }

    /*
     * getting all users
     * */
    public List<Utente> getAllUsers() {
        List<Utente> users = new ArrayList<Utente>();

        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Utente u = new Utente();
                u.setMatricola(c.getLong(c.getColumnIndex(KEY_MATRICOLA)));
                u.setEmail(c.getString(c.getColumnIndex(KEY_EMAIL)));
                u.setNome(c.getString(c.getColumnIndex(KEY_NOME)));
                u.setCognome(c.getString(c.getColumnIndex(KEY_COGNOME)));
                u.setPassword(c.getString(c.getColumnIndex(KEY_PASSWORD)));

                // adding to users list
                users.add(u);
            } while (c.moveToNext());
        }

        return users;
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
