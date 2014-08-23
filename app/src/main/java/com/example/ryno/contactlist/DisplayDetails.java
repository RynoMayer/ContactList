package com.example.ryno.contactlist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ryno on 22/08/2014.
 */
public class DisplayDetails extends Activity {
    private int layoutResourceId;
    private Context context;
    private ContactsDbAdapter dbHelper;
    private SimpleCursorAdapter dataAdapter;
    Button backButton;
    TextView FnameTextView;
    TextView SnameTextView;
    TextView cellnumbTextView;
    TextView emailsTextView;
    TextView homeATextView;
    String message;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        FnameTextView = (TextView) findViewById(R.id.FNdetails_text_view);
        SnameTextView =  (TextView) findViewById(R.id.LNdetails_text_view);
        cellnumbTextView = (TextView) findViewById(R.id.cellDetails_text_view);
        emailsTextView = (TextView) findViewById(R.id.EmailDetails_text_view);
        homeATextView = (TextView) findViewById(R.id.homedetails_text_view);

        dbHelper = new ContactsDbAdapter(this);
        dbHelper.open();

        displayDetails();

    }
    private void displayDetails(){
        // Details contact = dbhandler.getContact(0);
        Intent intent=getIntent();
        message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // The ArrayList that holds the row data
        ArrayList<Object> row;
        // ask the database manager to retrieve the row with the given rowID
        row = dbHelper.getRowAsArray(message);
        Log.w("rows", row.toString());
    try{
        // update the form fields to hold the retrieved data
        String sn = (String)row.get(0);
        String fn = (String)row.get(1);
        String cn = (String)row.get(2);
        String em= (String)row.get(3);
        String ad = (String)row.get(4);

        SnameTextView.setText(sn);
        FnameTextView.setText(fn);
        cellnumbTextView.setText(cn);
        emailsTextView.setText(em);
        homeATextView.setText(ad);
    }catch (Exception e){
        Log.e("Retrieve Error", e.toString());
        e.printStackTrace();
    }


        Cursor cursor = dbHelper.fetchCountriesByName(message);

        // The desired columns to be bound
        String[] columns = new String[] {
                ContactsDbAdapter.KEY_SURNAME,
                ContactsDbAdapter.KEY_NAME,
                ContactsDbAdapter.KEY_CELLNUM,
                ContactsDbAdapter.KEY_EMAILADDR,
                ContactsDbAdapter.KEY_HOMEADDR
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[] {
                R.id.FNdetails_text_view,
                R.id.LNdetails_text_view,
                R.id.cellDetails_text_view,
                R.id.EmailDetails_text_view,
                R.id.homedetails_text_view,
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.activity_details,
                cursor,
                columns,
                to,
                0);




    }
}
