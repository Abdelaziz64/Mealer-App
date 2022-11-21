package com.example.myloginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class suspended extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suspended);
    }
    public void OnSet4(View view){
        Intent intent = new Intent(getApplicationContext(), Welcome.class);
        startActivityForResult(intent, 0);}
}