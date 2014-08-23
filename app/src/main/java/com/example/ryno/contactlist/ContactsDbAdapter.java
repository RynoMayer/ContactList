package com.example.ryno.contactlist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Ryno on 20/08/2014.
 */
public class ContactsDbAdapter {
    public static final String KEY_ROWID = "_id";
    public static final String KEY_SURNAME = "surname";
    public static final String KEY_NAME = "name";
    public static final String KEY_CELLNUM = "cellnum";
    public static final String KEY_EMAILADDR = "emailAddr";
    public static final String KEY_HOMEADDR = "homeAddr";

    private static final String TAG = "ContactsDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    public static final String DATABASE_NAME = "AddressBook";
    public static final String SQLITE_TABLE = "Contacts";
    public static final int DATABASE_VERSION = 1;

    public final Context mCtx;

    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + SQLITE_TABLE + " (" +
                    KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                    KEY_SURNAME + "," +
                    KEY_NAME + "," +
                    KEY_CELLNUM + "," +
                    KEY_EMAILADDR + "," +
                    KEY_HOMEADDR + "," +
                    " UNIQUE (" + KEY_CELLNUM +"));";

    public static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG, DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
            onCreate(db);
        }
    }

    public ContactsDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public ContactsDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    public void createCountry(String surname, String name,
                              String cell, String email, String home) {
        mDb = mDbHelper.getWritableDatabase();

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_SURNAME, surname);
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_CELLNUM, cell);
        initialValues.put(KEY_EMAILADDR, email);
        initialValues.put(KEY_HOMEADDR, home);

        mDb.insert(SQLITE_TABLE, null, initialValues);
        //mDb.close();
    }

    public boolean deleteAllCountries() {

        int doneDelete = 0;
        doneDelete = mDb.delete(SQLITE_TABLE, null , null);
        Log.w(TAG, Integer.toString(doneDelete));
        return doneDelete > 0;

    }

    public Cursor fetchCountriesByName(String inputText) throws SQLException {
        Log.w(TAG, inputText);
        Cursor mCursor = null;
        if (inputText == null  ||  inputText.length () == 0)  {
            mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_ROWID,
                            KEY_SURNAME, KEY_NAME,KEY_CELLNUM ,KEY_EMAILADDR, KEY_HOMEADDR},
                    null, null, null, null, null);

        }
        else {
            mCursor = mDb.query(true, SQLITE_TABLE, new String[] {KEY_ROWID,
                            KEY_SURNAME, KEY_NAME, KEY_CELLNUM,KEY_EMAILADDR, KEY_HOMEADDR},
                    KEY_CELLNUM + " = " + inputText, null,
                    null, null, null, null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    public Cursor fetchAllCountries() {

        Cursor mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_ROWID,
                        KEY_SURNAME, KEY_NAME, KEY_CELLNUM,KEY_EMAILADDR, KEY_HOMEADDR},
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public void insertSomeCountries() {

        createCountry("jack", "Ravi", "9100000000", "jRav@gmail.com","25 bloem goodwood");
        createCountry("john","Srinivas", "9199999999","jstrin@gmail.com","70 voortrekker parow" );
        createCountry("Tommy","lee", "9522222222","tlee@gmail.com","12 orchid tygerberg");
        createCountry("Karthik","Jones", "9533333333","kjones@gmail.com","35 kerk panorama");

    }

    public ArrayList<Object> getRowAsArray(String rowID)
    {
        // create an array list to store data from the database row.
        // I would recommend creating a JavaBean compliant object
        // to store this data instead.  That way you can ensure
        // data types are correct.
        ArrayList<Object> rowArray = new ArrayList<Object>();
        Cursor cursor;

        try
        {
            // this is a database call that creates a "cursor" object.
            // the cursor object store the information collected from the
            // database and is used to iterate through the data.
            cursor = mDb.query
                    (
                            SQLITE_TABLE,
                            new String[] { KEY_SURNAME, KEY_NAME, KEY_CELLNUM,KEY_EMAILADDR, KEY_HOMEADDR  },
                            KEY_CELLNUM + " = '" +rowID+"'",
                            null, null, null, null, null
                    );
                Log.w("quaery", String.valueOf(cursor.getColumnNames()));
            // move the pointer to position zero in the cursor.
            cursor.moveToFirst();

            // if there is data available after the cursor's pointer, add
            // it to the ArrayList that will be returned by the method.
            if (!cursor.isAfterLast())
            {
                do
                {
                    rowArray.add(cursor.getString(0));
                    rowArray.add(cursor.getString(1));
                    rowArray.add(cursor.getString(2));
                    rowArray.add(cursor.getString(3));
                    rowArray.add(cursor.getString(4));
                }
                while (cursor.moveToNext());
            }

            // let java know that you are through with the cursor.
            cursor.close();
        }
        catch (SQLException e)
        {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }

        // return the ArrayList containing the given row from the database.
        return rowArray;
    }

    public void addContact(Contact contact) {
        mDb = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SURNAME, contact.getSurname()); // Contact surName
        values.put(KEY_CELLNUM, contact.getCellNum()); // Contact Phone
        values.put(KEY_NAME, contact.getFirstname()); // Contact FName
        values.put(KEY_EMAILADDR, contact.getEmailAddr()); // Contact email
        values.put(KEY_HOMEADDR, contact.getHomeAddr()); // Contact home

        // Inserting Row
        mDb.insert(SQLITE_TABLE, null, values);
        mDb.close(); // Closing database connection
    }

}

