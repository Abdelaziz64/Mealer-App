package com.example.myloginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class cook extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook);
        final Button commande = findViewById(R.id.cooksignin);
        final Button menu = findViewById(R.id.clientsignin);
        final Button profile = findViewById(R.id.clientsignin2);
        final Button logout = findViewById(R.id.loginbtn);
        commande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), commandes.class);
                startActivityForResult(intent, 0);
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), content1.class);
                startActivityForResult(intent, 0);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), profile.class);
                startActivityForResult(intent, 0);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }
    public void ichrak6(View view){
        Intent intent = new Intent(getApplicationContext(), commandes.class);
    startActivityForResult(intent, 0);}

    public void ichrak5(View view){
        Intent intent = new Intent(getApplicationContext(), content1.class);
        startActivityForResult(intent, 0);}
}