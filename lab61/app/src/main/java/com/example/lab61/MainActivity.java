package com.example.lab61;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        verifyStoragePermissions(this);
//        openFromInt();
        openFromExt();

        CalendarView cv = findViewById(R.id.calendarView);
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                int mYear = year;
                int mMonth = month;
                int mDay = dayOfMonth;
                String selectedDate = new StringBuilder().append(mDay).append("/").append(mMonth + 1).append("/").append(mYear).toString();
                TextView textView = findViewById(R.id.textView);
                List<User> persons = openFromExt();
                String outtext = "";

                for (User itVar : persons)
                {
                    if(selectedDate.equals(itVar.getDate())) {
                        outtext = "Name: " + itVar.getName() + "\n" + "SrName: " + itVar.getSrName() + "\n"
                                + "Mobile phone: " + itVar.getPhone();
                    } else {
                        outtext ="Empty data";
                    }
                }
                textView.setText(outtext);
            }
        });
    }

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    public List<User> openFromExt(){
        List<User> usersList = new ArrayList<User>();
        try{
            usersList = JSONHelper.importFromJSONExternal(this);
//            TextView textView = (TextView) findViewById(R.id.textView);
//            String text = new String (usersList!=null ? usersList.toString() : "empty");
//            textView.setText(text);
//            Toast.makeText(this, usersList.toString(), Toast.LENGTH_LONG).show();
        }catch(Exception e){
            Toast.makeText(this, "Файл ext отсутствует", Toast.LENGTH_LONG).show();
        }
        return usersList;
    }

    public List<User> openFromInt() {
        List<User> personList = new ArrayList<User>();
        try {
            personList = JSONHelper.importFromJSONInternal(this);
//            TextView textView = (TextView) findViewById(R.id.textView);
//            String text = new String (personList!=null ? personList.toString() : "empty");
//            textView.setText(text);

            Toast.makeText(this, personList.toString(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Файл отсутствует", Toast.LENGTH_LONG).show();
        }
        return personList;
    }
}