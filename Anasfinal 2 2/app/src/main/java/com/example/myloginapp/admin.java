package com.example.myloginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

public class admin extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mealerapp-a6e86-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        TextView plaintes=findViewById(R.id.plaintes);
        final EditText Msuspension=findViewById(R.id.duration);
        final Button suspend=findViewById(R.id.suspend);
        final Button reject=findViewById(R.id.reject);

        databaseReference.child("Plaintes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()){
                for (DataSnapshot postSnapShot : snapshot.getChildren()){
                String pl=postSnapShot.child("description").getValue(String.class);
                plaintes.setText(pl);
                break;
                }}
                else{
                    Toast.makeText(admin.this, "il n'y a plus de plaintes", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


        suspend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String suspendtxt=Msuspension.getText().toString().trim();
                if(suspendtxt.isEmpty()){
                    Toast.makeText(admin.this, "Enter the number of suspension months", Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference.child("Plaintes").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                String futureUID = "";
                                for(DataSnapshot futureUIDdatasnapshot:snapshot.getChildren() ){
                                    futureUID = futureUIDdatasnapshot.getKey();
                                    break;}
                                final String cook=snapshot.child(futureUID).child("cook").getValue(String.class);


                                System.out.println(cook);
                                System.out.println(suspendtxt);
                                databaseReference.child("Users").child(cook).child("monthsofsuspension").setValue(suspendtxt);
                                databaseReference.child("Plaintes").child(futureUID).removeValue();
                                Intent intent = new Intent(getApplicationContext(), admin.class);
                                startActivityForResult(intent, 0);
                                Toast.makeText(admin.this, cook+" succesfully suspendend", Toast.LENGTH_SHORT).show();

                            }




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
                        if (snapshot.exists()) {
                            String futureUID = "";
                            for(DataSnapshot futureUIDdatasnapshot:snapshot.getChildren() ){
                                futureUID = futureUIDdatasnapshot.getKey();
                                break;}
                            databaseReference.child("Plaintes").child(futureUID).removeValue();
                            Intent intent = new Intent(getApplicationContext(), admin.class);
                            startActivityForResult(intent, 0);
                            Toast.makeText(admin.this, "Plainte rejected", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });




    }
}