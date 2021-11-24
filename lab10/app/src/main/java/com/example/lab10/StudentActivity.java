package com.example.lab10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class StudentActivity extends AppCompatActivity {
    EditText idGroup, idStudent, studentName;
    DataBaseHelper databaseHelper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        idStudent = findViewById(R.id.studentId);
        idGroup = findViewById(R.id.groupId);
        studentName = findViewById(R.id.studentName);

        databaseHelper = new DataBaseHelper(this);
        db = databaseHelper.getWritableDatabase();
    }

    public void AddStudent(View view) {
        try {
//            id = Integer.parseInt(idEditText.getText().toString());
//            f = Float.parseFloat(fEditText.getText().toString());
//            t = tEditText.getText().toString();

            ContentValues values = new ContentValues();
            values.put("ID_STUDENT", idStudent.getText().toString());
            values.put("_id", idGroup.getText().toString());
            values.put("NAME", studentName.getText().toString());

            long rowId = db.insert(DataBaseHelper.S_TABLE_NAME, null, values);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }
}