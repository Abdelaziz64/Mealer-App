package com.example.myloginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class plainte4 extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mealerapp-a6e86-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plainte4);
        TextView plaintes=findViewById(R.id.plaintes);
        final EditText Msuspension=findViewById(R.id.duration);
        final Button suspend=findViewById(R.id.suspend);
        final Button reject=findViewById(R.id.reject);



        suspend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String suspendtxt=Msuspension.getText().toString().trim();
                if(suspendtxt.isEmpty()){
                    Toast.makeText(plainte4.this, "Enter the number of suspension months", Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference.child("Plaintes").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            final String cook=snapshot.child("plainte4").child("cook").getValue(String.class);
                            System.out.println(cook);
                            System.out.println(suspendtxt);
                            databaseReference.child("Users").child(cook).child("monthsofsuspension").setValue(suspendtxt);
                            databaseReference.child("Plaintes").child("plainte4").child("description").setValue("already processed");
                            Toast.makeText(plainte4.this, "cook:"+cook+"suspended", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), finish.class);
                            startActivityForResult(intent, 0);
                            Toast.makeText(plainte4.this, "fin des plaintes", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child("Plaintes").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child("Plaintes").child("plainte4").child("description").setValue("already processed");
                        Toast.makeText(plainte4.this, "Plainte rejected", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), finish.class);
                        startActivityForResult(intent, 0);
                        Toast.makeText(plainte4.this, "fin des plaintes", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }
}