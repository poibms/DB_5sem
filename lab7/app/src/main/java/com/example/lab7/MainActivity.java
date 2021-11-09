package com.example.lab7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String selectedDate;
    private EditText note;
    private Button editBtn, delBtn, saveBtn;
    String fname = "note.json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        note = findViewById(R.id.noteEdit);
        saveBtn = findViewById(R.id.button4);
        editBtn = findViewById(R.id.editBtn);
        delBtn = findViewById(R.id.delBtn);


        ExistBase(fname);

        CalendarView cv = findViewById(R.id.calendarView);
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                int mYear = year;
                int mMonth = month;
                int mDay = dayOfMonth;
                selectedDate = new StringBuilder().append(mDay).append("/").append(mMonth + 1).append("/").append(mYear).toString();
                getData();
            }
        });
    }


    private boolean ExistBase(String fname) {
        boolean rc = false;
        File file = new File(super.getFilesDir(), fname);
        if(rc = file.exists()) {
            Toast.makeText(this, "File already created", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "File not founded", Toast.LENGTH_SHORT).show();
            try {
                file.createNewFile();
                Toast.makeText(this, "File was created ", Toast.LENGTH_SHORT).show();
            } catch(IOException ex) {

                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        return rc;
    }

    public List<Note> openFromInt() {
        List<Note> noteList = new ArrayList<Note>();
        try {
            noteList = JSONHelper.importFromJSONInternal(this);
//            TextView textView = (TextView) findViewById(R.id.outInternal);
//            String text = new String (personList!=null ? personList.toString() : "");
//            textView.setText(text);
//            textView.setText(personList.toString());
//            Toast.makeText(this, personList.toString(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Файл отсутствует", Toast.LENGTH_LONG).show();
        }
        return noteList;
    }

    private void getData() {
        List<Note> notes = openFromInt();
        String text = "";

        for (Note checNote : notes) {
            if (selectedDate.equals(checNote.getDate())) {
                text = checNote.getNote().toString();
                saveBtn.setVisibility(View.INVISIBLE);
                editBtn.setVisibility(View.VISIBLE);
                delBtn.setVisibility(View.VISIBLE);
                note.setText(text);
                return;
            }
        }
        saveBtn.setVisibility(View.VISIBLE);
        editBtn.setVisibility(View.INVISIBLE);
        delBtn.setVisibility(View.INVISIBLE);
        note.setText("");
        return;
    }

    private void saveData(List<Note> notes) {
        boolean result = JSONHelper.exportToJSONInternal(this, notes);
        if (result) {
            Toast.makeText(this, "File was updated", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
        }
    }

    public void SaveNote(View view) {
        String noteValue = note.getText().toString();
        Note note = new Note(selectedDate, noteValue);
        List<Note> notes = new ArrayList<>();
        notes = JSONHelper.importFromJSONInternal(this);
        notes.add(note);

        saveData(notes);
        getData();
    }

    public void EditNote(View view) {
        try {
            List<Note> notes = openFromInt();
            Note editNote = new Note();
            for (Note checkNote : notes){
                if(selectedDate.equals(checkNote.getDate())){
                    editNote = checkNote;
                }
            }
            editNote.setNote(note.getText().toString());
            saveData(notes);
            getData();
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void DelNote(View view) {
        try {
            List<Note> notes = openFromInt();
            Note delNote = new Note();
            for(Note checkNote : notes) {
                if(selectedDate.equals(checkNote.getDate())) {
                    delNote = checkNote;
                }
            }
            notes.remove(delNote);
            saveData(notes);
            getData();
            
        } catch (Exception e) {
            Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
        }
    }
}