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

public class Plaintes extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mealerapp-a6e86-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plaintes);
        final EditText firstname= findViewById(R.id.username) ;
        final EditText description =findViewById(R.id.description);
        final Button signupbtn = findViewById(R.id.signupbtn);
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String cooktxt = firstname.getText().toString().trim();
                final String descriptiontxt = description.getText().toString().trim();
                if(cooktxt.isEmpty() || descriptiontxt.isEmpty()){
                    Toast.makeText(Plaintes.this, "Veuillez remplir tous les les champs ", Toast.LENGTH_SHORT).show();
                }
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(!snapshot.child("Users").hasChild(cooktxt)){
                            Toast.makeText(Plaintes.this, "Ce cuisinier n'existe pas", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            databaseReference.child("Plaintes").child(descriptiontxt).child("cook").setValue(cooktxt);
                            databaseReference.child("Plaintes").child(descriptiontxt).child("description").setValue(descriptiontxt);
                            Toast.makeText(Plaintes.this, "Votre plainte a été soumise", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });




    }
    public void ichrak0(View view){
        Intent intent = new Intent(getApplicationContext(), Client.class);
        startActivityForResult(intent, 0);}
}