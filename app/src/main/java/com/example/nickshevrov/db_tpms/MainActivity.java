package com.example.nickshevrov.db_tpms;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    final Context c = this;
    private Button addButton;
    private Button summButton;
    private TextView SummText;
    private DB MyDB;
    private ArrayAdapter<String> mAdapter;
    private ArrayList<String> Names = new ArrayList<String>();
    private ArrayList<Integer> Values = new ArrayList<Integer>();
    private ArrayList<HashMap> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyDB = new DB(this);

        getFromDb();

        ListView lview = (ListView) findViewById(R.id.lvMain);
        populateList();
        listviewAdapter adapter = new listviewAdapter(this, list);
        lview.setAdapter(adapter);

        SummText = (TextView) findViewById(R.id.summText);
        addButton = (Button) findViewById(R.id.add_to_list);
        summButton = (Button) findViewById(R.id.summ);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                View mView = layoutInflaterAndroid.inflate(R.layout.custom_layout, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setView(mView);

                final EditText userInputName = (EditText) mView.findViewById(R.id.editText);
                final EditText userInputValue = (EditText) mView.findViewById(R.id.editText2);
                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                String name = userInputName.getText().toString();
                                Integer value = 0;
                                try {
                                    value = Integer.valueOf(userInputValue.getText().toString());
                                } catch (NumberFormatException e) {
                                    Log.d("unlucky", e.toString());
                                }

                                addToDb(name, value);
                                getFromDb();
                            }
                        })

                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
            }
        });

        summButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSummFromDb();
            }
        });
    }

    private void populateList() {
        list = new ArrayList<HashMap>();

        for (Integer i = 0; i < Names.size(); i++) {
            HashMap temp = new HashMap();

            temp.put("First", Names.get(i));
            temp.put("Second", Values.get(i).toString());

            list.add(temp);
        }
    }

    private void addToDb(String name, int value) {
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = MyDB.getWritableDatabase();

        cv.put("name", name);
        cv.put("value", value);

        db.insert("TMPS", null, cv);
    }

    private void getFromDb() {
        SQLiteDatabase db = MyDB.getWritableDatabase();

        ArrayList<String> NamesIn = new ArrayList<String>();
        ArrayList<Integer> ValuesIn = new ArrayList<Integer>();

        try {
            Cursor c = db.query("TMPS", new String[]{"NAME", "VALUE"}, null, null, null, null, null);
            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        String name = c.getString(c.getColumnIndex("NAME"));
                        int value = c.getInt(c.getColumnIndex("VALUE"));
                        NamesIn.add(name);
                        ValuesIn.add(value);
                    } while (c.moveToNext());
                }
            }
        } catch (SQLiteException se) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        } finally {
            Names = NamesIn;
            Values = ValuesIn;
            displayResultList();
            db.close();
        }
    }

    private void getSummFromDb() {
        SQLiteDatabase db = MyDB.getWritableDatabase();
        Integer Sum = 0;

        try {
            Cursor c = db.rawQuery("SELECT SUM(VALUE) FROM TMPS", null);
            if (c != null) {
                if (c.moveToFirst()) {
                    Sum = c.getInt(0);

                    SummText.setText(Sum.toString());
                }
            }
        } catch (SQLiteException se) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        } finally {
            db.close();
        }
    }

    private void displayResultList() {
        ListView lview = (ListView) findViewById(R.id.lvMain);
        populateList();
        listviewAdapter adapter = new listviewAdapter(this, list);
        lview.setAdapter(adapter);
    }

}
