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

public class profile extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mealerapp-a6e86-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        final EditText cookname= findViewById(R.id.duration2);
        final TextView title = findViewById(R.id.welcometitle);
        final Button rate= findViewById(R.id.logout2);
        final Button show= findViewById(R.id.logout3);
        final Button logout= findViewById(R.id.logout);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String cooktxt = cookname.getText().toString().trim();


                if (cooktxt.isEmpty() ){
                    Toast.makeText(profile.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                }
                else {


                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (!snapshot.child("Users").hasChild(cooktxt)) {
                                    Toast.makeText(profile.this, "Ce cuisinier n'existe pas", Toast.LENGTH_SHORT).show();
                                }

                                else {
                                    Integer note = snapshot.child("Users").child(cooktxt).child("rating").getValue(Integer.class);

                                    title.setText("Rating : "+note);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });}
                }


        });
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), cook.class);
                startActivityForResult(intent, 0);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Welcome.class);
                startActivityForResult(intent, 0);
            }
        });
    }
}