package com.example.lab10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    EditText idGroup;
    ListView listView;
    DataBaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
    DataBaseHelper sqlHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idGroup = findViewById(R.id.selectId);
        listView = findViewById(R.id.listView);

        databaseHelper = new DataBaseHelper(this);
        db = databaseHelper.getReadableDatabase();

    }
    public void AddGroupButton(View view) {
        Intent intent = new Intent(this, GroupActivity.class);
        startActivity(intent);
    }

    public void AddStudentButton(View view) {
        Intent intent = new Intent(this, StudentActivity.class);
        startActivity(intent);
    }

    public void selectIdGroup(View view) {
        try {
            db = databaseHelper.getReadableDatabase();

            int group = Integer.parseInt(String.valueOf(idGroup.getText()));
            String sql = "select * from Students where _id = " + group;
            userCursor = db.rawQuery(sql,null);
            String[] headers = new String[  ]{DataBaseHelper.ID_GROUP, DataBaseHelper.NAME};
            userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                    userCursor, headers, new int[]{android.R.id.text1,android.R.id.text2}, 0);
            listView.setAdapter(userAdapter);

        }
        catch (Exception e)
        {
            Toast.makeText(this, "Нет такой группы!", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteStudent(View view) {
        int group = Integer.parseInt(String.valueOf(idGroup.getText()));
        try
        {
            db.delete(databaseHelper.S_TABLE_NAME,"ID_STUDENT = ?", new String[] {String.valueOf(group)});
            Toast.makeText(this, "удалено", Toast.LENGTH_SHORT).show();

        }
        catch (Exception e) {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }

    }

    public void deleteGroup(View view) {
        int group = Integer.parseInt(String.valueOf(idGroup.getText()));
        try
        {
            db.delete(databaseHelper.G_TABLE_NAME,"_id = ?", new String[] {String.valueOf(group)});
            Toast.makeText(this, "удалено", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();}

    }
}