package com.example.ryno.contactlist;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Ryno on 22/08/2014.
 */
public class AddContact extends Activity{
    Button saveButton;
    EditText FNameEditText;
    EditText LNameEditText;
    EditText CellNumEditText;
    EditText EmailEditText;
    EditText HomeAdddrEditText;
    ContactsDbAdapter dbHelper;

    SQLiteDatabase mDb;
    ArrayList ContactArray = new ArrayList();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        FNameEditText = (EditText) findViewById(R.id.Firstname_edittext);
        LNameEditText=(EditText) findViewById(R.id.LastName_edittext);
        CellNumEditText=(EditText) findViewById(R.id.CellNum_edittext);
        EmailEditText=(EditText) findViewById(R.id.Email_edittext);
        HomeAdddrEditText=(EditText) findViewById(R.id.home_edittext);
        dbHelper = new ContactsDbAdapter(this);

        saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){

                String fname = FNameEditText.getText().toString();
                String lname = LNameEditText.getText().toString();
                String cellnum = CellNumEditText.getText().toString();
                String emailaddr = EmailEditText.getText().toString();
                String homeaddr = HomeAdddrEditText.getText().toString();

                Contact contact = new Contact();
                contact.setSurname(lname);
                contact.setFirstname(fname);
                contact.setCellNum(cellnum);
                contact.setEmailAddr(emailaddr);
                contact.setHomeAddr(homeaddr);

                Log.d("Insert: ", "Inserting .." + fname +" "+lname+" "+cellnum+" "+emailaddr+" "+homeaddr);

                ContactArray.add(contact);

                Long rowids = insertToDB(contact);
                //check inserted
                Log.d("Inserted ", "Inserted .." + rowids);
               finish();


            }
        });


    }

    public Long insertToDB(Contact contact){


        ContactsDbAdapter.DatabaseHelper mDbH = new ContactsDbAdapter.DatabaseHelper(getApplicationContext());
        mDb = mDbH.getWritableDatabase();
        long rowid = 0;
        /**
         * CRUD Operations
         * */
        // Inserting Contacts
        //;
        try{



            ContentValues val = new ContentValues();
            val.put(dbHelper.KEY_SURNAME, contact.getSurname());
            val.put(dbHelper.KEY_NAME, contact.getFirstname());
            val.put(dbHelper.KEY_CELLNUM, contact.getCellNum());
            val.put(dbHelper.KEY_EMAILADDR, contact.getEmailAddr());
            val.put(dbHelper.KEY_HOMEADDR, contact.getHomeAddr());

            rowid = mDb.insert(dbHelper.SQLITE_TABLE, null, val);
            mDb.close();

        }catch (Exception e){
            Log.e("insert Error", e.toString());
            e.printStackTrace();
        }
        //update table?


        //empty fields
        emptyFields();

        Toast.makeText(this, "Inserted ! "+rowid, Toast.LENGTH_LONG).show();

        return rowid;

    }

    public void emptyFields(){
        FNameEditText.setText("");
        LNameEditText.setText("");
        CellNumEditText.setText("");
        EmailEditText.setText("");
        HomeAdddrEditText.setText("");
    }

}
