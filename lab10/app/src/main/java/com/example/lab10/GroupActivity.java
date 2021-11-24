package com.example.lab10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class GroupActivity extends AppCompatActivity {
    EditText idEdit, facultyEdit, courseEdit, nameEdit, headEdit;
    SQLiteDatabase db;
    DataBaseHelper sqlHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        idEdit = findViewById(R.id.idGroup);
        facultyEdit = findViewById(R.id.facultyGroup);
        courseEdit = findViewById(R.id.courseGroup);
        nameEdit = findViewById(R.id.nameGroup);
        headEdit = findViewById(R.id.headGroup);

        sqlHelper = new DataBaseHelper(this);
        db = sqlHelper.getWritableDatabase();
    }

    public void InsertGroup(View view) {
        try {
            ContentValues values = new ContentValues();
            values.put("_id", idEdit.getText().toString());
            values.put("FACULTY", facultyEdit.getText().toString());
            values.put("COURSE", courseEdit.getText().toString());
            values.put("NAME", nameEdit.getText().toString());
            values.put("HEAD", headEdit.getText().toString());

            long rowId = db.insert(DataBaseHelper.G_TABLE_NAME, null, values);
            Toast.makeText(getApplicationContext(), "Inserted", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }

    }
}