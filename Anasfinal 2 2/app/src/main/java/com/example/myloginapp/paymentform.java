package com.example.myloginapp;

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

import java.util.Random;

public class paymentform extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mealerapp-a6e86-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentform);
        final EditText firstname= findViewById(R.id.username) ;
        final EditText lastname= findViewById(R.id.lastname);
        final EditText email =findViewById(R.id.email);
        final EditText password =findViewById(R.id.password);
        final EditText username = findViewById(R.id.adresse);
        final Button signupbtn = findViewById(R.id.signupbtn);
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emailtxt = email.getText().toString().trim();
                final  String firstnametxt = firstname.getText().toString().trim();
                final String lastnametxt = lastname.getText().toString().trim();
                final String passwordtxt = password.getText().toString().trim();
                final String usernametxt = username.getText().toString().trim();
                if (emailtxt.isEmpty() || firstnametxt.isEmpty() || lastnametxt.isEmpty() || passwordtxt.isEmpty() || usernametxt.isEmpty() ){
                    Toast.makeText(paymentform.this, "Veuillez saisir toutes les informations ", Toast.LENGTH_SHORT).show();
                }
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Random rand = new Random();

                        databaseReference.child("commande").child(String.valueOf(rand.nextInt())).child("status").setValue("en attente");
                        Toast.makeText(paymentform.this, "Commande confirmé", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }
    public void ichrak1(View view){
        Intent intent = new Intent(getApplicationContext(), Client.class);
        startActivityForResult(intent, 0);}
}