package com.example.lab8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String fname = "notes.xml";
    String categoryFname = "categories.xml";

    public String selectedDate;
    public EditText noteEdit, categoryEdit;
    public Spinner categorySpinner;
    public Button addNote, addCategory, editNote, deleteNote;
    public ListView listNotes;
    int itemPosition = 0;
    public List<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ExistBase(fname);
        openNotes();
        openCategories();

        //get
        noteEdit = findViewById(R.id.noteEdit);
        categoryEdit = findViewById(R.id.categoryEdit);
        categorySpinner = findViewById(R.id.categorySpinner);
        addNote = findViewById(R.id.add);
        editNote = findViewById(R.id.edit);
        deleteNote = findViewById(R.id.delete);
        addCategory = findViewById(R.id.addCategory);
        listNotes = findViewById(R.id.ListView);

        CalendarView cal = findViewById(R.id.calendar);
        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                int mYear = year;
                int mMonth = month;
                int mDay = dayOfMonth;
                selectedDate = new StringBuilder().append(mDay).append(".").append(mMonth + 1).append(".").append(mYear).toString();
                check();
                noteEdit.setText("");
            }
        });
        listNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                itemPosition = position;

                editNote.setVisibility(View.VISIBLE);
                deleteNote.setVisibility(View.VISIBLE);
                addNote.setVisibility(View.INVISIBLE);
            }
        });
    }

    private boolean ExistBase(String fname) {
        boolean rc = false;
        File f = new File(super.getFilesDir(), fname);
        File f2 = new File(super.getFilesDir(), categoryFname);
        if(rc = f.exists()) {
            Toast.makeText(this, "File already created", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "File not founded", Toast.LENGTH_SHORT).show();
            try {
                f.createNewFile();
                Toast.makeText(this, "File was created ", Toast.LENGTH_SHORT).show();
            } catch(IOException ex) {

                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        if(rc = f2.exists()) {
            Toast.makeText(this, "File already created", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "File not founded", Toast.LENGTH_SHORT).show();
            try {
                f2.createNewFile();
                Toast.makeText(this, "File was created ", Toast.LENGTH_SHORT).show();
            } catch(IOException ex) {

                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        return rc;
    }

    public void saveNotes(List<Note> list) {
        XML xml = new XML(fname);
        xml.writeNoteToInternal(this, list);
    }

    public void saveCategories(List<String> list) {
        XML xml = new XML(categoryFname);
        xml.writeCategoryToInternal(this, list);
    }

    public List<Note> openNotes() {
        List<Note> NoteList = new ArrayList<Note>();
        XML xml = new XML(fname);
        try {
            NoteList = xml.readNoteFromInternal(this);
        } catch (Exception e) {
            Toast.makeText(this, "Файл отсутствует", Toast.LENGTH_LONG).show();
        }
        return NoteList != null ? NoteList : new ArrayList<Note>();
    }

    public List<String> openCategories() {
        List<String> list = new ArrayList<String>();
        XML xml = new XML(categoryFname);
        try {
            list = xml.readCategoryFromInternal(this);
        } catch (Exception e) {
            Toast.makeText(this, "Файл отсутствует", Toast.LENGTH_LONG).show();
        }
        return list != null ? list : new ArrayList<String>();
    }

    public void addNoteBtn(View view) {
        try {
            List<Note> notes = openNotes();
            if (notes.size() > 19)
                return;
            int count = 0;
            for (Note var : notes) {
                if (var.getDate().equals(selectedDate))
                    count++;
            }
            if (count > 4)
                return;
            String outstr = noteEdit.getText().toString();
            String outcat = categorySpinner.getSelectedItem().toString();
            notes.add(new Note(selectedDate, outstr, outcat));

            CustomComparator ds = new CustomComparator();
            Collections.sort(notes, ds);
            saveNotes(notes);
            check();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void check() {
        editNote.setVisibility(View.INVISIBLE);
        deleteNote.setVisibility(View.INVISIBLE);
        addNote.setVisibility(View.VISIBLE);

        List<Note> notes = openNotes();
        List<String> noteForView = new ArrayList<>();
        List<String> categories = openCategories();
        for (Note itVar : notes) {
            if (selectedDate.equals(itVar.getDate())) {
                noteForView.add(itVar.getValue() + " - " + itVar.getCategory());
            }
        }
        ArrayAdapter<String> adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter1);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, noteForView);
        listNotes.setAdapter(adapter);

//        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,
//                android.R.layout.simple_list_item_1, categories);
//        listViewCategories.setAdapter(adapter2);
        /*NoteAdapter adapter = new NoteAdapter(this, noteForView);
        recyclerViewNotes.setAdapter(adapter);*/

        /*CategoryAdapter adapter2 = new CategoryAdapter(this, categories);
        recyclerViewCategories.setAdapter(adapter2);*/
        return;
    }


    public void deleteNoteBtn(View view) {
        try {
            List<Note> notes = openNotes();
            Note tmp = notes.get(itemPosition);
            notes.remove(tmp);
            saveNotes(notes);
            check();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void addCategoryBtn(View view) {
        List<String> categories = openCategories();
        if (categories.size() > 4)
            return;
        String newCat = categoryEdit.getText().toString();
        categories.add(newCat);
        saveCategories(categories);
        check();
    }

    public void changeNoteBtn(View view) {
        try {
            List<Note> notes = openNotes();
            if (notes.size() > 19)
                return;
            int count = 0;
            for (Note var : notes) {
                if (var.getDate().equals(selectedDate))
                    count++;
            }
            if (count > 4)
                return;

            String outstr = noteEdit.getText().toString();
            String outcat = categorySpinner.getSelectedItem().toString();

            Note tmp = notes.get(itemPosition);
            tmp.setCategory(outcat);
            tmp.setValue(outstr);

            CustomComparator ds = new CustomComparator();
            Collections.sort(notes, ds);
            saveNotes(notes);
            check();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}