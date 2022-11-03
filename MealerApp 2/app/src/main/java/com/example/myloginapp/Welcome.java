package com.example.myloginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }
    public void OnSet1(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(intent, 0);

    }
    public void OnSet2(View view){
        Intent intent = new Intent(getApplicationContext(), registercook.class);
        startActivityForResult(intent, 0);
    }
    public void OnSet3(View view){
        Intent intent = new Intent(getApplicationContext(), register.class);
        startActivityForResult(intent, 0);
    }

}