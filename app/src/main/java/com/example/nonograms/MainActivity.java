package com.example.nonograms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonStart = findViewById(R.id.button);
        buttonStart.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, ItemActivity.class);
            startActivity(i);
        });
    }
}