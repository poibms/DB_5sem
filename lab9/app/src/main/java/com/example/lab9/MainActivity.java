package com.example.lab9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public EditText idEditText, fEditText, tEditText;
    int id;
    float f;
    String t;
    long rowId;

    SQLiteDatabase db;
    DataBaseHelper sqlHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get Elements
        idEditText = findViewById(R.id.idEditText);
        fEditText = findViewById(R.id.fEditText);
        tEditText = findViewById(R.id.tEditText);

        sqlHelper = new DataBaseHelper(this);
        db = sqlHelper.getWritableDatabase();
    }

    public void insertButton(View view) {
        try {
            id = Integer.parseInt(idEditText.getText().toString());
            f = Float.parseFloat(fEditText.getText().toString());
            t = tEditText.getText().toString();

            ContentValues values = new ContentValues();
            values.put("ID", id);
            values.put("F", f);
            values.put("T", t);

            rowId = db.insert(DataBaseHelper.TABLE, null, values);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }

    public void selectButton(View view) {
        try {
            String[] colums = {DataBaseHelper.COLUMN_ID, DataBaseHelper.COLUMN_F, DataBaseHelper.COLUMN_T};
            String whereParams = DataBaseHelper.COLUMN_ID + " = ?";
            String[] selectParams = {idEditText.getText().toString()};

            Cursor cursor = db.query(DataBaseHelper.TABLE, colums, whereParams, selectParams, null, null, null);
            cursor.moveToFirst();
            String idValue = cursor.getString(0);
            float fValue = cursor.getFloat(1);
            String tValue = cursor.getString(2);
            idEditText.setText(idValue);
            fEditText.setText(String.valueOf(fValue));
            tEditText.setText(tValue);
            cursor.close();
        } catch(Exception e) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }

    public void selectRowButton(View view) {
        try {
            String[] selectParams = {idEditText.getText().toString()};
            Cursor cursor = db.rawQuery("select ID, F, T from SimpleTable where ID = ?", selectParams);
            cursor.moveToFirst();
            String idValue = cursor.getString(0);
            float fValue = cursor.getFloat(1);
            String tValue = cursor.getString(2);
            idEditText.setText(idValue);
            fEditText.setText(String.valueOf(fValue));
            tEditText.setText(tValue);
            cursor.close();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }

    public void updateButton(View view) {
        try {
            String queryParams = DataBaseHelper.COLUMN_ID + " LIKE ?";
            String[] selectParams = {idEditText.getText().toString()};
            f = Float.parseFloat(fEditText.getText().toString());
            t = tEditText.getText().toString();
            ContentValues values = new ContentValues();
            values.put("F", f);
            values.put("T", t);
            rowId = db.update(
                    DataBaseHelper.TABLE,
                    values,
                    queryParams,
                    selectParams);

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }

    public void deleteButton(View view) {
        try {
            String queryParams = DataBaseHelper.COLUMN_ID + " LIKE ?";
            String[] selectParams = {idEditText.getText().toString()};
            int deletedRows = db.delete(DataBaseHelper.TABLE, queryParams,
                    selectParams);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }

    public void clearButton(View view) {
        idEditText.setText("");
        fEditText.setText("");
        tEditText.setText("");
    }
}