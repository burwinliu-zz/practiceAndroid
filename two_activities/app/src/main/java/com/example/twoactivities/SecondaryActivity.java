package com.example.twoactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SecondaryActivity extends AppCompatActivity {

    TextView messageView;
    Intent intent;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);

        intent = getIntent();
        message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        messageView = findViewById(R.id.text_message);

        messageView.setText(message);
    }
}
