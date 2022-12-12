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

public class rating extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mealerapp-a6e86-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        final EditText cook = findViewById(R.id.cookname);
        final EditText rating= findViewById(R.id.rating);
        final Button rate= findViewById(R.id.button2);
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String cooktxt = cook.getText().toString().trim();
                final  String ratingtxt = rating.getText().toString().trim();

                if (cooktxt.isEmpty() || ratingtxt.isEmpty()){
                    Toast.makeText(rating.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                }
                else {
                    final Integer rate = Integer.parseInt(ratingtxt);
                       if (rate>10){
                        Toast.makeText(rating.this, "la note ne peut pas depasser 10", Toast.LENGTH_SHORT).show();
                    }
                       else{
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (!snapshot.child("Users").hasChild(cooktxt)) {
                                Toast.makeText(rating.this, "Ce cuisinier n'existe pas", Toast.LENGTH_SHORT).show();
                            }

                            else {
                                Integer note = snapshot.child("Users").child(cooktxt).child("rating").getValue(Integer.class);
                                Integer NBnote = snapshot.child("Users").child(cooktxt).child("NBrating").getValue(Integer.class);
                                System.out.println(note);
                                System.out.println(NBnote);
                                System.out.println(rate);
                                Integer a = (note * NBnote + rate) /( NBnote + 1);
                                System.out.println(a);
                                databaseReference.child("Users").child(cooktxt).child("rating").setValue(a);
                                databaseReference.child("Users").child(cooktxt).child("NBrating").setValue(NBnote + 1);
                                Toast.makeText(rating.this, "Rating completed", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Client.class);
                                startActivityForResult(intent, 0);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });}
                }

            }
        });



    }
}