package com.example.myloginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class finish extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
//
//        textview view = R.id.textView
//                view.setText(kdjegfajd + user.type)
    }
    public void OnSet4(View view){
        Intent intent = new Intent(getApplicationContext(), Welcome.class);
        startActivityForResult(intent, 0);
}}