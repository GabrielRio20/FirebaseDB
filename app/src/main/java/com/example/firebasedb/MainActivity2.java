package com.example.firebasedb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity2 extends AppCompatActivity {

    EditText etClass, etCapacity, etUpdateClass, etUpdateCapacity;
    Button btnInsert, btnRead, btnUpdate, btnDelete, btnStudent;

    DatabaseReference databaseReference;
    Classes classes;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        databaseReference = FirebaseDatabase.getInstance().getReference(Classes.class.getSimpleName());

        etClass = findViewById(R.id.et_class);
        etCapacity = findViewById(R.id.et_capacty);
        etUpdateClass = findViewById(R.id.et_update_class);
        etUpdateCapacity = findViewById(R.id.et_update_capacity);

        btnInsert = findViewById(R.id.btn_insert_class);
        btnRead = findViewById(R.id.btn_read_class);
        btnUpdate = findViewById(R.id.btn_update);
        btnDelete = findViewById(R.id.btn_delete);
        btnStudent = findViewById(R.id.btn_student);

        btnStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readData();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });
    }

    private void insertData(){
        Classes nClasses = new Classes();
        String className = etClass.getText().toString();
        int capacity = Integer.parseInt(etCapacity.getText().toString());

        if(className != "" && capacity != 0){
            nClasses.setClassName(className);
            nClasses.setCapacity(capacity);

            databaseReference.push().setValue(nClasses);
            Toast.makeText(this, "Successfully insert data!", Toast.LENGTH_SHORT).show();
        }
    }

    private void readData(){
        classes = new Classes();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()){
                    for(DataSnapshot currentData:snapshot.getChildren()){
                        key = currentData.getKey();
                        classes.setClassName(currentData.child("className").getValue().toString());
                        classes.setCapacity(Integer.parseInt(currentData.child("capacity").getValue().toString()));
                    }
                }

                etUpdateClass.setText(classes.getClassName());
                etUpdateCapacity.setText(String.valueOf(classes.getCapacity()));

                Toast.makeText(MainActivity2.this, "Data has been shown!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateData(){
        Classes updatedData = new Classes();
        updatedData.setClassName(etUpdateClass.getText().toString());
        updatedData.setCapacity(Integer.parseInt(etCapacity.getText().toString()));
        databaseReference.child(key).setValue(updatedData);
    }

    private void deleteData(){
        Classes deletedData = new Classes();
        deletedData.setClassName(etUpdateClass.getText().toString());
        deletedData.setCapacity(Integer.parseInt(etCapacity.getText().toString()));
        databaseReference.child(key).removeValue();
    }

}