package com.example.ryno.contactlist;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {
    private ContactsDbAdapter dbHelper;
    private SimpleCursorAdapter dataAdapter;
    public String msg = null;
    SQLiteDatabase mDb;
    public static final String EXTRA_MESSAGE = "com.example.ryno.anthrolibri.MESSAGE";
    Button addContact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addContact = (Button) findViewById(R.id.addContact_button);
        addContact.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this,AddContact.class);
                startActivity(intent);
            }});

        dbHelper = new ContactsDbAdapter(this);
        dbHelper.open();

        //Clean all data
        dbHelper.deleteAllCountries();
        //Add some data
        dbHelper.insertSomeCountries();

        //Generate ListView from SQLite Database
        displayListView();

    }

    public void displayListView() {


        Cursor cursor = dbHelper.fetchAllCountries();

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
                R.id.surname_text,
                R.id.name,
                R.id.cell_text,
                R.id.email_text,
                R.id.home_text,
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.country_info,
                cursor,
                columns,
                to,
                0);

        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // Get the state's capital from this row in the database.
                String contactCell =
                        cursor.getString(cursor.getColumnIndexOrThrow("cellnum"));

                //go to next intent
                Intent intent = new Intent(MainActivity.this,DisplayDetails.class);
                //send id
                intent.putExtra(EXTRA_MESSAGE, contactCell);
                startActivity(intent);

                Toast.makeText(getApplicationContext(),
                        contactCell, Toast.LENGTH_SHORT).show();

            }
        });

    }
    @Override
    public void onResume(){
        super.onResume();
        displayListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    }

