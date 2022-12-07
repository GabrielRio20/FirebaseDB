package com.example.firebasedb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity3 extends AppCompatActivity {

    EditText etCampus, etLocation, etUpdateCampus, etUpdateLocation;

    DatabaseReference databaseReference;
    Campus campus;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        databaseReference = FirebaseDatabase.getInstance().getReference(Campus.class.getSimpleName());

        etCampus = findViewById(R.id.et_campus);
        etLocation = findViewById(R.id.et_location);
        etUpdateCampus = findViewById(R.id.et_update_campus);
        etUpdateLocation = findViewById(R.id.et_update_location);

        findViewById(R.id.btn_insert_campus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });
    }

    private void insertData(){
        Campus newCampus = new Campus();
        String campusName = etCampus.getText().toString();
        String location = etLocation.getText().toString();

        if(campusName != "" && location != ""){
            newCampus.setCampusName(campusName);
            newCampus.setLocation(location);

            databaseReference.push().setValue(newCampus);
            Toast.makeText(this, "Data Inserted", Toast.LENGTH_SHORT).show();
        }
    }
}