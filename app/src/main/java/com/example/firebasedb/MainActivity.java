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

public class MainActivity extends AppCompatActivity {
    EditText etName, etAddress, etUpdateName, etUpdateAddress;
//    Button insert, read, update;
    Button btnCampus;

    DatabaseReference databaseReference;
    Student student;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReference = FirebaseDatabase.getInstance().getReference(Student.class.getSimpleName());

        etName = findViewById(R.id.et_name);
        etAddress = findViewById(R.id.et_address);
        etUpdateName = findViewById(R.id.et_update_name);
        etUpdateAddress = findViewById(R.id.et_update_address);

        btnCampus = findViewById(R.id.btn_classes);

        btnCampus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity2.class));
            }
        });

        findViewById(R.id.btn_insert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });

        findViewById(R.id.btn_read).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readData();
            }
        });

        findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });

        findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });
    }

    private void insertData(){
        Student newStudent = new Student();
        String name = etName.getText().toString();
        String address = etAddress.getText().toString();
        if(name != "" && address != ""){
            newStudent.setName(name);
            newStudent.setAddress(address);

            databaseReference.push().setValue(newStudent);
            Toast.makeText(this, "Successfully insert data!", Toast.LENGTH_SHORT).show();
        }
    }

    private void readData(){

        student = new Student();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()){
                    for(DataSnapshot currentData:snapshot.getChildren()){
                        key = currentData.getKey();
                        student.setName(currentData.child("name").getValue().toString());
                        student.setAddress(currentData.child("address").getValue().toString());
                    }
                }

                etUpdateName.setText(student.getName());
                etUpdateAddress.setText(student.getAddress());
                Toast.makeText(MainActivity.this, "Data has been shown!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateData(){
        Student updatedData = new Student();
        updatedData.setName(etUpdateName.getText().toString());
        updatedData.setAddress(etUpdateAddress.getText().toString());

        //untuk update
        databaseReference.child(key).setValue(updatedData);
        //untuk hapus
//        databaseReference.child(key).removeValue();
    }

    private void deleteData() {
        Student deleteData = new Student();
        deleteData.setName(etUpdateName.getText().toString());
        deleteData.setAddress(etUpdateAddress.getText().toString());

        databaseReference.child(key).removeValue();
    }
}