package com.example.twoactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.android.twoactivities.extra.MESSAGE";

    private EditText mMessageEditText;

    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        send = findViewById(R.id.sendButton);
        mMessageEditText = findViewById(R.id.editText_main);

        send.setOnClickListener(
            new View.OnClickListener(){
                @Override
                public void onClick (View v){
                    moveActivity();
                }
            }
        );
    }

    public void moveActivity(){
        Intent change_intent = new Intent(this, SecondaryActivity.class);
        String message = mMessageEditText.getText().toString();
        change_intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(change_intent);
    }
}
