package com.example.droiddessert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.android.droidcafe.extra.MESSAGE";

    String mOrderMessage = "";
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.floatingActionButton);

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                displayOrder();
            }
        });
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
        mOrderMessage += ("\n" + message);
    }

    public void showDonutOrder(View view) {
        displayToast(getString(R.string.donut_order_message));
    }

    public void showIceCreamOrder(View view) {
        displayToast(getString(R.string.ice_cream_order_message));
    }

    public void showFroyoOrder(View view) {
        displayToast(getString(R.string.froyo_order_message));

    }

    public void displayOrder(){
        Intent intent =
                new Intent(MainActivity.this, OrderActivity.class);
        intent.putExtra(EXTRA_MESSAGE, mOrderMessage);
        startActivity(intent);
    }
}
