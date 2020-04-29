package com.tqc.gdd02;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.DateFormat;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.lang.reflect.Array;

public class GDD02 extends Activity implements View.OnClickListener {
    Button btnOK, btnExit;
    ListView listview;
    Cursor cursor;
    SQLiteDatabase db;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        btnOK = (Button) findViewById(R.id.btnOK);
        btnExit = (Button) findViewById(R.id.btnExit);
        listview = (ListView) findViewById(R.id.listview);

        db = openOrCreateDatabase("notes.db", MODE_PRIVATE, null);
        String sql = "create table if not exists notes(_id integer primary key,note TEXT not null ,created integer)"; //TO DO
        db.execSQL(sql);

        db.execSQL("DELETE from notes;");
        db.execSQL("INSERT INTO notes values (1,'BOOK', 10);");
        db.execSQL("INSERT INTO notes values (2,'FOOD', 10);");
        db.execSQL("INSERT INTO notes values (3,'TOOL', 10);");

        cursor = db.rawQuery("SELECT * FROM notes;", null);

        btnOK.setOnClickListener(this);
        btnExit.setOnClickListener(this);

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this
                , android.R.layout.simple_list_item_multiple_choice
                , cursor
                , new String[]{"note"}
                , new int[]{android.R.id.text1}
                , SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        );

        // TO DO Listview
        listview.setAdapter(adapter);
        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    @Override
    public void onClick(View v) {
        if (v == btnOK) {
            String msg = "{";
            // TO DO
            SparseBooleanArray checklist = listview.getCheckedItemPositions();
            for (int i = 0; i < listview.getCount(); i++) {
                if (checklist.get(i)) {
                    cursor.moveToPosition(i);
                    msg += (cursor.getString(1) + " ");
                }
            }

            msg += "}";
            setTitle(msg);
        } else {
            finish();
        }
    }
}
