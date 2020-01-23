package com.example.unitipsnew;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.unitipsnew.Eventi.Evento;
import com.example.unitipsnew.Recensioni.Corso;
import com.example.unitipsnew.Recensioni.Recensione;
import com.example.unitipsnew.Utente.Utente;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    private static final String TABLE_CORSO = "corso";
    private static final String TABLE_RECENSIONE = "recensione";
    private static final String TABLE_EVENTO = "evento";

    // USER Table - column names
    private static final String KEY_MATRICOLA = "matricola";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_NOME = "nome";
    private static final String KEY_COGNOME = "cognome";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_AVATAR = "avatar";

    // CORSO Table - column names
    private static final String KEY_ID_CORSO = "id";
    private static final String KEY_NOME_CORSO = "nome";
    private static final String KEY_PROFESSORE = "professore";
    private static final String KEY_RECENSIONI = "recensioni";

    // RECENSIONE Table - column names
    private static final String KEY_ID_RECENSIONE = "id";
    private static final String KEY_ID_CORSO_RECENSIONE = "idCorso";
    private static final String KEY_MATRICOLA_RECENSIONE = "matricola";
    private static final String KEY_TITOLO_RECENSIONE = "titolo";
    private static final String KEY_TESTO_RECENSIONE = "testo";
    private static final String KEY_DATA_RECENSIONE = "data";

    // EVENTO Table - column names
    private static final String KEY_ID_EVENTO = "id";
    private static final String KEY_IMMAGINE_EVENTO = "immagine";
    private static final String KEY_INTERESSATI = "interessati";
    private static final String KEY_TITOLO_EVENTO = "titolo";
    private static final String KEY_DESCRIZIONE_EVENTO = "descrizione";
    private static final String KEY_LUOGO_EVENTO = "luogo";
    private static final String KEY_DATA_EVENTO = "data";

    // Table Create Statements
    // USER table create statement
    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "(" +
            KEY_MATRICOLA + " LONG PRIMARY KEY," +
            KEY_EMAIL + " TEXT," +
            KEY_NOME + " TEXT," +
            KEY_COGNOME + " TEXT," +
            KEY_PASSWORD + " TEXT," +
            KEY_AVATAR + " INTEGER" + ")";

    // CORSO table create statement
    private static final String CREATE_TABLE_CORSO = "CREATE TABLE " + TABLE_CORSO + "(" +
            KEY_ID_CORSO + " LONG PRIMARY KEY," +
            KEY_NOME_CORSO + " TEXT," +
            KEY_PROFESSORE + " TEXT," +
            KEY_RECENSIONI + " INT" + ")";

    // RECENSIONE table create statement
    private static final String CREATE_TABLE_RECENSIONE = "CREATE TABLE " + TABLE_RECENSIONE + "(" +
            KEY_ID_RECENSIONE + " LONG PRIMARY KEY," +
            KEY_ID_CORSO_RECENSIONE + " LONG," +
            KEY_MATRICOLA_RECENSIONE + " LONG," +
            KEY_TITOLO_RECENSIONE + " TEXT," +
            KEY_TESTO_RECENSIONE + " TEXT," +
            KEY_DATA_RECENSIONE + " DATE" + ")";

    // EVENTO table create statement
    private static final String CREATE_TABLE_EVENTO = "CREATE TABLE " + TABLE_EVENTO + "(" +
            KEY_ID_EVENTO + " INT PRIMARY KEY," +
            KEY_IMMAGINE_EVENTO + " INT," +
            KEY_INTERESSATI + " INT," +
            KEY_TITOLO_EVENTO + " TEXT," +
            KEY_DESCRIZIONE_EVENTO + " TEXT," +
            KEY_LUOGO_EVENTO + " TEXT," +
            KEY_DATA_EVENTO + " DATE" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_CORSO);
        db.execSQL(CREATE_TABLE_RECENSIONE);
        db.execSQL(CREATE_TABLE_EVENTO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CORSO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECENSIONE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTO);

        // create new tables
        onCreate(db);
    }

/*##################################################################################################
                                            USER
##################################################################################################*/

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
                new String[]{String.valueOf(user.getMatricola())});
    }

    /*
     * Deleting a user
     */
    public void deleteUser(long user_matricola) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, KEY_MATRICOLA + " = ?",
                new String[]{String.valueOf(user_matricola)});
    }

    /*
     * get single user
     */
    public Utente getUser(double matricola) {

        try {
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
        } catch (Exception e) {
            return null;
        }
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

/*##################################################################################################
                                            CORSO
##################################################################################################*/

    /*
     * Creating a corso
     */
    public long createCorso(Corso corso) {
        SQLiteDatabase db = this.getWritableDatabase();

        long lastId = 0;
        if (!getAllCorsi().isEmpty()) {
            lastId = getAllCorsi().get(getAllCorsi().size() - 1).getId();
        }

        ContentValues values = new ContentValues();
        values.put(KEY_ID_CORSO, lastId + 1);
        values.put(KEY_NOME_CORSO, corso.getNomeCorso());
        values.put(KEY_PROFESSORE, corso.getNomeProfessore());
        values.put(KEY_RECENSIONI, corso.getNumeroRecensioni());

        // insert row
        long corso_id = db.insert(TABLE_CORSO, null, values);

        return corso_id;
    }

    /*
     * get single corso
     */
    public Corso getCorso(long corso_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_CORSO + " WHERE "
                + KEY_ID_CORSO + " = " + corso_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Corso corso = new Corso();
        corso.setId(c.getLong(c.getColumnIndex(KEY_ID_CORSO)));
        corso.setNomeCorso(c.getString(c.getColumnIndex(KEY_NOME_CORSO)));
        corso.setNomeProfessore(c.getString(c.getColumnIndex(KEY_PROFESSORE)));
        corso.setNumeroRecensioni(c.getLong(c.getColumnIndex(KEY_RECENSIONI)));

        return corso;
    }

    /*
     * getting all corso
     * */
    public List<Corso> getAllCorsi() {
        List<Corso> corsi = new ArrayList<Corso>();
        String selectQuery = "SELECT  * FROM " + TABLE_CORSO;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Corso corso = new Corso();
                corso.setId(c.getLong(c.getColumnIndex(KEY_ID_CORSO)));
                corso.setNomeCorso(c.getString(c.getColumnIndex(KEY_NOME_CORSO)));
                corso.setNomeProfessore(c.getString(c.getColumnIndex(KEY_PROFESSORE)));
                corso.setNumeroRecensioni(c.getLong(c.getColumnIndex(KEY_RECENSIONI)));

                // adding to corso list
                corsi.add(corso);
            } while (c.moveToNext());
        }

        return corsi;
    }

    /*
     * Updating a corso
     */
    public int updateCorso(Corso corso) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_CORSO, corso.getId());
        values.put(KEY_NOME_CORSO, corso.getNomeCorso());
        values.put(KEY_PROFESSORE, corso.getNomeProfessore());
        values.put(KEY_RECENSIONI, corso.getNumeroRecensioni());

        // updating row
        return db.update(TABLE_CORSO, values, KEY_ID_CORSO + " = ?",
                new String[]{String.valueOf(corso.getId())});
    }

    /*
     * Deleting a corso
     */
    public void deleteCorso(long corso_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        // before deleting corso
        // check if recensioni under this corso should also be deleted

        // get all todos under this tag
        List<Recensione> allRecensioniOfCorso = getAllRecensionesByCorso(corso_id);

        // delete all todos
        for (Recensione recensione : allRecensioniOfCorso) {
            // delete recensione
            deleteRecensione(recensione.getId_recensione());
        }

        // now delete the tag
        db.delete(TABLE_CORSO, KEY_ID_CORSO + " = ?",
                new String[]{String.valueOf(corso_id)});
    }

/*##################################################################################################
                                            RECENSIONE
##################################################################################################*/

    /*
     * Creating a recensione
     */
    public long createRecensione(Recensione recensione) {
        SQLiteDatabase db = this.getWritableDatabase();

        long lastId = 0;
        if (!getAllRecensioni().isEmpty()) {
            lastId = getAllRecensioni().get(getAllRecensioni().size() - 1).getId_recensione();
        }
        ContentValues values = new ContentValues();
        values.put(KEY_ID_RECENSIONE, lastId + 1);
        values.put(KEY_ID_CORSO_RECENSIONE, recensione.getId_corso());
        values.put(KEY_MATRICOLA_RECENSIONE, recensione.getMatricola());
        values.put(KEY_TITOLO_RECENSIONE, recensione.getTitle());
        values.put(KEY_TESTO_RECENSIONE, recensione.getText());
        values.put(KEY_DATA_RECENSIONE, getDateTime());

        // insert row
        long recensione_id = db.insert(TABLE_RECENSIONE, null, values);

        Corso c = getCorso(recensione.getId_corso());
        c.setNumeroRecensioni(c.getNumeroRecensioni() + 1);
        updateCorso(c);

        return recensione_id;
    }

    /*
     * get single recensione
     */
    public Recensione getRecensione(long recensione_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_RECENSIONE + " WHERE "
                + KEY_ID_RECENSIONE + " = " + recensione_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Recensione recensione = new Recensione();
        recensione.setId_recensione(c.getLong(c.getColumnIndex(KEY_ID_RECENSIONE)));
        recensione.setId_corso(c.getLong(c.getColumnIndex(KEY_ID_CORSO)));
        recensione.setMatricola(c.getLong(c.getColumnIndex(KEY_MATRICOLA_RECENSIONE)));
        recensione.setTitle((c.getString(c.getColumnIndex(KEY_TITOLO_RECENSIONE))));
        recensione.setText((c.getString(c.getColumnIndex(KEY_TESTO_RECENSIONE))));
        recensione.setDate((c.getString(c.getColumnIndex(KEY_DATA_RECENSIONE))));

        return recensione;
    }

    /*
     * getting all recensione
     * */
    public List<Recensione> getAllRecensioni() {
        List<Recensione> recensioni = new ArrayList<Recensione>();
        String selectQuery = "SELECT  * FROM " + TABLE_RECENSIONE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Recensione recensione = new Recensione();
                recensione.setId_recensione(c.getLong(c.getColumnIndex(KEY_ID_RECENSIONE)));
                recensione.setId_corso(c.getLong(c.getColumnIndex(KEY_ID_CORSO)));
                recensione.setMatricola(c.getLong(c.getColumnIndex(KEY_MATRICOLA_RECENSIONE)));
                recensione.setTitle((c.getString(c.getColumnIndex(KEY_TITOLO_RECENSIONE))));
                recensione.setText((c.getString(c.getColumnIndex(KEY_TESTO_RECENSIONE))));
                recensione.setDate((c.getString(c.getColumnIndex(KEY_DATA_RECENSIONE))));

                // adding to recensioni list
                recensioni.add(recensione);
            } while (c.moveToNext());
        }

        return recensioni;
    }

    /*
     * getting all recensione under single corso
     * */
    public ArrayList<Recensione> getAllRecensionesByCorso(long corso_id) {
        ArrayList<Recensione> recensioni = new ArrayList<Recensione>();

        String selectQuery = "SELECT  * FROM " + TABLE_RECENSIONE + " WHERE " + KEY_ID_CORSO_RECENSIONE + " = " + corso_id;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Recensione recensione = new Recensione();
                recensione.setId_recensione(c.getLong(c.getColumnIndex(KEY_ID_RECENSIONE)));
                recensione.setId_corso(c.getLong(c.getColumnIndex(KEY_ID_CORSO)));
                recensione.setMatricola(c.getLong(c.getColumnIndex(KEY_MATRICOLA_RECENSIONE)));
                recensione.setTitle((c.getString(c.getColumnIndex(KEY_TITOLO_RECENSIONE))));
                recensione.setText((c.getString(c.getColumnIndex(KEY_TESTO_RECENSIONE))));
                recensione.setDate((c.getString(c.getColumnIndex(KEY_DATA_RECENSIONE))));

                // adding to recensione list
                recensioni.add(recensione);
            } while (c.moveToNext());
        }
        Collections.sort(recensioni, new Comparator<Recensione>() {
            public int compare(Recensione o1, Recensione o2) {
                return (((int) o2.getId_recensione()) - ((int) o1.getId_recensione()));
            }
        });
        return recensioni;
    }

    /*
     * Updating a recensione
     */
    public int updateRecensione(Recensione recensione) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_RECENSIONE, recensione.getId_recensione());
        values.put(KEY_ID_CORSO_RECENSIONE, recensione.getId_corso());
        values.put(KEY_MATRICOLA_RECENSIONE, recensione.getMatricola());
        values.put(KEY_TITOLO_RECENSIONE, recensione.getTitle());
        values.put(KEY_TESTO_RECENSIONE, recensione.getText());
        values.put(KEY_DATA_RECENSIONE, recensione.getDate());

        // updating row
        return db.update(TABLE_RECENSIONE, values, KEY_ID_RECENSIONE + " = ?",
                new String[]{String.valueOf(recensione.getId_recensione())});
    }

    /*
     * Deleting a recensione
     */
    public void deleteRecensione(long recensione_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RECENSIONE, KEY_ID_RECENSIONE + " = ?",
                new String[]{String.valueOf(recensione_id)});
        Corso c = getCorso(getRecensione(recensione_id).getId_corso());
        c.setNumeroRecensioni(c.getNumeroRecensioni() - 1);
        updateCorso(c);
    }

/*##################################################################################################
                                            EVENTO
##################################################################################################*/

    /*
     * Creating a evento
     */
    public long createEvento(Evento evento) {
        SQLiteDatabase db = this.getWritableDatabase();

        long lastId = 0;
        if (!getAllEventi().isEmpty()) {
            lastId = getAllEventi().get(0).getId();
        }
        ContentValues values = new ContentValues();
        values.put(KEY_ID_EVENTO, lastId + 1);
        values.put(KEY_IMMAGINE_EVENTO, evento.getImmagine());
        values.put(KEY_INTERESSATI, 0);
        values.put(KEY_TITOLO_EVENTO, evento.getTitolo());
        values.put(KEY_DESCRIZIONE_EVENTO, evento.getDescrizione());
        values.put(KEY_LUOGO_EVENTO, evento.getLuogo());
        values.put(KEY_DATA_EVENTO, evento.getData());

        // insert row
        long evento_id = db.insert(TABLE_EVENTO, null, values);

        return evento_id;
    }

    /*
     * Updating a evento
     */
    public int updateEvento(Evento evento) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_EVENTO, evento.getId());
        values.put(KEY_IMMAGINE_EVENTO, evento.getImmagine());
        values.put(KEY_INTERESSATI, evento.getInteressati());
        values.put(KEY_TITOLO_EVENTO, evento.getTitolo());
        values.put(KEY_DESCRIZIONE_EVENTO, evento.getDescrizione());
        values.put(KEY_LUOGO_EVENTO, evento.getLuogo());
        values.put(KEY_DATA_EVENTO, evento.getData());

        // updating row
        return db.update(TABLE_EVENTO, values, KEY_ID_EVENTO + " = ?",
                new String[]{String.valueOf(evento.getId())});
    }

    /*
     * Deleting a evento
     */
    public void deleteEvento(int id_evento) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EVENTO, KEY_ID_EVENTO + " = ?",
                new String[]{String.valueOf(id_evento)});
    }

    /*
     * get single evento
     */
    public Evento getEvento(int id_evento) {

        try {
            SQLiteDatabase db = this.getReadableDatabase();

            String selectQuery = "SELECT  * FROM " + TABLE_EVENTO + " WHERE "
                    + KEY_ID_EVENTO + " = " + id_evento;

            Cursor c = db.rawQuery(selectQuery, null);

            if (c != null)
                c.moveToFirst();

            Evento evento = new Evento();
            evento.setId(c.getInt(c.getColumnIndex(KEY_ID_EVENTO)));
            evento.setImmagine(c.getInt(c.getColumnIndex(KEY_IMMAGINE_EVENTO)));
            evento.setInteressati(c.getInt(c.getColumnIndex(KEY_INTERESSATI)));
            evento.setTitolo(c.getString(c.getColumnIndex(KEY_TITOLO_EVENTO)));
            evento.setDescrizione(c.getString(c.getColumnIndex(KEY_DESCRIZIONE_EVENTO)));
            evento.setLuogo(c.getString(c.getColumnIndex(KEY_LUOGO_EVENTO)));
            evento.setData(c.getString(c.getColumnIndex(KEY_DATA_EVENTO)));

            return evento;
        } catch (Exception e) {
            return null;
        }
    }

    /*
     * getting all eventi
     * */
    public List<Evento> getAllEventi() {
        List<Evento> eventi = new ArrayList<Evento>();

        String selectQuery = "SELECT  * FROM " + TABLE_EVENTO;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Evento evento = new Evento();
                evento.setId(c.getInt(c.getColumnIndex(KEY_ID_EVENTO)));
                evento.setImmagine(c.getInt(c.getColumnIndex(KEY_IMMAGINE_EVENTO)));
                evento.setInteressati(c.getInt(c.getColumnIndex(KEY_INTERESSATI)));
                evento.setTitolo(c.getString(c.getColumnIndex(KEY_TITOLO_EVENTO)));
                evento.setDescrizione(c.getString(c.getColumnIndex(KEY_DESCRIZIONE_EVENTO)));
                evento.setLuogo(c.getString(c.getColumnIndex(KEY_LUOGO_EVENTO)));
                evento.setData(c.getString(c.getColumnIndex(KEY_DATA_EVENTO)));

                // adding to users list
                eventi.add(evento);
            } while (c.moveToNext());
        }
        Collections.sort(eventi, new Comparator<Evento>() {
            public int compare(Evento o1, Evento o2) {
                return (o2.getId() - o1.getId());
            }
        });
        return eventi;
    }

    /*##################################################################################################
                                        CHIUSURA DATABASE E ALTRO
    ##################################################################################################*/

    /**
     * get datetime
     */
    public String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
