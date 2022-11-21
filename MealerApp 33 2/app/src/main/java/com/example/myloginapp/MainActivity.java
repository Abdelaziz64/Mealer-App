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

public class MainActivity extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mealerapp-a6e86-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final EditText username = findViewById(R.id.username);
        final EditText password = findViewById(R.id.adresse);
//        final TextView register = findViewById(R.id.register);
        final Button loginbtn = findViewById(R.id.loginbtn);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 String usernametxt = username.getText().toString();
                 String passwordtxt = password.getText().toString();
                if (usernametxt.isEmpty() || passwordtxt.isEmpty()) {
                    Toast.makeText(MainActivity.this, "please enter your username or password", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(usernametxt)){
                                final String getpassword =snapshot.child(usernametxt).child("password").getValue(String.class);
                                final String usertype =snapshot.child(usernametxt).child("usertype").getValue(String.class);
                                System.out.println(usertype);
                                System.out.println(usertype.equals("admin"));
                                System.out.println(usertype.equals("Cook"));


                                if(getpassword.equals(passwordtxt)){

                                    if(usertype.equals("admin")){
                                        Intent intent = new Intent(getApplicationContext(), admin.class);
                                        startActivityForResult(intent, 0);
                                    }

                                    else{
                                        if(usertype.equals("Cook")){

                                            String suspension= snapshot.child(usernametxt).child("monthsofsuspension").getValue(String.class);
                                            System.out.println(suspension);
                                            if(!suspension.equals("0")){
                                                //99 is special code for definitive suspension
                                                if(suspension.equals("99")){
                                                    Toast.makeText(MainActivity.this, "banned definetly", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(getApplicationContext(),suspended.class);
                                                    startActivityForResult(intent, 0);
                                                }
                                                else{
                                                    Toast.makeText(MainActivity.this, suspension+"Months of suspension", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(getApplicationContext(),suspended.class);
                                                    startActivityForResult(intent, 0);
                                                }
                                            }
                                            else{
                                            Intent intent = new Intent(getApplicationContext(),content1.class);
                                            startActivityForResult(intent, 0);}
                                        }

                                    }


                                }
                                else{
                                    Toast.makeText(MainActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                                }

                            }
                            else{
                                Toast.makeText(MainActivity.this, "Wrong username", Toast.LENGTH_SHORT).show();
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

    public void OnSetRegister(View view){
        Intent intent = new Intent(getApplicationContext(), Welcome.class);
        startActivityForResult(intent, 0);
    }
    public void OnSet77(View view){
        Intent intent = new Intent(getApplicationContext(), finish.class);
        startActivityForResult(intent, 0);
    }

}

