package com.example.myloginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class registercook extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mealerapp-a6e86-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registercook);
        final EditText firstname= findViewById(R.id.username) ;
        final EditText lastname= findViewById(R.id.lastname);
        final EditText email =findViewById(R.id.email);
        final EditText password =findViewById(R.id.password);
        final EditText username = findViewById(R.id.adresse);
        final EditText specimen= findViewById(R.id.sepciem);
        final EditText description= findViewById(R.id.description);
        final Button signupBtn = findViewById(R.id.signupbtn);



        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emailtxt = email.getText().toString().trim();
                final  String firstnametxt = firstname.getText().toString().trim();
                final String lastnametxt = lastname.getText().toString().trim();
                final String passwordtxt = password.getText().toString().trim();
                final String usernametxt = username.getText().toString().trim();
                final String specimentxt = specimen.getText().toString().trim();
                final String descriptiontxt = description.getText().toString().trim();
                if ( emailtxt.isEmpty() || firstnametxt.isEmpty()||lastnametxt.isEmpty()||passwordtxt.isEmpty()||usernametxt.isEmpty()||specimentxt.isEmpty()){
                    Toast.makeText(registercook.this, "please fill all fields", Toast.LENGTH_SHORT).show();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(emailtxt).matches()){
                    Toast.makeText(registercook.this, "please enter a valid email", Toast.LENGTH_SHORT).show();
                }
                else if(passwordtxt.length()<6){
                    Toast.makeText(registercook.this, "minimum password length should be 6", Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(usernametxt)){
                                Toast.makeText(registercook.this, "username is already registred: plese change the username", Toast.LENGTH_SHORT).show();

                            }
                            else{
                                databaseReference.child("Users").child(usernametxt).child("firstname").setValue(firstnametxt);
                                databaseReference.child("Users").child(usernametxt).child("lastname").setValue(lastnametxt);
                                databaseReference.child("Users").child(usernametxt).child("email").setValue(emailtxt);
                                databaseReference.child("Users").child(usernametxt).child("password").setValue(passwordtxt);
                                databaseReference.child("Users").child(usernametxt).child("specimen").setValue(specimentxt);
                                databaseReference.child("Users").child(usernametxt).child("description").setValue(descriptiontxt);
                                databaseReference.child("Users").child(usernametxt).child("usertype").setValue("Cook");
                                databaseReference.child("Users").child(usernametxt).child("monthsofsuspension").setValue("0");
                                databaseReference.child("Users").child(usernametxt).child("rating").setValue(0);
                                databaseReference.child("Users").child(usernametxt).child("NBrating").setValue(0);
                                Intent intent = new Intent(getApplicationContext(), finish.class);
                                startActivityForResult(intent, 0);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });
    }


    public void OnSetich3(View view){
        Intent intent = new Intent(getApplicationContext(), content1.class);

    }


}